package com.lyonguyen.esky.logic.models;

public class Learner extends DatabaseObject {

    private String id;

    private int trophy;

    private int experience;

    private int wordsRecord;

    private int questionsAnswered;

    private int lessonsPassed;

    private int challengeTimes;

    private int topChallenge;

    public Learner() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTrophy() {
        return trophy;
    }

    public void setTrophy(int trophy) {
        this.trophy = trophy;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getWordsRecord() {
        return wordsRecord;
    }

    public void setWordsRecord(int wordsRecord) {
        this.wordsRecord = wordsRecord;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    public int getLessonsPassed() {
        return lessonsPassed;
    }

    public void setLessonsPassed(int lessonsPassed) {
        this.lessonsPassed = lessonsPassed;
    }

    public int getChallengeTimes() {
        return challengeTimes;
    }

    public void setChallengeTimes(int challengeTimes) {
        this.challengeTimes = challengeTimes;
    }

    public int getTopChallenge() {
        return topChallenge;
    }

    public void setTopChallenge(int topChallenge) {
        this.topChallenge = topChallenge;
    }
}
