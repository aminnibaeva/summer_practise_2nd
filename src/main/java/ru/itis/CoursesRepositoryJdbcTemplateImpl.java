package ru.itis;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Course;
import ru.itis.models.Student;
import ru.itis.models.Teacher;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class CoursesRepositoryJdbcTemplateImpl implements CoursesRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_SAVE_COURSE =
            "insert into course (name, start_date, end_date, teacher_id) values  (?, ?, ?, ?);";

    private static final String SQL_UPDATE_COURSE =
            "update course set name = ?, start_date = ?, end_date = ?, teacher_id = ? where id = ?";

    private static final String SQL_FIND_COURSE_BY_ID = "select course_id_ as id_course, name as course_name, start_date " +
            "as course_start_date, end_date as course_end_date, t.id as teacher_id, t.first_name as teacher_first_name," +
            " t.second_name as teacher_second_name,   work_experience , t2.id_student as student_id, t2.first_name as " +
            "student_first_name, t2.second_name as student_second_name, group_number as student_group_number from (select " +
            "*  from (select *, id as course_id_ from course full join course_students on course.id = " +
            "course_students.id_course where course.id = ?) t1 left join  student s on t1.id_student = s.id) t2 left" +
            " join teacher t on  t2.teacher_id = t.id order by id_course";

    private static final String SQL_FIND_COURSE_BY_NAME = "select course_id_ as id_course, name as course_name, " +
            "start_date as course_start_date, end_date as course_end_date, t.id as teacher_id, t.first_name as " +
            "teacher_first_name, t.second_name as teacher_second_name,   work_experience , t2.id_student as student_id, " +
            "t2.first_name as student_first_name, t2.second_name as student_second_name, group_number as student_group_number" +
            " from (select *  from (select *, id as course_id_ from course full join course_students on course.id = " +
            "course_students.id_course where course.name = ?) t1 left join  student s on t1.id_student = s.id) t2 left join" +
            " teacher t on  t2.teacher_id = t.id order by id_course";


    private final Function<ResultSet, Course> courseMapper = row -> {
        try {
            Long id = row.getLong("id_course");
            String name = row.getString("course_name");
            LocalDate startDate = LocalDate.parse(row.getString("course_start_date"));
            LocalDate endDate = LocalDate.parse(row.getString("course_end_date"));

            return new Course(id, name, startDate, endDate, new ArrayList<>());
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    private final Function<ResultSet, Teacher> teacherMapper = row -> {
        try {
            Long teacherId = row.getLong("teacher_id");
            String teacherFirstName = row.getString("teacher_first_name");
            String teacherSecondName = row.getString("teacher_second_name");
            Integer teacherWorkExperience = row.getInt("work_experience");

            return new Teacher(teacherId, teacherFirstName, teacherSecondName, teacherWorkExperience, new ArrayList<>());
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    private final Function<ResultSet, Student> studentMapper = row -> {
        try {
            if (row.getString("student_first_name") == null) {
                return null;
            }
            Long id = row.getLong("student_id");
            String firstName = row.getString("student_first_name");
            String secondName = row.getString("student_second_name");
            Integer groupNumber = row.getInt("student_group_number");
            return new Student(id, firstName, secondName, groupNumber);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    public final ResultSetExtractor<List<Course>> courseListResultSetExtractor = rows -> {
        List<Course> courseList = new ArrayList<>();
        Set<Long> processedCourses = new HashSet<>();
        Course currentCourse = null;
        while (rows.next()) {

            if (!processedCourses.contains(rows.getLong("id_course"))) {
                currentCourse = courseMapper.apply(rows);
                courseList.add(currentCourse);
                Teacher teacher = teacherMapper.apply(rows);
                currentCourse.setTeacher(teacher);
            }

            Integer student_id = rows.getObject("student_id", Integer.class);

            if (student_id != null) {
                Student student = studentMapper.apply(rows);
                currentCourse.getStudents().add(student);
            }
            processedCourses.add(currentCourse.getId());
        }
        return courseList;
    };

    public final ResultSetExtractor<Course> courseResultSetExtractor = resultSet -> {
        if (resultSet.next()) {
            Course course = courseMapper.apply(resultSet);
            Teacher teacher = teacherMapper.apply(resultSet);
            course.setTeacher(teacher);
            List<Student> students = new ArrayList<>();
            do {
                students.add(studentMapper.apply(resultSet));
            } while (resultSet.next());
            course.setStudents(students);
            return course;
        }
        return null;

    };


    public CoursesRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Course> findAllByName(String name) {
        return jdbcTemplate.query(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(SQL_FIND_COURSE_BY_NAME, new String[]{"id"});
                    statement.setString(1, name);
                    return statement;
                }, courseListResultSetExtractor);
    }

    @Override
    public Optional<Course> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.query(
                    connection -> {
                        PreparedStatement statement = connection.prepareStatement(SQL_FIND_COURSE_BY_ID);
                        statement.setInt(1, id);
                        return statement;
                    }, courseResultSetExtractor));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {

                    PreparedStatement statement = connection.prepareStatement(SQL_SAVE_COURSE, new String[]{"id"});
                    statement.setString(1, course.getName());
                    statement.setString(2, course.getStartDate().toString());
                    statement.setString(3, course.getEndDate().toString());
                    statement.setLong(4, course.getTeacher().getId());
                    return statement;
                }
                , keyHolder);
        course.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(SQL_UPDATE_COURSE, course.getName(), course.getStartDate(),
                course.getEndDate(), course.getTeacher().getId(), course.getId());
    }
}
