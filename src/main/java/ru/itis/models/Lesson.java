package ru.itis.models;

import java.time.LocalDate;

public class Lesson {
    private Long id;
    private String name;
    private LocalDate date;
    private Course course;

    public Lesson(Long id, String name, LocalDate date, Course course) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.course = course;
    }

    public Lesson(String name, LocalDate date, Course course) {
        this.name = name;
        this.date = date;
        this.course = course;
    }

    public Lesson(Long id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", course=" + course +
                '}';
    }
}
