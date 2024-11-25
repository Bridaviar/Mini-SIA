package com.grades.model;

public class Grade {
    private Subject subject;
    private double score;

    public Grade(Subject subject, double score) {
        this.subject = subject;
        this.score = score;
    }

    public Subject getSubject() {
        return subject;
    }

    public double getScore() {
        return score;
    }
}
