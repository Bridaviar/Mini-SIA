package com.grades.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String id;
    private List<Grade> grades;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public double calculateAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double weightedSum = 0;
        double totalWeight = 0;
        for (Grade g : grades) {
            weightedSum += g.getScore() * g.getSubject().getPercentage();
            totalWeight += g.getSubject().getPercentage();
        }
        return weightedSum / totalWeight;
    }
}
