package com.lyonguyen.esky.ws.services;

import com.lyonguyen.esky.logic.dao.DatabaseContext;
import com.lyonguyen.esky.logic.dao.QuestionDAO;
import com.lyonguyen.esky.logic.models.Question;
import com.lyonguyen.esky.ws.factories.ResponseFactory;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import java.util.List;

@Resource
public class QuestionService {

    private QuestionDAO questionDAO = DatabaseContext.getInstance().getQuestionDAO();

    public Response getAll() {
        List<Question> questions = questionDAO.getAll();
        return ResponseFactory.createResponseWithEntity(questions);
    }

    public Response get(String id) {
        Question question = questionDAO.get(id);
        if(question == null) {
            return ResponseFactory.createContentNotFoundResponse(id);
        }
        return ResponseFactory.createResponseWithEntity(question);
    }
}
