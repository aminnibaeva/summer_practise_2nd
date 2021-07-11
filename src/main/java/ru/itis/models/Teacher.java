package ru.itis.models;

import java.util.List;

public class Teacher {
    private Long id;
    private String firstName;
    private String secondName;
    private Integer workExperience;
    private List<Course> courses;

    public Teacher(Long id) {
        this.id = id;
    }

    public Teacher(Long id, String firstName, String secondName, Integer workExperience, List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.workExperience = workExperience;
        this.courses = courses;
    }

    public Teacher(String firstName, String secondName, Integer workExperience, List<Course> courses) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.workExperience = workExperience;
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

    public Integer getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", workExperience=" + workExperience +
                '}';
    }
}
