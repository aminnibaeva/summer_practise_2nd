package ru.itis;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Course;
import ru.itis.models.Teacher;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CoursesRepositoryJdbcTemplateImpl implements CoursesRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_SAVE_COURSE =
            "insert into course (name, start_date, end_date, teacher_id) values  (?, ?, ?, ?);";

    private static final String SQL_UPDATE_COURSE =
            "update course set name = ?, start_date = ?, end_date = ?, teacher_id = ? where id = ?";

    private static final String SQL_FIND_COURSE_BY_ID = "select * from course left join teacher t on course.teacher_id = t.id where course.id = ?";

    private static final String SQL_FIND_COURSE_BY_NAME = "select * from course left join teacher t on course.teacher_id = t.id where course.name = ?";


    private final Function<ResultSet, Course> courseMapper = row -> {
        try {
            Long id = row.getLong("id");
            String name = row.getString("name");
            LocalDate startDate = LocalDate.parse(row.getString("start_date"));
            LocalDate endDate = LocalDate.parse(row.getString("end_date"));

            return new Course(id, name, startDate, endDate);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    private final Function<ResultSet, Teacher> teacherMapper = row -> {
        try {
            Long teacherId = row.getLong("teacher_id");
            String teacherFirstName = row.getString("first_name");
            String teacherSecondName = row.getString("second_name");
            Integer teacherWorkExperience = row.getInt("work_experience");

            return new Teacher(teacherId, teacherFirstName, teacherSecondName, teacherWorkExperience, new ArrayList<>());
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    public final ResultSetExtractor<List<Course>> courseListResultSetExtractor = resultSet -> {
        List<Course> courseList = new ArrayList<>();
        while (resultSet.next()) {
            Course course = courseMapper.apply(resultSet);
            Teacher teacher = teacherMapper.apply(resultSet);
            course.setTeacher(teacher);
            courseList.add(course);
        }
        return courseList;
    };

    public final ResultSetExtractor<Course> courseResultSetExtractor = resultSet -> {
        if (resultSet.next()) {
            Course course = courseMapper.apply(resultSet);
            Teacher teacher = teacherMapper.apply(resultSet);
            course.setTeacher(teacher);
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
            return Optional.of(jdbcTemplate.query(
                    connection -> {
                        PreparedStatement statement = connection.prepareStatement(SQL_FIND_COURSE_BY_ID);
                        statement.setInt(1, id);
                        return statement;
                    }
                    , courseResultSetExtractor));
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
        course.setId(keyHolder.getKey().longValue());
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(SQL_UPDATE_COURSE, course.getName(), course.getStartDate(),
                course.getEndDate(), course.getTeacher().getId(), course.getId());
    }
}
