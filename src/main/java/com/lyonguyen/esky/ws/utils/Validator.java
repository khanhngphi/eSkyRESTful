package com.lyonguyen.esky.ws.utils;

import com.lyonguyen.esky.logic.mapper.DataEncoder;
import com.lyonguyen.esky.logic.models.Lesson;
import com.lyonguyen.esky.logic.models.Question;

import java.util.List;

public class Validator {

    private static String emailRegex = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    private static String usernameRegex = "^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$";

    private static String sha256Regex = "[A-Fa-f0-9]{64}";

    private static DataEncoder encoder = new DataEncoder();

    private Validator() {}

    public static boolean email(String email) {
        return email.matches(emailRegex);
    }

    public static boolean username(String username) {
        return username.matches(usernameRegex);
    }

    public static boolean name(String name) {
        return name.trim().length() > 0;
    }

    public static boolean password(String password) {
        return password.matches(sha256Regex);
    }

    public static boolean lesson(Lesson lesson) {
        if (lesson == null) {
            return false;
        }
        if (lesson.getSubject().isEmpty() || lesson.getTitle().isEmpty() || lesson.getDescription().isEmpty()) {
            return false;
        }
        if (lesson.getSubject().length() > 50 || lesson.getTitle().length() > 50 || lesson.getDescription().length() > 100
                || lesson.getLevel() < 1 || lesson.getLevel() > 100) {
            return false;
        }
        return true;
    }

    public static boolean question(Question question) {
        if (question == null) {
            return false;
        }
        if (question.getPhrase().isEmpty() && question.getVoice().isEmpty() && question.getPicture().isEmpty()
                || question.getAnswerType() == null || question.getDifficulty() == null) {
            return false;
        }
        String compressedAnswers = encoder.compress(question.getAnswers());
        String compressedChoices = encoder.compress(question.getChoices());
        if (question.getQuestion().length() > 100 || question.getPhrase().length() > 100
                || question.getVoice().length() > 100 || question.getPicture().length() > 100
                || compressedAnswers!= null && compressedAnswers.length() > 512 || compressedChoices != null && compressedChoices.length() > 512) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(List<String> list) {
        if (list.isEmpty()) {
            return true;
        }
        return list.stream().allMatch(str -> str.isEmpty());
    }
}
