package com.lyonguyen.esky.logic.models;

import java.util.List;
import java.util.Vector;

public class Lesson extends DatabaseObject {

    private String id;

    private String subject;

    private String title;

    private String description;

    private int level;

    private List<String> answered = new Vector<>();

    private List<String> remains = new Vector<>();

    private int experience;

    private boolean passed;

    public Lesson() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<String> getAnswered() {
        return answered;
    }

    public void setAnswered(List<String> answered) {
        this.answered = answered;
    }

    public List<String> getRemains() {
        return remains;
    }

    public void setRemains(List<String> remains) {
        this.remains = remains;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean getPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
