package com.lyonguyen.esky.ws.services;

import com.lyonguyen.esky.logic.dao.ContributorDAO;
import com.lyonguyen.esky.logic.dao.DatabaseContext;
import com.lyonguyen.esky.logic.dao.LessonDAO;
import com.lyonguyen.esky.logic.dao.QuestionDAO;
import com.lyonguyen.esky.logic.enums.ContributeMethod;
import com.lyonguyen.esky.logic.models.Lesson;
import com.lyonguyen.esky.logic.models.Question;
import com.lyonguyen.esky.ws.factories.ResponseFactory;
import com.lyonguyen.esky.ws.models.request.LessonContribute;
import com.lyonguyen.esky.ws.models.request.QuestionContribute;
import com.lyonguyen.esky.ws.utils.Validator;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

@Resource
public class ContributorService {

    private ContributorDAO contributorDAO = DatabaseContext.getInstance().getContributorDAO();

    private LessonDAO lessonDAO = DatabaseContext.getInstance().getLessonDAO();

    private QuestionDAO questionDAO = DatabaseContext.getInstance().getQuestionDAO();

    public Response insertLesson(String id, boolean accepted, LessonContribute info) {
        info.getLesson().trim();
        if (!Validator.lesson(info.getLesson()) || Validator.isEmpty(info.getQuestions())) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        boolean result = contributorDAO.contributeLesson(id, ContributeMethod.INSERT, info.getNote(), accepted,
                                                        info.getLesson(), info.getQuestions());
        if (!result) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        return ResponseFactory.createOkResponse();
    }

    public Response updateLesson(String id, String lessonId, boolean accepted, LessonContribute info) {
        info.getLesson().trim();
        info.getLesson().setId(lessonId);
        if (!Validator.lesson(info.getLesson()) || Validator.isEmpty(info.getQuestions())) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        boolean result = contributorDAO.contributeLesson(id, ContributeMethod.UPDATE, info.getNote(), accepted,
                                                        info.getLesson(), info.getQuestions());
        if (!result) {
            return ResponseFactory.createContentNotFoundResponse(info.getLesson().getId());
        }
        return ResponseFactory.createOkResponse();
    }

    public Response deleteLesson(String id, String lessonId, boolean accepted, String note) {
        if (note.isEmpty()) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        Lesson lesson = lessonDAO.get(lessonId);
        if (lesson == null) {
            return ResponseFactory.createContentNotFoundResponse(lessonId);
        }
        boolean result = contributorDAO.contributeLesson(id, ContributeMethod.DELETE, note, accepted, lesson, null);
        if (!result) {
            return ResponseFactory.createContentNotFoundResponse(lessonId);
        }
        return ResponseFactory.createOkResponse();
    }

    public Response insertQuestion(String id, boolean accepted, QuestionContribute info) {
        info.getQuestion().trim();
        if (!Validator.question(info.getQuestion())) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        boolean result = contributorDAO.contributeQuestion(id, ContributeMethod.INSERT, info.getNote(), accepted, info.getQuestion());
        if (!result) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        return ResponseFactory.createOkResponse();
    }

    public Response updateQuestion(String id, String questionId, boolean accepted, QuestionContribute info) {
        info.getQuestion().trim();
        info.getQuestion().setId(questionId);
        if (!Validator.question(info.getQuestion()) || info.getNote().trim().isEmpty()) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        boolean result = contributorDAO.contributeQuestion(id, ContributeMethod.UPDATE, info.getNote(), accepted, info.getQuestion());
        if (result == false) {
            return ResponseFactory.createContentNotFoundResponse(info.getQuestion().getId());
        }
        return ResponseFactory.createOkResponse();
    }

    public Response deleteQuestion(String id, String questionId, boolean accepted, String note) {
        if (note.isEmpty()) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        Question question = questionDAO.get(questionId);
        if (question == null) {
            return ResponseFactory.createContentNotFoundResponse(questionId);
        }
        boolean result = contributorDAO.contributeQuestion(id, ContributeMethod.DELETE, note, accepted, question);
        if (!result) {
            return ResponseFactory.createContentNotFoundResponse(questionId);
        }
        return ResponseFactory.createOkResponse();
    }
}
