package com.grades.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private List<Student> students;
    private List<Subject> subjects;

    public Course(String name) {
        this.students = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = new ArrayList<>(subjects);
    }

    public Student getStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public double calculateCourseAverage() {
        if (students.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        int count = 0;
        for (Student student : students) {
            double avg = student.calculateAverage();
            if (avg > 0) {
                sum += avg;
                count++;
            }
        }
        return count > 0 ? sum / count : 0.0;
    }
}
