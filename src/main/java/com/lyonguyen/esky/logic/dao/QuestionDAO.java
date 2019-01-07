package com.lyonguyen.esky.logic.dao;

import com.lyonguyen.esky.database.DataProvider;
import com.lyonguyen.esky.database.DataTable;
import com.lyonguyen.esky.logic.models.Question;

import java.util.List;

public class QuestionDAO extends DataAccessObject {

    public QuestionDAO(DataProvider dataProvider) {
        super(dataProvider);
    }

    public boolean has(String id) {
        Integer decodedId = encoder.decode(id);
        Object result = executeScalar("select id from questions where id = ? and deleted = 0", decodedId);
        return result != null;
    }

    public List<Question> getAll() {
        DataTable table = executeQuery("select * from questions where deleted = 0");
        List<Question> questions = mapper.mapDataTable(table, Question.class);
        return encoder.encodeObject(questions);
    }

    public Question get(String id) {
        Integer decodedId = encoder.decode(id);
        DataTable table = executeQuery("select * from questions where id = ? and deleted = 0", decodedId);
        if(table.isEmpty()) {
            return null;
        }
        Question question = mapper.mapDataRow(table.row(0), Question.class);
        return encoder.encodeObject(question);
    }

    public String insert(Question question) {
        String compressedAnswers = encoder.compress(question.getAnswers());
        String compressedChoices = encoder.compress(question.getChoices());
        int result = executeUpdate("call insert_questions(?, ?, ?, ?, ?, ? ,? ,?)",
                question.getQuestion(), question.getPhrase(), question.getVoice(), question.getPicture(),
                question.getAnswerType(), compressedAnswers, compressedChoices, question.getDifficulty());
        if (result == 0) {
            return null;
        }
        return encoder.encode(result);
    }

    public boolean update(Question question) {
        Integer decodedId = encoder.decode(question.getId());
        String compressedAnswers = encoder.compress(question.getAnswers());
        String compressedChoices = encoder.compress(question.getChoices());
        int result = executeUpdate("call update_questions(?, ?, ?, ?, ?, ?, ? ,? ,?)",
                                    decodedId, question.getQuestion(), question.getPhrase(), question.getVoice(), question.getPicture(),
                                    question.getAnswerType(), compressedAnswers, compressedChoices, question.getDifficulty());
        return result != 0;
    }

}
