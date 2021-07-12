package ru.itis;

import ru.itis.models.Course;
import ru.itis.models.Lesson;
import ru.itis.models.Teacher;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;


public class Main {

    public static void main(String[] args) {
        Properties properties = new Properties();

        try {
            properties.load(new FileReader("src\\main\\resources\\application.properties"));

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        DataSource dataSource = new HikariDataSourceWrapper(properties).getHikariDataSource();
        CoursesRepository coursesRepository = new CoursesRepositoryJdbcTemplateImpl(dataSource);

        coursesRepository.save(new Course("Французский", LocalDate.parse("2020-02-05"), LocalDate.parse("2020-06-21"),
                        new Teacher(1L), new ArrayList<>()));

        coursesRepository.update(new Course(5L, "Испанский", LocalDate.parse("2020-06-12"), LocalDate.parse("2021-07-18"),
                new Teacher(2L)));

        System.out.print("\n" + "findAllCoursesByName: ");
        for (Course course : coursesRepository.findAllByName("Английский")) {
            System.out.println(course);

        }
        System.out.println("\n" + "findCoursesById: " + coursesRepository.findById(77));

        LessonsRepository lessonsRepository = new LessonRepositoryJdbcTemplateImpl(dataSource);
        System.out.println("\n" + "findAllLessonsByName: " + lessonsRepository.findAllByName("Английский"));
        lessonsRepository.save(new Lesson("Французский", LocalDate.parse("2020-05-05"), new Course(1L)));
        System.out.println("\n" + "findAllLessonsById: " + lessonsRepository.findById(6));
        lessonsRepository.update(new Lesson(6L, "Татарский", LocalDate.parse("2020-05-05"), new Course(1L)));
   }
}
