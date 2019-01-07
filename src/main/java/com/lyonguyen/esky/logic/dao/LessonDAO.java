package com.lyonguyen.esky.logic.dao;

import com.lyonguyen.esky.database.DataProvider;
import com.lyonguyen.esky.database.DataTable;
import com.lyonguyen.esky.logic.models.Lesson;
import com.lyonguyen.esky.logic.models.Question;

import java.util.List;

public class LessonDAO extends DataAccessObject {

    public LessonDAO(DataProvider dataProvider) {
        super(dataProvider);
    }

    public boolean has(String id) {
        Integer decodedId = encoder.decode(id);
        Object result = executeScalar("select id from lessons where id = ?", decodedId);
        return result != null;
    }

    public Lesson get(String id) {
        Integer decodedId = encoder.decode(id);
        DataTable table = executeQuery("select * from lessons where id = ? and deleted = 0", decodedId);
        if (table.isEmpty()) {
            return null;
        }
        Lesson lesson = mapper.mapDataRow(table.row(0), Lesson.class);
        return encoder.encodeObject(lesson);
    }

    public List<Question> getQuestions(String id) {
        Integer decodedId = encoder.decode(id);
        DataTable table = executeQuery("call get_lesson_questions(?)", decodedId);
        List<Question> questions = mapper.mapDataTable(table, Question.class);
        return encoder.encodeObject(questions);
    }
}
