package com.lyonguyen.esky.ws.services;

import com.lyonguyen.esky.logic.dao.DatabaseContext;
import com.lyonguyen.esky.logic.dao.LearnerDAO;
import com.lyonguyen.esky.logic.dao.LessonDAO;
import com.lyonguyen.esky.logic.dao.QuestionDAO;
import com.lyonguyen.esky.logic.mapper.DataEncoder;
import com.lyonguyen.esky.logic.models.Learner;
import com.lyonguyen.esky.logic.models.Lesson;
import com.lyonguyen.esky.logic.models.LevelInfo;
import com.lyonguyen.esky.logic.models.Question;
import com.lyonguyen.esky.ws.factories.ResponseFactory;
import com.lyonguyen.esky.ws.models.request.LessonProgress;
import com.lyonguyen.esky.ws.utils.JSONUtils;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Vector;

@Resource
public class LearnerService {

    private LearnerDAO learnerDAO = DatabaseContext.getInstance().getLearnerDAO();

    private LessonDAO lessonDAO = DatabaseContext.getInstance().getLessonDAO();

    private QuestionDAO questionDAO = DatabaseContext.getInstance().getQuestionDAO();

    private DataEncoder encoder = new DataEncoder();

    public Response get(String id) {
        Learner learner = learnerDAO.get(id);
        if(learner == null) {
            return ResponseFactory.createUserNotFoundResponse(id);
        }
        return ResponseFactory.createResponseWithEntity(learner);
    }

    public Response getLessons(String id) {
        if(!learnerDAO.has(id)) {
            return ResponseFactory.createUserNotFoundResponse(id);
        }
        return ResponseFactory.createResponseWithEntity(learnerDAO.getLessons(id));
    }

    public Response getLesson(String id, String lessonId) {
        Lesson lesson = learnerDAO.getLesson(id, lessonId);
        if(lesson == null) {
            return ResponseFactory.createContentNotFoundResponse(lessonId);
        }
        List<Question> questions = lessonDAO.getQuestions(lessonId);
        String json = JSONUtils.create().add("lesson", lesson).add("questions", questions).build();
        return ResponseFactory.createResponseWithEntity(json);
    }

    public Response answerQuestion(String id, String lessonId, String questionId, String answer) {
        if(!checkAnswer(questionId, answer)) {
            updateRemains(id, lessonId, questionId, false);
            return ResponseFactory.createResponseWithEntity("result", false);
        }
        updateAnswered(id, lessonId, questionId);
        updateRemains(id, lessonId, questionId, true);
        return ResponseFactory.createResponseWithEntity("result", true);
    }

    private boolean checkAnswer(String questionId, String answer) {
        Question question = questionDAO.get(questionId);
        List<String> answers = question.getAnswers();
        switch (question.getAnswerType()) {
            case ARRANGEMENT:
                return checkArrangementAnswer(answers, answer);
            case MULTI_CHOICE:
                return checkMultiChoiceAnswer(answers, answer);
            case SPEECH:
                return checkSpeechAnswer(answers, answer);
            case TYPING:
                return checkTypingAnswer(answers, answer);
        }
        return false;
    }

    private boolean checkArrangementAnswer(List<String> questionAnswer, String answer) {
        String compressedAnswer = encoder.compress(questionAnswer);
        return compressedAnswer.equals(answer);
    }

    private boolean checkMultiChoiceAnswer(List<String> questionAnswer, String answer) {
        return questionAnswer.contains(answer.toLowerCase());
    }

    private boolean checkSpeechAnswer(List<String> questionAnswer, String answer) {
        return questionAnswer.contains(answer.toLowerCase());
    }

    private boolean checkTypingAnswer(List<String> questionAnswer, String answer) {
        return questionAnswer.contains(answer.toLowerCase());
    }

    private void updateAnswered(String id, String lessonId, String questionId) {
        List<String> answered = learnerDAO.getLesson(id, lessonId).getAnswered();
        if(!answered.contains(questionId)) {
            answered.add(questionId);
            learnerDAO.updateAnswered(id, lessonId, answered);
        }
    }

    public void updateRemains(String id, String lessonId, String questionId, boolean result) {
        List<String> remains = learnerDAO.getLesson(id, lessonId).getRemains();
        if(result) {
            remains.remove(questionId);
        } else {
            remains.remove(questionId);
            remains.add(questionId);
        }
        learnerDAO.updateRemains(id, lessonId, remains);
    }

    public Response getLessonReward(String id, String lessonId) {
        Lesson lesson = learnerDAO.getLesson(id, lessonId);
        if(lesson.getPassed() || !lesson.getRemains().isEmpty()) {
            return ResponseFactory.createResponseWithEntity("experience", 0);
        }
        learnerDAO.passLesson(id, lessonId);
        return ResponseFactory.createResponseWithEntity("experience", lesson.getExperience());
    }

    public Response getLevelInfo() {
        LevelInfo levelInfo = learnerDAO.getLevelInfo();
        return ResponseFactory.createResponseWithEntity(levelInfo);
    }
}
