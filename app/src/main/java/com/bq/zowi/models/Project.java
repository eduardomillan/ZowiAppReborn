package com.bq.zowi.models;

import androidx.annotation.Nullable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Project {
    public String achievementId;
    public long blockadePendingMillis;
    public String id;
    public String imageResourceId;
    public boolean isCompleted;
    public boolean isQuizBlocked;
    public String learningDescriptionResourceId;

    @Nullable
    public String projectHex;
    public String projectUrlResourceId;
    public ArrayList<TestQuestion> testQuestions;
    public String titleResourceId;

    public Project(String id, String titleResourceId, String learningDescriptionResourceId, String imageResourceId, String projectUrlResourceId, ArrayList<TestQuestion> testQuestions, String achievementId, @Nullable String projectHex) {
        this.id = id;
        this.titleResourceId = titleResourceId;
        this.learningDescriptionResourceId = learningDescriptionResourceId;
        this.imageResourceId = imageResourceId;
        this.projectUrlResourceId = projectUrlResourceId;
        this.testQuestions = testQuestions;
        this.achievementId = achievementId;
        this.projectHex = projectHex;
    }

    public static class TestQuestion {
        public ArrayList<TestAnswer> answers;
        public String questionResourceId;

        public TestQuestion(String questionResourceId, ArrayList<TestAnswer> answers) {
            this.questionResourceId = questionResourceId;
            this.answers = answers;
        }
    }

    public static class TestAnswer {
        public String answerResourceId;
        public boolean isCorrect;

        public TestAnswer(String answerResourceId, boolean isCorrect) {
            this.answerResourceId = answerResourceId;
            this.isCorrect = isCorrect;
        }
    }
}
