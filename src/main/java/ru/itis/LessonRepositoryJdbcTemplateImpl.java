package ru.itis;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Course;
import ru.itis.models.Lesson;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class LessonRepositoryJdbcTemplateImpl implements LessonsRepository {

    private final JdbcTemplate jdbcTemplate;

    public LessonRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //language=SQL
    private static final String SQL_SAVE_LESSON =
            "insert into lesson (name, date, course_id) values  (?, ?, ?);";

    //language=SQL
    private static final String SQL_UPDATE_LESSON =
            "update lesson set name = ?, date = ?, course_id = ? where id = ?";

    //language=SQL
    private static final String SQL_FIND_LESSON_BY_ID = "select *, c.name as course_name from lesson l left join course c on l.course_id = c.id where l.id = ?";

    //language=SQL
    private static final String SQL_FIND_LESSON_BY_NAME = "select *,  c.name as course_name  from lesson l left join course c on l.course_id = c.id where l.name = ?";


    private final Function<ResultSet, Lesson> lessonMapper = row -> {
        try {
            Long id = row.getLong("id");
            String name = row.getString("name");
            LocalDate date = LocalDate.parse(row.getString("date"));

            return new Lesson(id, name, date);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    private final Function<ResultSet, Course> courseMapper = row -> {
        try {
            Long id = row.getLong("id");
            String name = row.getString("course_name");
            LocalDate startDate = LocalDate.parse(row.getString("start_date"));
            LocalDate endDate = LocalDate.parse(row.getString("end_date"));

            return new Course(id, name, startDate, endDate, new ArrayList<>());

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };


    public final ResultSetExtractor<List<Lesson>> lessonsListResultSetExtractor = resultSet -> {
        List<Lesson> lessonsList = new ArrayList<>();
        while (resultSet.next()) {
            Lesson lesson = lessonMapper.apply(resultSet);
            Course course = courseMapper.apply(resultSet);
            lesson.setCourse(course);
            lessonsList.add(lesson);
        }
        return lessonsList;
    };

    public final ResultSetExtractor<Lesson> lessonResultSetExtractor = resultSet -> {
        if (resultSet.next()) {
            Lesson lesson = lessonMapper.apply(resultSet);
            Course course = courseMapper.apply(resultSet);
            lesson.setCourse(course);
            return lesson;
        }
        return null;
    };


    @Override
    public List<Lesson> findAllByName(String name) {
        return jdbcTemplate.query(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(SQL_FIND_LESSON_BY_NAME, new String[]{"id"});
                    statement.setString(1, name);
                    return statement;
                }, lessonsListResultSetExtractor);
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.query(
                    connection -> {
                        PreparedStatement statement = connection.prepareStatement(SQL_FIND_LESSON_BY_ID);
                        statement.setInt(1, id);
                        return statement;
                    }
                    , lessonResultSetExtractor));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        System.out.println("\n" + lesson);
        jdbcTemplate.update(connection -> {

                    PreparedStatement statement = connection.prepareStatement(SQL_SAVE_LESSON, new String[]{"id"});
                    statement.setString(1, lesson.getName());
                    statement.setString(2, lesson.getDate().toString());
                    statement.setLong(3, lesson.getCourse().getId());
                    return statement;
                }
                , keyHolder);
        lesson.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        System.out.println(lesson);

    }

    @Override
    public void update(Lesson lesson) {
        jdbcTemplate.update(SQL_UPDATE_LESSON, lesson.getName(), lesson.getDate(),
                lesson.getCourse().getId(), lesson.getId());
    }
}
