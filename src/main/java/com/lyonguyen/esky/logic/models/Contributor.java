package com.lyonguyen.esky.logic.models;

public class Contributor extends DatabaseObject {

    private String id;

    private int editTimes;

    private int addTimes;

    private int removeTimes;

    public Contributor() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEditTimes() {
        return editTimes;
    }

    public void setEditTimes(int editTimes) {
        this.editTimes = editTimes;
    }

    public int getAddTimes() {
        return addTimes;
    }

    public void setAddTimes(int addTimes) {
        this.addTimes = addTimes;
    }

    public int getRemoveTimes() {
        return removeTimes;
    }

    public void setRemoveTimes(int removeTimes) {
        this.removeTimes = removeTimes;
    }
}
