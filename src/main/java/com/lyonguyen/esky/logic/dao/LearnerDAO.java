package com.lyonguyen.esky.logic.dao;

import com.lyonguyen.esky.logic.models.Learner;
import com.lyonguyen.esky.logic.models.Lesson;
import com.lyonguyen.esky.database.DataProvider;
import com.lyonguyen.esky.database.DataTable;
import com.lyonguyen.esky.logic.models.LevelInfo;

import java.util.List;

public class LearnerDAO extends DataAccessObject {

    public LearnerDAO(DataProvider dataProvider) {
        super(dataProvider);
    }

    public boolean has(String id) {
        Integer decodedId = encoder.decode(id);
        Object result = executeScalar("select * from learners where id = ?", decodedId);
        return result != null;
    }

    public Learner get(String id) {
        Integer decodedId = encoder.decode(id);
        DataTable table = executeQuery("call get_learner(?)", decodedId);
        if(table.isEmpty()) {
            return null;
        }
        Learner learner = mapper.mapDataRow(table.row(0), Learner.class);
        return encoder.encodeObject(learner);
    }

    public List<Lesson> getLessons(String id) {
        Integer decodedId = encoder.decode(id);
        DataTable table = executeQuery("call get_learner_lessons(?)", decodedId);
        List<Lesson> lessons = mapper.mapDataTable(table, Lesson.class);
        return encoder.encodeObject(lessons);
    }

    public Lesson getLesson(String id, String lessonId) {
        Integer decodedId = encoder.decode(id);
        Integer decodedLessonId = encoder.decode(lessonId);
        DataTable table = executeQuery("call get_learner_lesson(?, ?)", decodedId, decodedLessonId);
        if(table.isEmpty()) {
            return null;
        }
        Lesson lesson = mapper.mapDataRow(table.row(0), Lesson.class);
        return encoder.encodeObject(lesson);
    }

    public boolean updateRemains(String id, String lessonId, List<String> remains) {
        Integer decodedId = encoder.decode(id);
        Integer decodedLessonId = encoder.decode(lessonId);
        List<String> decodedRemains = encoder.decode(remains);
        String compressedRemains = encoder.compress(decodedRemains);
        Integer result = executeUpdate("update learners_lessons set remains = ? where learner = ? and lesson = ?", compressedRemains, decodedId, decodedLessonId);
        return result != 0;
    }

    public LevelInfo getLevelInfo() {
        DataTable table = executeQuery("call get_level_info()");
        return mapper.mapDataRow(table.row(0), LevelInfo.class);
    }

    public boolean updateAnswered(String id, String lessonId, List<String> answered) {
        Integer decodedId = encoder.decode(id);
        Integer decodedLessonId = encoder.decode(lessonId);
        List<String> decodedAnswered = encoder.decode(answered);
        String compressedAnswered = encoder.compress(decodedAnswered);
        Integer result = executeUpdate("update learners_lessons set answered = ? where learner = ? and lesson = ?", compressedAnswered, decodedId, decodedLessonId);
        return result != 0;
    }

    public boolean passLesson(String id, String lessonId) {
        Integer decodedId = encoder.decode(id);
        Integer decodedLessonId = encoder.decode(lessonId);
        Integer result= executeUpdate("call pass_lesson(?, ?)", decodedId, decodedLessonId);
        return result != 0;
    }
}
