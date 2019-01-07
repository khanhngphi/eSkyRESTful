package com.lyonguyen.esky.ws.models.response;

import com.lyonguyen.esky.logic.models.Lesson;
import com.lyonguyen.esky.logic.models.Question;

import java.util.List;

public class LessonResponse {

    private Lesson lesson;

    private List<Question> questions;

    public LessonResponse() {}

    public LessonResponse(Lesson lesson, List<Question> questions) {
        this.lesson = lesson;
        this.questions = questions;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
