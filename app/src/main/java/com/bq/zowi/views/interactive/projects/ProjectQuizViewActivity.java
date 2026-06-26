package com.bq.zowi.views.interactive.projects;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bq.analytics.hit.Event;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.components.QuizView;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogAchievement;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogFailure;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogSuccess;
import com.bq.zowi.controllers.ProjectController;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.models.viewmodels.ProjectViewModel;
import com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenter;
import com.bq.zowi.utils.AnimationUtils;
import com.bq.zowi.utils.ResourceResolver;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import com.bq.zowi.wireframes.projects.ProjectQuizWireframe;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class ProjectQuizViewActivity extends InteractiveBaseActivity<ProjectQuizPresenter> implements ProjectQuizView, QuizView.QuizEventListener {
    private MakerBoxDialogAchievement achievementLayout;
    private MakerBoxDialogFailure failureLayout;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private ImageView projectCompletedImageView;
    private String projectId;
    private QuizView quizView;
    private MakerBoxDialogSuccess successWithoutAchievementLayout;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_quiz_view);
        this.projectId = getIntent().getStringExtra(ProjectViewActivity.PROJECT_ID_EXTRA);
        this.quizView = (QuizView) findViewById(R.id.activity_project_quiz_view_component);
        this.projectCompletedImageView = (ImageView) findViewById(R.id.activity_project_quiz_completed_imageview);
        this.progressBar = (ProgressBar) findViewById(R.id.activity_project_quiz_progressbar);
        this.progressTextView = (TextView) findViewById(R.id.activity_project_quiz_progress_textview);
        View progressBarContainer = findViewById(R.id.activity_project_quiz_progressbar_makerbox);
        progressBarContainer.setVisibility(getResources().getBoolean(R.bool.show_quiz_progress_bar) ? 0 : 8);
        this.quizView.setQuizEventListener(this);
        Button homeButton = (Button) findViewById(R.id.activity_project_quiz_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectQuizViewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ProjectQuizPresenter) ProjectQuizViewActivity.this.getPresenter()).homeButtonPressed();
            }
        });
        this.achievementLayout = (MakerBoxDialogAchievement) findViewById(R.id.minigame_achievement_layout);
        this.successWithoutAchievementLayout = (MakerBoxDialogSuccess) findViewById(R.id.project_success_without_achievement_layout);
        this.failureLayout = (MakerBoxDialogFailure) findViewById(R.id.project_failure_layout);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ((ProjectQuizPresenter) getPresenter()).loadProjectQuiz(this.projectId);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public ProjectQuizPresenter resolvePresenter() {
        ProjectQuizPresenter presenter = AndroidDependencyInjector.getInstance().provideProjectQuizPresenter();
        ProjectQuizWireframe wireframe = AndroidDependencyInjector.getInstance().provideProjectQuizWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectQuizView
    public void paintProjectQuiz(ProjectViewModel project) {
        if (project.isComplete) {
            this.projectCompletedImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.project_done_icon));
        } else {
            this.projectCompletedImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.project_not_done_icon));
        }
        this.quizView.configureQuizQuestions(project.testQuestions);
        this.progressBar.setProgress(0);
        this.progressTextView.setText("0 / " + project.testQuestions.size());
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectQuizView
    public void showProjectQuizLoadingError() {
        this.eduBar.show(R.string.projects_loading_error);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseView
    public void showAchievementUnlock(AchievementViewModel achievement) {
        this.achievementLayout.setAchievementTitle(ResourceResolver.getStringByResourceId(achievement.getTitleResourceId(), getResources(), getPackageName()));
        this.achievementLayout.setAchievementDescription(ResourceResolver.getStringByResourceId(achievement.getDescriptionResourceId(), getResources(), getPackageName()));
        this.achievementLayout.setAchievementDrawable(ResourceResolver.getDrawableByResourceId(achievement.getBadgeImageResourceId(), this));
        this.achievementLayout.setOnContinueButtonClickedListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectQuizViewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ProjectQuizViewActivity.this.achievementLayout.setVisibility(8);
                ((ProjectQuizPresenter) ProjectQuizViewActivity.this.getPresenter()).achievementContinueButtonClicked();
            }
        });
        this.achievementLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectQuizView
    public void showFailureFeedback() {
        this.failureLayout.setFailureDescriptionText(getString(R.string.projects_dialog_failure_description));
        this.failureLayout.setFailureMainText(getString(R.string.projects_dialog_failure_wrong_answer));
        Date date = new Date(ProjectController.PROJECT_QUIZ_BLOCKADE_MILLIS_ON_FAILURE);
        DateFormat formatter = new SimpleDateFormat("mm");
        String dateFormatted = formatter.format(date);
        this.failureLayout.setFailureContentText(getString(R.string.projects_dialog_failure_wrong_answer_penalty, new Object[]{dateFormatted}));
        this.failureLayout.setOnContinueButtonClickedListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectQuizViewActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProjectQuizViewActivity.super.onBackPressed();
            }
        });
        this.failureLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectQuizView
    public void showQuizCompleteWithoutAchievement() {
        this.successWithoutAchievementLayout.setSuccessDescriptionText(getString(R.string.projects_dialog_success_congratulations_description));
        this.successWithoutAchievementLayout.setSuccessMainText(getString(R.string.makerbox_dialog_success_congratulations));
        this.successWithoutAchievementLayout.setOnContinueButtonClickedListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectQuizViewActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ProjectQuizPresenter) ProjectQuizViewActivity.this.getPresenter()).achievementContinueButtonClicked();
            }
        });
        this.successWithoutAchievementLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.components.QuizView.QuizEventListener
    public void onSuccess(int successQuestionIndex, int totalQuestions) {
        AnimationUtils.animateProgressBarToProgress(this.progressBar, (successQuestionIndex * 100) / totalQuestions);
        this.progressTextView.setText(successQuestionIndex + " / " + totalQuestions);
        getAnalyticsController().send(new Event("Project" + this.projectId, AnalyticsUtils.EVENT_PROJECT_QUIZ_QUESTION_PREFIX + successQuestionIndex, AnalyticsUtils.EVENT_PROJECT_QUIZ_QUESTION_RIGHT, 0L));
    }

    @Override // com.bq.zowi.components.QuizView.QuizEventListener
    public void onError(int errorQuestionIndex, int totalQuestions) {
        ((ProjectQuizPresenter) getPresenter()).quizFailure(this.projectId);
        getAnalyticsController().send(new Event("Project" + this.projectId, AnalyticsUtils.EVENT_PROJECT_QUIZ_QUESTION_PREFIX + errorQuestionIndex, AnalyticsUtils.EVENT_PROJECT_QUIZ_QUESTION_WRONG, 0L));
    }

    @Override // com.bq.zowi.components.QuizView.QuizEventListener
    public void onQuizComplete() {
        this.projectCompletedImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.project_done_icon));
        ((ProjectQuizPresenter) getPresenter()).projectCompleted(this.projectId);
    }
}
