package com.lyonguyen.esky.ws.services;

import com.lyonguyen.esky.logic.dao.DatabaseContext;
import com.lyonguyen.esky.logic.dao.LessonDAO;
import com.lyonguyen.esky.logic.models.Lesson;
import com.lyonguyen.esky.ws.factories.ResponseFactory;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

@Resource
public class LessonService {

    private LessonDAO lessonDAO = DatabaseContext.getInstance().getLessonDAO();

    public Response get(String id) {
        Lesson lesson = lessonDAO.get(id);
        if (lesson == null) {
            return ResponseFactory.createContentNotFoundResponse(id);
        }
        return ResponseFactory.createResponseWithEntity(lesson);
    }

    public Response getQuestions(String id) {
        if(!lessonDAO.has(id)) {
            return ResponseFactory.createContentNotFoundResponse(id);
        }
        return ResponseFactory.createResponseWithEntity(lessonDAO.getQuestions(id));
    }
}
