package com.lyonguyen.esky.ws.models.request;

import com.lyonguyen.esky.logic.enums.ContributeMethod;
import com.lyonguyen.esky.logic.models.Lesson;
import com.lyonguyen.esky.logic.models.Question;

public class QuestionContribute {

    private String note;

    private Question question;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
