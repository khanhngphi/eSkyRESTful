package com.lyonguyen.esky.logic.dao;

import com.lyonguyen.esky.database.DataProvider;

public class DatabaseContext {

    private static final String URL = "jdbc:mysql://localhost:3306/esky?autoReconnect=true&useSSL=false";

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "1234";

    private DataProvider dataProvider;

    private static DatabaseContext instance = new DatabaseContext();

    private DatabaseContext() {
        dataProvider = new DataProvider(URL, USER_NAME, PASSWORD);
    }

    public static DatabaseContext getInstance() {
        return instance;
    }

    public AccountDAO getAccountDAO() {
        return new AccountDAO(dataProvider);
    }

    public LearnerDAO getLearnerDAO() {
        return new LearnerDAO(dataProvider);
    }

    public LessonDAO getLessonDAO() {
        return new LessonDAO(dataProvider);
    }

    public QuestionDAO getQuestionDAO() {
        return new QuestionDAO(dataProvider);
    }

    public ContributorDAO getContributorDAO() { return new ContributorDAO(dataProvider); }

}
