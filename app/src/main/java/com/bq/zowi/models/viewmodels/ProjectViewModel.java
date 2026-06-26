package com.bq.zowi.models.viewmodels;

import androidx.annotation.Nullable;
import com.bq.zowi.models.Project;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ProjectViewModel {
    public String achievementId;
    public long blockadePendingMillis;
    public String imageResourceId;
    public boolean isComplete;
    public boolean isQuizBlocked;
    public String learningDescriptionResourceId;

    @Nullable
    public String projectHex;
    public String projectUrlResourceId;
    public ArrayList<TestQuestionViewModel> testQuestions;
    public String titleResourceId;

    public ProjectViewModel(String titleResourceId, String learningDescriptionResourceId, String imageResourceId, String projectUrlResourceId, ArrayList<TestQuestionViewModel> testQuestions, String achievementId, boolean isComplete, boolean isQuizBlocked, long blockadePendingMillis, @Nullable String projectHex) {
        this.titleResourceId = titleResourceId;
        this.learningDescriptionResourceId = learningDescriptionResourceId;
        this.imageResourceId = imageResourceId;
        this.projectUrlResourceId = projectUrlResourceId;
        this.testQuestions = testQuestions;
        this.achievementId = achievementId;
        this.isComplete = isComplete;
        this.isQuizBlocked = isQuizBlocked;
        this.blockadePendingMillis = blockadePendingMillis;
        this.projectHex = projectHex;
    }

    public static class TestQuestionViewModel {
        public ArrayList<TestAnswerViewModel> answers;
        public String questionResourceId;

        public TestQuestionViewModel(String questionResourceId, ArrayList<TestAnswerViewModel> answers) {
            this.questionResourceId = questionResourceId;
            this.answers = answers;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static TestQuestionViewModel testQuestionViewModelFromTestQuestion(Project.TestQuestion testQuestion) {
            ArrayList<TestAnswerViewModel> testAnswerViewModels = new ArrayList<>();
            for (Project.TestAnswer testAnswer : testQuestion.answers) {
                testAnswerViewModels.add(TestAnswerViewModel.testAnswerViewModelFromTestAnswer(testAnswer));
            }
            return new TestQuestionViewModel(testQuestion.questionResourceId, testAnswerViewModels);
        }
    }

    public static class TestAnswerViewModel {
        public String answerResourceId;
        public boolean isCorrect;

        public TestAnswerViewModel(String answerResourceId, boolean isCorrect) {
            this.answerResourceId = answerResourceId;
            this.isCorrect = isCorrect;
        }

        public static TestAnswerViewModel testAnswerViewModelFromTestAnswer(Project.TestAnswer testAnswer) {
            return new TestAnswerViewModel(testAnswer.answerResourceId, testAnswer.isCorrect);
        }
    }

    public static ProjectViewModel projectViewModelFromProject(Project project) {
        ArrayList<TestQuestionViewModel> testQuestionViewModels = new ArrayList<>();
        for (Project.TestQuestion testQuestion : project.testQuestions) {
            testQuestionViewModels.add(TestQuestionViewModel.testQuestionViewModelFromTestQuestion(testQuestion));
        }
        return new ProjectViewModel(project.titleResourceId, project.learningDescriptionResourceId, project.imageResourceId, project.projectUrlResourceId, testQuestionViewModels, project.achievementId, project.isCompleted, project.isQuizBlocked, project.blockadePendingMillis, project.projectHex);
    }
}
