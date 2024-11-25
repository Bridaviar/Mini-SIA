package com.grades.model;

public class Subject {
    private String name;
    private double percentage;

    public Subject(String name, double percentage) {
        this.name = name;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public double getPercentage() {
        return percentage;
    }
}
