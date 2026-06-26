package com.bq.zowi.controllers;

import com.bq.zowi.models.Project;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface ProjectController {
    public static final long PROJECT_QUIZ_BLOCKADE_MILLIS_ON_FAILURE = 600000;

    Single<Void> blockProjectQuiz(String str);

    Single<Project> getProject(String str);

    Single<Boolean> getProjectIsCompleted(String str);

    Single<Long> isProjectQuizBlocked(String str);

    Single<Void> resetAllProjects();

    Single<Void> setProjectCompleted(String str);
}
