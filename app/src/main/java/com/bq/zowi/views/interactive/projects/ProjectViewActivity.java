package com.bq.zowi.views.interactive.projects;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.components.makerboxdialogs.MakerBoxButton;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialog;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.models.viewmodels.ProjectViewModel;
import com.bq.zowi.presenters.interactive.projects.ProjectPresenter;
import com.bq.zowi.utils.AnimationUtils;
import com.bq.zowi.utils.ResourceResolver;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import com.bq.zowi.wireframes.projects.ProjectWireframe;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class ProjectViewActivity extends InteractiveBaseActivity<ProjectPresenter> implements ProjectView {
    public static final String PROJECT_ID_EXTRA = "project_id_extra";
    private CountDownTimer blockedQuizCountdownTimer;
    private TextView descriptionTextView;
    private ProgressBar hexInstallationProgressBar;
    private TextView installHexButtonTextView;
    private TextView installHexDescriptionTextView;
    private MakerBoxDialog installHexMakerbox;
    private TextView installingHexDescriptionTextView;
    private boolean isQuizBlocked = false;
    private TextView linkTextView;
    private ImageView projectCompletedImageView;
    private String projectId;
    private ImageView projectImageView;
    private MakerBoxButton runTestButton;
    private TextView titleTextView;
    private ViewPager viewPager;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_project_view", R.layout.activity_project_view);
        this.projectId = getIntent().getStringExtra(PROJECT_ID_EXTRA);
        this.viewPager = (ViewPager) findResolvedView("activity_project_view_pager", R.id.activity_project_view_pager);
        int margin = (int) (getResources().getDimension(R.dimen.sections_pager_horizontal_margin) * 2.0f);
        if (this.viewPager != null) {
            this.viewPager.setPageMargin(-margin);
        }
        this.projectCompletedImageView = (ImageView) findResolvedView("activity_project_completed_imageview", R.id.activity_project_completed_imageview);
        this.installHexMakerbox = (MakerBoxDialog) findResolvedView("activity_project_install_hex_layout", R.id.activity_project_install_hex_layout);
        this.hexInstallationProgressBar = (ProgressBar) findResolvedView("activity_project_install_hex_progressbar", R.id.activity_project_install_hex_progressbar);
        this.titleTextView = (TextView) findResolvedView("activity_project_title_textview", R.id.activity_project_title_textview);
        this.descriptionTextView = (TextView) findResolvedView("activity_project_description_textview", R.id.activity_project_description_textview);
        this.linkTextView = (TextView) findResolvedView("activity_project_link_textview", R.id.activity_project_link_textview);
        this.projectImageView = (ImageView) findResolvedView("activity_project_image", R.id.activity_project_image);
        this.installHexDescriptionTextView = (TextView) findResolvedView("activity_project_install_hex_description", R.id.activity_project_install_hex_description);
        this.installHexButtonTextView = (TextView) findResolvedView("activity_project_install_hex_button_textview", R.id.activity_project_install_hex_button_textview);
        this.installingHexDescriptionTextView = (TextView) findResolvedView("activity_project_installing_hex_description_textview", R.id.activity_project_installing_hex_description_textview);
        this.runTestButton = (MakerBoxButton) findResolvedView("activity_project_run_test_button", R.id.activity_project_run_test_button);
        if (this.runTestButton != null) {
            this.runTestButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectViewActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((ProjectPresenter) ProjectViewActivity.this.getPresenter()).loadProjectQuiz(ProjectViewActivity.this.projectId);
                }
            });
        }
        ImageButton homeButton = (ImageButton) findResolvedView("activity_project_home_button", R.id.activity_project_home_button);
        if (homeButton != null) {
            homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectViewActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((ProjectPresenter) ProjectViewActivity.this.getPresenter()).homeButtonPressed();
                }
            });
        }
        this.zowiDependantViews = new View[]{this.installHexButtonTextView};
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ((ProjectPresenter) getPresenter()).loadProject(this.projectId);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getAnalyticsController().send(new ZowiScreen(this, "Project" + this.projectId));
        ((ProjectPresenter) getPresenter()).checkProjectCompleteness(this.projectId);
        ((ProjectPresenter) getPresenter()).checkProjectQuizBlocked(this.projectId);
    }

    @Override // com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (this.blockedQuizCountdownTimer != null) {
            this.blockedQuizCountdownTimer.cancel();
            this.blockedQuizCountdownTimer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public ProjectPresenter resolvePresenter() {
        ProjectPresenter presenter = AndroidDependencyInjector.getInstance().provideProjectPresenter();
        ProjectWireframe wireframe = AndroidDependencyInjector.getInstance().provideProjectWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void paintProject(final ProjectViewModel project, String zowiName) {
        ProjectPagerAdapter projectPagerAdapter;
        paintProjectCompleteness(project.isComplete);
        this.installHexDescriptionTextView.setText(getString(R.string.projects_reprogram_description, new Object[]{zowiName}));
        this.titleTextView.setText(ResourceResolver.getStringByResourceId(project.titleResourceId, getResources(), getPackageName()));
        this.descriptionTextView.setText(ResourceResolver.getTextByResourceId(project.learningDescriptionResourceId, getResources(), getPackageName()));
        this.linkTextView.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectViewActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse(ResourceResolver.getStringByResourceId(project.projectUrlResourceId, ProjectViewActivity.this.getResources(), ProjectViewActivity.this.getPackageName())));
                ProjectViewActivity.this.startActivity(i);
            }
        });
        paintProjectQuizBlocked(project.isQuizBlocked, project.blockadePendingMillis);
        this.projectImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId(project.imageResourceId, getApplicationContext()));
        if (project.projectHex != null) {
            this.installHexButtonTextView.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectViewActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((ProjectPresenter) ProjectViewActivity.this.getPresenter()).manageLowBatteryForInstallingProjectFirmware(project.projectHex);
                }
            });
            projectPagerAdapter = new ProjectPagerAdapter(true);
        } else {
            projectPagerAdapter = new ProjectPagerAdapter(false);
        }
        this.viewPager.setOffscreenPageLimit(this.viewPager.getChildCount());
        this.viewPager.setAdapter(projectPagerAdapter);
        this.installingHexDescriptionTextView.setText(getString(R.string.projects_reprogramming_progress_dialog_description, new Object[]{zowiName}));
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void showProjectLoadingError() {
        this.eduBar.show(R.string.projects_loading_error);
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void paintProjectCompleteness(boolean isComplete) {
        if (isComplete) {
            this.projectCompletedImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.project_done_icon));
        } else {
            this.projectCompletedImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.project_not_done_icon));
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.bq.zowi.views.interactive.projects.ProjectViewActivity$5] */
    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void paintProjectQuizBlocked(boolean isProjectQuizBlocked, long blockadePendingMillis) {
        if (isProjectQuizBlocked) {
            this.isQuizBlocked = true;
            this.blockedQuizCountdownTimer = new CountDownTimer(blockadePendingMillis, 1000L) { // from class: com.bq.zowi.views.interactive.projects.ProjectViewActivity.5
                @Override // android.os.CountDownTimer
                public void onTick(long millisUntilFinished) {
                    Date date = new Date(millisUntilFinished);
                    DateFormat formatter = new SimpleDateFormat("mm:ss");
                    String dateFormatted = formatter.format(date);
                    ProjectViewActivity.this.runTestButton.setText(ProjectViewActivity.this.getString(R.string.projects_blocked_quiz_run_test_button, new Object[]{dateFormatted}));
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    ProjectViewActivity.this.isQuizBlocked = false;
                    ProjectViewActivity.this.runTestButton.setText(ProjectViewActivity.this.getString(R.string.projects_run_test_button));
                    ProjectViewActivity.this.manageRuntestButtonEnabled();
                }
            }.start();
        }
        manageRuntestButtonEnabled();
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void showHexLoadingProgress() {
        this.installHexMakerbox.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void hideHexLoadingProgress() {
        this.installHexMakerbox.setVisibility(8);
        AnimationUtils.animateProgressBarToProgress(this.hexInstallationProgressBar, 0);
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void setHexLoadingProgressValue(int progressValue) {
        AnimationUtils.animateProgressBarToProgress(this.hexInstallationProgressBar, progressValue);
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void showHexLoadingSuccess(String zowiName) {
        this.firmwareInstallationSuccessDescriptionTextView.setText(getString(R.string.reprogram_fw_success_dialog_description, new Object[]{zowiName}));
        this.firmwareInstallationSuccessContinueButton.setText(getString(R.string.reprogram_fw_success_dialog_button));
        this.firmwareInstalationSuccessDialog.setVisibility(0);
        ((ProjectPresenter) getPresenter()).checkAndManageZowiAppInstalled();
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void showHexLoadingError(String zowiName, boolean zowiStillConnected, final String projectHex) {
        if (zowiStillConnected) {
            this.firmwareInstallationErrorTitleTextView.setText(getString(R.string.firmware_installation_success_dialog_error_still_connected_title));
            this.firmwareInstallationErrorDescriptionTextView.setText(getString(R.string.reprogram_fw_error_still_connected_description, new Object[]{zowiName}));
        } else {
            this.firmwareInstallationErrorTitleTextView.setText(getString(R.string.firmware_installation_success_dialog_error_not_connected_title, new Object[]{zowiName}));
            this.firmwareInstallationErrorDescriptionTextView.setText(getString(R.string.reprogram_fw_error_not_connected_description, new Object[]{zowiName}));
        }
        this.firmwareInstallationErrorRetryButton.setText(getString(R.string.reprogram_fw_retry_button));
        this.firmwareInstallationErrorRetryButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectViewActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProjectViewActivity.this.firmwareInstalationErrorDialog.setVisibility(8);
                ((ProjectPresenter) ProjectViewActivity.this.getPresenter()).manageLowBatteryForInstallingProjectFirmware(projectHex);
            }
        });
        this.firmwareInstalationErrorDialog.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void updateNotificationOnProjectHextInstallationSuccess() {
        updateNotificationOnFwInstallationSuccess();
        this.isAltered = true;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.projects.ProjectView
    public void showLowBatteryForInstallingProjectFirmwareDialog(String zowiName, final String projectHex) {
        this.lowBatteryForInstallingFwTitle.setText(getString(R.string.low_battery_when_installing_fw_dialog_title, new Object[]{zowiName}));
        this.lowBatteryForInstallingFwDescription.setText(getString(R.string.low_battery_when_installing_fw_reprogram_dialog_description, new Object[]{zowiName}));
        this.lowBatteryForInstallingFwButtonOk.setText(R.string.calibration_warning_continue_button);
        this.lowBatteryForInstallingFwButtonOk.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectViewActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProjectViewActivity.this.lowBatteryForInstallingFwDialog.setVisibility(8);
                ((ProjectPresenter) ProjectViewActivity.this.getPresenter()).loadProjectHexToZowi(projectHex);
            }
        });
        this.lowBatteryForInstallingFwButtonCancel.setText(R.string.calibration_warning_cancel_button);
        this.lowBatteryForInstallingFwButtonCancel.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.projects.ProjectViewActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProjectViewActivity.this.lowBatteryForInstallingFwDialog.setVisibility(8);
            }
        });
        this.lowBatteryForInstallingFwDialog.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void manageRuntestButtonEnabled() {
        if (!this.isQuizBlocked) {
            this.runTestButton.setEnabled(true);
        } else {
            this.runTestButton.setEnabled(false);
        }
    }

    private class ProjectPagerAdapter extends PagerAdapter {
        private int numPages;
        private boolean showReprogramPage;

        private class ProjectPages {
            public static final int DESCRIPTION = 0;
            public static final int IMAGE = 1;
            public static final int REPROGRAM_OR_RUN_TEST = 2;
            public static final int RUN_TEST = 3;

            private ProjectPages() {
            }
        }

        public ProjectPagerAdapter(boolean showReprogramPage) {
            this.numPages = 2;
            this.showReprogramPage = false;
            this.showReprogramPage = showReprogramPage;
            if (showReprogramPage) {
                this.numPages = ProjectViewActivity.this.viewPager.getChildCount();
            } else {
                this.numPages = ProjectViewActivity.this.viewPager.getChildCount() - 1;
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup collection, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.activity_project_description_layout;
                    break;
                case 1:
                    resId = R.id.activity_project_image_layout;
                    break;
                case 2:
                    if (this.showReprogramPage) {
                        resId = R.id.activity_project_reprogram_layout;
                    } else {
                        resId = R.id.activity_project_run_test_layout;
                    }
                    break;
                case 3:
                    resId = R.id.activity_project_run_test_layout;
                    break;
            }
            return collection.findViewById(resId);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.numPages;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
