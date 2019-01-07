package com.lyonguyen.esky.ws.models.request;

import com.lyonguyen.esky.logic.models.Lesson;

import java.util.List;

public class LessonContribute {

    private String note;

    private Lesson lesson;

    private List<String> questions;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
}
