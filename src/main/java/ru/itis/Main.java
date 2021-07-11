package ru.itis;

import ru.itis.models.Course;
import ru.itis.models.Lesson;
import ru.itis.models.Teacher;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
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
//        CoursesRepository coursesRepository = new CoursesRepositoryJdbcTemplateImpl(dataSource);
//
//        List<Student> students = new ArrayList<>();
//        students.add(new Student(1L));
//        students.add(new Student(2L));
//        students.add(new Student(3L));
//
//        coursesRepository.save(
//                new Course("Французский", LocalDate.parse("2020-02-05"), LocalDate.parse("2020-06-21"),
//                        new Teacher(1L), students));
//
//        coursesRepository.update(new Course(5L, "Испанский", LocalDate.parse("2020-06-12"), LocalDate.parse("2021-07-18"), new Teacher(1L)));
//        System.out.println(coursesRepository.findAllByName("Французский"));
//        System.out.println(coursesRepository.findById(3));
//
//
//        LessonsRepository lessonsRepository = new LessonRepositoryJdbcTemplateImpl(dataSource);


//        System.out.println(lessonsRepository.findAllByName("wlkn"));
//            lessonsRepository.save(new Lesson("wlkn", LocalDate.parse("2020-05-05"), new Course(1L)));
//        System.out.println(lessonsRepository.findById(1));
//        lessonsRepository.update(new Lesson(7L, "Татарский", LocalDate.parse("2020-05-05"), new Course(1L)));
/*
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L));
        students.add(new Student(2L));
        students.add(new Student(3L));

        coursesRepository.save(
                new Course("Французский", LocalDate.parse("2020-02-05"), LocalDate.parse("2020-06-21"),
                        new Teacher(1L), students));
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L));
        students.add(new Student(2L));
        students.add(new Student(3L));

        coursesRepository.save(
                new Course("Французский", LocalDate.parse("2020-02-05"), LocalDate.parse("2020-06-21"),
                        new Teacher(1L), students));

        System.out.println("findById: " + coursesRepository.findById(5L));

        System.out.println("findByName: " + coursesRepository.findByName("Французский").toString());

        System.out.println("findAll: " + coursesRepository.findAll().toString());

        coursesRepository.delete(5L);
*/

    }
}
