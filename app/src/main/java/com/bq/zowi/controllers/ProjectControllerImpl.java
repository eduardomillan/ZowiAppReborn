package com.bq.zowi.controllers;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import com.bq.zowi.models.Project;
import com.bq.zowi.utils.FileReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func3;

/* JADX INFO: loaded from: classes.dex */
public class ProjectControllerImpl implements ProjectController {
    private static final String PROJECT_COMPLETENESS_SHARED_PREFERENCE_SUFFIX = "_project_completeness";
    private static final String PROJECT_QUIZ_BLOCKADE_SHARED_PREFERENCE_SUFFIX = "_project_quiz_blockade";
    private AssetManager assetManager;
    private SharedPreferences sharedPreferences;

    public ProjectControllerImpl(AssetManager assetManager, SharedPreferences sharedPreferences) {
        this.assetManager = assetManager;
        this.sharedPreferences = sharedPreferences;
    }

    @Override // com.bq.zowi.controllers.ProjectController
    public Single<Project> getProject(final String projectId) {
        return Single.zip(Single.create(new Single.OnSubscribe<Project>() { // from class: com.bq.zowi.controllers.ProjectControllerImpl.1
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Project> singleSubscriber) {
                String projectJsonString = FileReader.readFielAsString(ProjectControllerImpl.this.assetManager, "projects/" + projectId + ".json");
                Gson gson = new GsonBuilder().registerTypeAdapter(Project.class, new ProjectDeserializer()).create();
                Project project = (Project) gson.fromJson(projectJsonString, Project.class);
                singleSubscriber.onSuccess(project);
            }
        }), getProjectIsCompleted(projectId), isProjectQuizBlocked(projectId), new Func3<Project, Boolean, Long, Project>() { // from class: com.bq.zowi.controllers.ProjectControllerImpl.2
            @Override // rx.functions.Func3
            public Project call(Project project, Boolean projectIsCompleted, Long blockadePendingMillis) {
                project.isCompleted = projectIsCompleted.booleanValue();
                if (blockadePendingMillis.longValue() == -1) {
                    project.isQuizBlocked = false;
                    project.blockadePendingMillis = -1L;
                } else {
                    project.isQuizBlocked = true;
                    project.blockadePendingMillis = blockadePendingMillis.longValue();
                }
                return project;
            }
        });
    }

    @Override // com.bq.zowi.controllers.ProjectController
    public Single<Boolean> getProjectIsCompleted(final String projectId) {
        return Single.create(new Single.OnSubscribe<Boolean>() { // from class: com.bq.zowi.controllers.ProjectControllerImpl.3
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
                singleSubscriber.onSuccess(Boolean.valueOf(ProjectControllerImpl.this.sharedPreferences.getBoolean(ProjectControllerImpl.getSharefPrefRankingKeyForGame(projectId), false)));
            }
        });
    }

    @Override // com.bq.zowi.controllers.ProjectController
    public Single<Void> setProjectCompleted(final String projectId) {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.ProjectControllerImpl.4
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                SharedPreferences.Editor editor = ProjectControllerImpl.this.sharedPreferences.edit();
                editor.putBoolean(ProjectControllerImpl.getSharefPrefRankingKeyForGame(projectId), true);
                editor.commit();
                singleSubscriber.onSuccess(null);
            }
        });
    }

    @Override // com.bq.zowi.controllers.ProjectController
    public Single<Long> isProjectQuizBlocked(final String projectId) {
        return Single.create(new Single.OnSubscribe<Long>() { // from class: com.bq.zowi.controllers.ProjectControllerImpl.5
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Long> singleSubscriber) {
                long blockadeStartTime = ProjectControllerImpl.this.sharedPreferences.getLong(ProjectControllerImpl.getSharefPrefQuizBlockadeKeyForGame(projectId), -1L);
                if (blockadeStartTime == -1) {
                    singleSubscriber.onSuccess(-1L);
                    return;
                }
                Date currentDate = new Date();
                Long millisSinceLastBlockade = Long.valueOf(currentDate.getTime() - blockadeStartTime);
                if (millisSinceLastBlockade.longValue() < ProjectController.PROJECT_QUIZ_BLOCKADE_MILLIS_ON_FAILURE) {
                    singleSubscriber.onSuccess(Long.valueOf(ProjectController.PROJECT_QUIZ_BLOCKADE_MILLIS_ON_FAILURE - millisSinceLastBlockade.longValue()));
                } else {
                    singleSubscriber.onSuccess(-1L);
                }
            }
        });
    }

    @Override // com.bq.zowi.controllers.ProjectController
    public Single<Void> blockProjectQuiz(final String projectId) {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.ProjectControllerImpl.6
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                SharedPreferences.Editor editor = ProjectControllerImpl.this.sharedPreferences.edit();
                Date currentDate = new Date();
                editor.putLong(ProjectControllerImpl.getSharefPrefQuizBlockadeKeyForGame(projectId), currentDate.getTime());
                editor.commit();
                singleSubscriber.onSuccess(null);
            }
        });
    }

    @Override // com.bq.zowi.controllers.ProjectController
    public Single<Void> resetAllProjects() {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.ProjectControllerImpl.7
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                Map<String, ?> allPrefs = ProjectControllerImpl.this.sharedPreferences.getAll();
                SharedPreferences.Editor editor = ProjectControllerImpl.this.sharedPreferences.edit();
                for (String prefKey : allPrefs.keySet()) {
                    if (prefKey.endsWith(ProjectControllerImpl.PROJECT_COMPLETENESS_SHARED_PREFERENCE_SUFFIX) || prefKey.endsWith(ProjectControllerImpl.PROJECT_QUIZ_BLOCKADE_SHARED_PREFERENCE_SUFFIX)) {
                        editor.remove(prefKey);
                    }
                }
                editor.commit();
                singleSubscriber.onSuccess(null);
            }
        });
    }

    private class ProjectDeserializer implements JsonDeserializer<Project> {
        private ProjectDeserializer() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.JsonDeserializer
        public Project deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String id = jsonObject.get("id").getAsString();
            String titleResourceId = jsonObject.get("title").getAsString();
            String learningDescriptionResourceId = jsonObject.get("learning_description").getAsString();
            String imageResourceId = jsonObject.get("image").getAsString();
            String projectUrlResourceId = jsonObject.get("project_url").getAsString();
            ArrayList<Project.TestQuestion> testQuestions = getTestQuestionsByJsonArray(jsonObject.get("test").getAsJsonArray());
            String achievementId = jsonObject.get("achievement").getAsString();
            String projectHex = (jsonObject.get("project_hex") == null || jsonObject.get("project_hex").getAsString().length() <= 0) ? null : jsonObject.get("project_hex").getAsString();
            return new Project(id, titleResourceId, learningDescriptionResourceId, imageResourceId, projectUrlResourceId, testQuestions, achievementId, projectHex);
        }

        private ArrayList<Project.TestQuestion> getTestQuestionsByJsonArray(JsonArray testQuestionsJsonArray) {
            ArrayList<Project.TestQuestion> testQuestions = new ArrayList<>();
            for (JsonElement testQuestionJsonElement : testQuestionsJsonArray) {
                JsonObject testQuestionJsonObject = testQuestionJsonElement.getAsJsonObject();
                String questionResourceId = testQuestionJsonObject.get("question").getAsString();
                JsonArray answersJsonArray = testQuestionJsonObject.get("answers").getAsJsonArray();
                ArrayList<Project.TestAnswer> answers = new ArrayList<>();
                for (JsonElement answerJsonElement : answersJsonArray) {
                    JsonObject answerJsonObject = answerJsonElement.getAsJsonObject();
                    String answerResourceId = answerJsonObject.get("answer").getAsString();
                    Boolean isCorrect = Boolean.valueOf(answerJsonObject.get("is_correct").getAsBoolean());
                    answers.add(new Project.TestAnswer(answerResourceId, isCorrect.booleanValue()));
                }
                testQuestions.add(new Project.TestQuestion(questionResourceId, answers));
            }
            return testQuestions;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSharefPrefRankingKeyForGame(String projectId) {
        return projectId + PROJECT_COMPLETENESS_SHARED_PREFERENCE_SUFFIX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSharefPrefQuizBlockadeKeyForGame(String projectId) {
        return projectId + PROJECT_QUIZ_BLOCKADE_SHARED_PREFERENCE_SUFFIX;
    }
}
