package com.lyonguyen.esky.ws.providers;

import com.lyonguyen.esky.ws.services.*;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(new AccountService()).in(Singleton.class);
        bind(new AuthenticationService()).in(Singleton.class);
        bind(new LearnerService()).in(Singleton.class);
        bind(new LessonService()).in(Singleton.class);
        bind(new QuestionService()).in(Singleton.class);
        bind(new ContributorService()).in(Singleton.class);
        bind(new TestService()).in(Singleton.class);
    }
}
