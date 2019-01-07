package com.lyonguyen.esky.logic.dao;

import com.lyonguyen.esky.database.DataProvider;
import com.lyonguyen.esky.database.DataTable;
import com.lyonguyen.esky.logic.enums.ContributeMethod;
import com.lyonguyen.esky.logic.models.Contributor;
import com.lyonguyen.esky.logic.models.Lesson;
import com.lyonguyen.esky.logic.models.Question;

import java.util.List;

public class ContributorDAO extends DataAccessObject {

    public ContributorDAO(DataProvider dataProvider) {
        super(dataProvider);
    }

    public List<Contributor> getAll() {
        DataTable table = executeQuery("select * from contributors");
        List<Contributor> contributor = mapper.mapDataTable(table, Contributor.class);
        return encoder.encodeObject(contributor);
    }

    public Contributor get(String id) {
        Integer decodedId = encoder.decode(id);
        DataTable table = executeQuery("select * from contributors where id = ?", decodedId);
        if(table.isEmpty()) {
            return null;
        }
        Contributor contributor = mapper.mapDataRow(table.row(0), Contributor.class);
        return encoder.encodeObject(contributor);
    }

    public boolean contributeLesson(String id, ContributeMethod method, String note, boolean accepted, Lesson lesson, List<String> questionIds) {
        Integer decodedId = encoder.decode(id);
        Integer decodedLessonId = method == ContributeMethod.INSERT ? null : encoder.decode(lesson.getId());
        List<String> decodedQuestionIds = method == ContributeMethod.DELETE ? null : encoder.decode(questionIds);
        String compressedQuestionIds = encoder.compress(decodedQuestionIds, ",");
        int result = executeScalar(int.class, "call contribute_lesson(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                  decodedId, method.name(), note, accepted, decodedLessonId, lesson.getSubject(),
                                  lesson.getTitle(), lesson.getDescription(), lesson.getLevel(), compressedQuestionIds);
        return result != 0;
    }

    public boolean contributeQuestion(String id, ContributeMethod method, String note, boolean accepted, Question question) {
        Integer decodedId = encoder.decode(id);
        Integer decodedQuestionId = method == ContributeMethod.INSERT ? null : encoder.decode(question.getId());
        String compressedAnswers = encoder.compress(question.getAnswers());
        String compressedChoices = encoder.compress(question.getChoices());
        int result = executeScalar(int.class, "call contribute_question(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                  decodedId, method.name(), note, accepted, decodedQuestionId, question.getQuestion(),
                                  question.getPhrase(), question.getVoice(), question.getPicture(),
                                  question.getAnswerType().name(), compressedAnswers, compressedChoices,
                                  question.getDifficulty().name());
        return result != 0;
    }

}
