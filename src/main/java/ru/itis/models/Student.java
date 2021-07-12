package ru.itis.models;

import java.util.List;

public class Student {
    private Long id;
    private String firstName;
    private String secondName;
    private Integer group;
    private List<Course> courses;

    public Student(Long id) {
        this.id = id;
    }

    public Student(Long id, String firstName, String secondName, Integer group) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.group = group;
    }

    public Student(String firstName, String secondName, Integer group, List<Course> courses) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.group = group;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", group=" + group +
                '}';
    }
}
