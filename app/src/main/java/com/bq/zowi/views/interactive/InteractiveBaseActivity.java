package com.bq.zowi.views.interactive;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.core.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.components.EduBar;
import com.bq.zowi.components.makerboxdialogs.MakerBoxButton;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialog;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogAchievement;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.utils.ResourceResolver;
import com.bq.zowi.views.BaseActivity;

/* JADX INFO: loaded from: classes.dex */
public abstract class InteractiveBaseActivity<T extends InteractiveBasePresenter<? extends InteractiveBaseView, ?>> extends BaseActivity<T> implements InteractiveBaseView {
    private MakerBoxDialogAchievement achievementLayout;
    private ProgressBar connectingProgressBar;
    private MakerBoxDialog corruptedFirmwareDetectedDialog;
    private TextView corruptedFirmwareDetectedDialogCancelTextView;
    private MakerBoxButton corruptedFirmwareDetectedDialogInstallButton;
    protected EduBar eduBar;
    protected MakerBoxDialog firmwareInstalationErrorDialog;
    protected MakerBoxDialog firmwareInstalationSuccessDialog;
    protected TextView firmwareInstallationErrorDescriptionTextView;
    protected MakerBoxButton firmwareInstallationErrorRetryButton;
    protected TextView firmwareInstallationErrorTitleTextView;
    protected MakerBoxButton firmwareInstallationSuccessContinueButton;
    protected TextView firmwareInstallationSuccessDescriptionTextView;
    private ProgressBar firmwareUpdateProgressBar;
    private MakerBoxDialog firmwareUpdatingDialog;
    private MakerBoxButton installFactoryFirmwareButton;
    protected TextView lowBatteryForInstallingFwButtonCancel;
    protected MakerBoxButton lowBatteryForInstallingFwButtonOk;
    protected TextView lowBatteryForInstallingFwDescription;
    protected MakerBoxDialog lowBatteryForInstallingFwDialog;
    protected TextView lowBatteryForInstallingFwTitle;
    protected MakerBoxDialog restoreFirmwareDialog;
    protected TextView restoreFirmwareDialogDescription;
    protected TextView restoreFirmwareDialogTitle;
    private Button zowiConnectButton;
    protected View[] zowiDependantViews;
    private Button zowiFactoryResetButton;
    private Button zowiLaunchWizardButton;
    private TextView zowiNameTextView;
    private ImageView zowiStatusImageView;
    private TextView zowiStatusTextView;
    private boolean shouldShowConnectingSpinner = true;
    private boolean isConnected = false;
    private boolean isConnecting = false;
    private boolean isLowBattery = false;
    private boolean isDemoMode = false;
    protected boolean isAltered = false;
    private boolean isInstallingFw = false;

    @Override // com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.still);
    }

    @Override // com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        View notificationBarContainer = findResolvedView("notification_bar_container", R.id.notification_bar_container);
        if (notificationBarContainer != null) {
            notificationBarContainer.setOnTouchListener(new View.OnTouchListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
        this.zowiNameTextView = (TextView) findResolvedView("notification_bar_zowi_name_textview", R.id.notification_bar_zowi_name_textview);
        this.zowiStatusTextView = (TextView) findResolvedView("notification_bar_zowi_status_textview", R.id.notification_bar_zowi_status_textview);
        this.zowiStatusImageView = (ImageView) findResolvedView("notification_bar_imageview", R.id.notification_bar_imageview);
        this.zowiConnectButton = (Button) findResolvedView("notification_bar_action_connect", R.id.notification_bar_action_connect);
        this.zowiFactoryResetButton = (Button) findResolvedView("notification_bar_action_factory_reset", R.id.notification_bar_action_factory_reset);
        this.zowiLaunchWizardButton = (Button) findResolvedView("notification_bar_action_launch_wizard", R.id.notification_bar_action_launch_wizard);
        this.connectingProgressBar = (ProgressBar) findResolvedView("notification_bar_connecting_progressbar", R.id.notification_bar_connecting_progressbar);
        this.eduBar = (EduBar) findResolvedView("notification_edubar", R.id.notification_edubar);
        this.achievementLayout = (MakerBoxDialogAchievement) findResolvedView("minigame_achievement_layout", R.id.minigame_achievement_layout);
        this.restoreFirmwareDialog = (MakerBoxDialog) findResolvedView("notification_restore_firmware_dialog", R.id.notification_restore_firmware_dialog);
        this.restoreFirmwareDialogTitle = (TextView) findResolvedView("notification_restore_firmware_dialog_title", R.id.notification_restore_firmware_dialog_title);
        this.restoreFirmwareDialogDescription = (TextView) findResolvedView("notification_restore_firmware_dialog_description", R.id.notification_restore_firmware_dialog_description);
        this.installFactoryFirmwareButton = (MakerBoxButton) findResolvedView("notification_install_factory_firmware_button", R.id.notification_install_factory_firmware_button);
        if (this.installFactoryFirmwareButton != null) {
            this.installFactoryFirmwareButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((InteractiveBasePresenter) InteractiveBaseActivity.this.getPresenter()).installFactoryFirmware(false);
                }
            });
        }
        View installFactoryFirmwareButtonCancel = findResolvedView("notification_install_factory_firmware_button_cancel", R.id.notification_install_factory_firmware_button_cancel);
        if (installFactoryFirmwareButtonCancel != null) {
            installFactoryFirmwareButtonCancel.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    InteractiveBaseActivity.this.restoreFirmwareDialog.setVisibility(8);
                }
            });
        }
        this.lowBatteryForInstallingFwDialog = (MakerBoxDialog) findResolvedView("notification_low_battery_dialog", R.id.notification_low_battery_dialog);
        this.lowBatteryForInstallingFwTitle = (TextView) findResolvedView("notification_low_battery_dialog_title", R.id.notification_low_battery_dialog_title);
        this.lowBatteryForInstallingFwDescription = (TextView) findResolvedView("notification_low_battery_dialog_description", R.id.notification_low_battery_dialog_description);
        this.lowBatteryForInstallingFwButtonOk = (MakerBoxButton) findResolvedView("notification_low_battery_dialog_button_ok", R.id.notification_low_battery_dialog_button_ok);
        this.lowBatteryForInstallingFwButtonCancel = (TextView) findResolvedView("notification_low_battery_dialog_button_cancel", R.id.notification_low_battery_dialog_button_cancel);
        if (this.lowBatteryForInstallingFwButtonCancel != null) {
            this.lowBatteryForInstallingFwButtonCancel.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    InteractiveBaseActivity.this.lowBatteryForInstallingFwDialog.setVisibility(8);
                }
            });
        }
        this.firmwareUpdatingDialog = (MakerBoxDialog) findResolvedView("notification_firmware_updating", R.id.notification_firmware_updating);
        this.firmwareUpdateProgressBar = (ProgressBar) findResolvedView("notification_firmware_updated_progressbar", R.id.notification_firmware_updated_progressbar);
        this.firmwareInstalationSuccessDialog = (MakerBoxDialog) findResolvedView("notification_firmware_installation_success", R.id.notification_firmware_installation_success);
        this.firmwareInstallationSuccessDescriptionTextView = (TextView) findResolvedView("notification_firmware_installation_success_description_textview", R.id.notification_firmware_installation_success_description_textview);
        this.firmwareInstalationErrorDialog = (MakerBoxDialog) findResolvedView("notification_firmware_installation_error", R.id.notification_firmware_installation_error);
        this.firmwareInstallationErrorTitleTextView = (TextView) findResolvedView("notification_firmware_installation_error_title_textview", R.id.notification_firmware_installation_error_title_textview);
        this.firmwareInstallationErrorDescriptionTextView = (TextView) findResolvedView("notification_firmware_installation_error_description_textview", R.id.notification_firmware_installation_error_description_textview);
        this.firmwareInstallationErrorRetryButton = (MakerBoxButton) findResolvedView("notification_firmware_installation_error_retry_button", R.id.notification_firmware_installation_error_retry_button);
        this.firmwareInstallationSuccessContinueButton = (MakerBoxButton) findResolvedView("notification_firmware_installation_success_continue_button", R.id.notification_firmware_installation_success_continue_button);
        if (this.firmwareInstallationSuccessContinueButton != null) {
            this.firmwareInstallationSuccessContinueButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    InteractiveBaseActivity.this.firmwareInstalationSuccessDialog.setVisibility(8);
                }
            });
        }
        this.corruptedFirmwareDetectedDialog = (MakerBoxDialog) findResolvedView("notification_corrupted_firmware_detected_dialog", R.id.notification_corrupted_firmware_detected_dialog);
        this.corruptedFirmwareDetectedDialogCancelTextView = (TextView) findResolvedView("notification_corrupted_dialog_install_factory_firmware_button_cancel", R.id.notification_corrupted_dialog_install_factory_firmware_button_cancel);
        if (this.corruptedFirmwareDetectedDialogCancelTextView != null) {
            this.corruptedFirmwareDetectedDialogCancelTextView.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    InteractiveBaseActivity.this.corruptedFirmwareDetectedDialog.setVisibility(8);
                }
            });
        }
        this.corruptedFirmwareDetectedDialogInstallButton = (MakerBoxButton) findResolvedView("notification_corrupted_dialog_install_factory_firmware_button", R.id.notification_corrupted_dialog_install_factory_firmware_button);
        if (this.corruptedFirmwareDetectedDialogInstallButton != null) {
            this.corruptedFirmwareDetectedDialogInstallButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    InteractiveBaseActivity.this.corruptedFirmwareDetectedDialog.setVisibility(8);
                    ((InteractiveBasePresenter) InteractiveBaseActivity.this.getPresenter()).manageLowBatteryForInstallingFirmware(false, true);
                }
            });
        }
        if (this.zowiConnectButton != null) {
            this.zowiConnectButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    InteractiveBaseActivity.this.shouldShowConnectingSpinner = true;
                    InteractiveBaseActivity.this.isConnected = false;
                    InteractiveBaseActivity.this.isConnecting = true;
                    InteractiveBaseActivity.this.isLowBattery = false;
                    InteractiveBaseActivity.this.isDemoMode = false;
                    InteractiveBaseActivity.this.isAltered = false;
                    InteractiveBaseActivity.this.updateStatusBar();
                    ((InteractiveBasePresenter) InteractiveBaseActivity.this.getPresenter()).manageConnection();
                }
            });
        }
        if (this.zowiFactoryResetButton != null) {
            this.zowiFactoryResetButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((InteractiveBasePresenter) InteractiveBaseActivity.this.getPresenter()).manageLowBatteryForInstallingFirmware(false);
                }
            });
        }
        if (this.zowiLaunchWizardButton != null) {
            this.zowiLaunchWizardButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((InteractiveBasePresenter) InteractiveBaseActivity.this.getPresenter()).launchWizard();
                }
            });
        }
        super.onPostCreate(savedInstanceState);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public boolean isZowiAltered() {
        return this.isAltered;
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showAchievementUnlock(AchievementViewModel achievement) {
        this.achievementLayout.setAchievementTitle(ResourceResolver.getStringByResourceId(achievement.getTitleResourceId(), getResources(), getPackageName()));
        this.achievementLayout.setAchievementDescription(ResourceResolver.getStringByResourceId(achievement.getDescriptionResourceId(), getResources(), getPackageName()));
        this.achievementLayout.setAchievementDrawable(ResourceResolver.getDrawableByResourceId(achievement.getBadgeImageResourceId(), this));
        this.achievementLayout.setOnContinueButtonClickedListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                InteractiveBaseActivity.this.achievementLayout.setVisibility(8);
            }
        });
        this.achievementLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showZowiName(String zowiName) {
        this.zowiNameTextView.setText(zowiName);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    @CallSuper
    public void showZowiConnected() {
        this.isConnected = true;
        this.isConnecting = false;
        this.isDemoMode = false;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    @CallSuper
    public void showZowiConnecting() {
        this.isConnected = false;
        this.isConnecting = true;
        this.isLowBattery = false;
        this.isDemoMode = false;
        this.isAltered = false;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    @CallSuper
    public void showZowiDisconnected() {
        this.isConnected = false;
        this.isConnecting = false;
        this.isLowBattery = false;
        this.isAltered = false;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showLowBatteryLevel() {
        this.isConnected = true;
        this.isConnecting = false;
        this.isLowBattery = true;
        this.isDemoMode = false;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showDemoMode() {
        this.isConnected = false;
        this.isConnecting = false;
        this.isLowBattery = false;
        this.isDemoMode = true;
        this.isAltered = false;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showGoodBatteryLevel() {
        this.isConnected = true;
        this.isConnecting = false;
        this.isLowBattery = false;
        this.isDemoMode = false;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void updateNotificationOnAlteredFirmwareDetected() {
        this.isConnected = true;
        this.isConnecting = false;
        this.isDemoMode = false;
        this.isAltered = true;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showIsInstallingFw() {
        this.isInstallingFw = true;
        this.isDemoMode = false;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void updateNotificationOnFwInstallationSuccess() {
        this.isConnected = true;
        this.isInstallingFw = false;
        this.isDemoMode = false;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void updateNotificationOnFwInstallationError() {
        this.isInstallingFw = false;
        this.isDemoMode = false;
        updateStatusBar();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showCorruptedZowiDialog(String zowiName) {
        ((TextView) findViewById(R.id.notification_corrupted_firmware_detected_dialog_title)).setText(getString(R.string.corrupted_zowi_found_dialog_title, new Object[]{zowiName}));
        ((TextView) findViewById(R.id.notification_corrupted_firmware_detected_dialog_description)).setText(getString(R.string.corrupted_zowi_found_dialog_description, new Object[]{zowiName}));
        this.corruptedFirmwareDetectedDialog.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showInstallingFwSuccessDialog(String zowiName, boolean isAutoUpdate) {
        getPresenter().checkAndManageZowiAppInstalled();
        if (isAutoUpdate) {
            this.firmwareInstallationSuccessDescriptionTextView.setText(getString(R.string.update_fw_success_dialog_description, new Object[]{zowiName}));
            this.firmwareInstallationSuccessContinueButton.setText(getString(R.string.update_fw_success_dialog_button));
        } else {
            this.firmwareInstallationSuccessDescriptionTextView.setText(getString(R.string.recover_fw_success_dialog_description, new Object[]{zowiName}));
            this.firmwareInstallationSuccessContinueButton.setText(getString(R.string.recover_fw_success_dialog_button));
        }
        this.firmwareInstalationSuccessDialog.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showInstallingFwErrorDialog(String zowiName, boolean isAutoUpdate, boolean zowiStillConnected) {
        if (isAutoUpdate) {
            if (zowiStillConnected) {
                this.firmwareInstallationErrorTitleTextView.setText(getString(R.string.firmware_installation_success_dialog_error_still_connected_title));
                this.firmwareInstallationErrorDescriptionTextView.setText(getString(R.string.update_fw_error_still_connected_description, new Object[]{zowiName}));
            } else {
                this.firmwareInstallationErrorTitleTextView.setText(getString(R.string.firmware_installation_success_dialog_error_not_connected_title, new Object[]{zowiName}));
                this.firmwareInstallationErrorDescriptionTextView.setText(getString(R.string.update_fw_error_not_connected_description, new Object[]{zowiName}));
            }
            this.firmwareInstallationErrorRetryButton.setText(getString(R.string.update_fw_retry_button));
        } else {
            if (zowiStillConnected) {
                this.firmwareInstallationErrorTitleTextView.setText(getString(R.string.firmware_installation_success_dialog_error_still_connected_title));
                this.firmwareInstallationErrorDescriptionTextView.setText(getString(R.string.recover_fw_error_still_connected_description, new Object[]{zowiName}));
            } else {
                this.firmwareInstallationErrorTitleTextView.setText(getString(R.string.firmware_installation_success_dialog_error_not_connected_title, new Object[]{zowiName}));
                this.firmwareInstallationErrorDescriptionTextView.setText(getString(R.string.recover_fw_error_not_connected_description, new Object[]{zowiName}));
            }
            this.firmwareInstallationErrorRetryButton.setText(getString(R.string.recover_fw_retry_button));
        }
        this.firmwareInstallationErrorRetryButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InteractiveBaseActivity.this.firmwareInstalationErrorDialog.setVisibility(8);
                ((InteractiveBasePresenter) InteractiveBaseActivity.this.getPresenter()).installFactoryFirmware(false);
            }
        });
        this.firmwareInstalationErrorDialog.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void hideRestoreFirmwareDialog() {
        this.restoreFirmwareDialog.setVisibility(8);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showFirmwareUpdatingDialog(String zowiName, boolean isAutoUpdate) {
        TextView updatingDescriptionView = (TextView) findViewById(R.id.notification_firmware_updating_description);
        if (isAutoUpdate) {
            updatingDescriptionView.setText(getString(R.string.update_progress_description, new Object[]{zowiName}));
        } else {
            updatingDescriptionView.setText(getString(R.string.recover_progress_description, new Object[]{zowiName}));
        }
        this.firmwareUpdatingDialog.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void hideFirmwareUpdatingDialog() {
        this.firmwareUpdatingDialog.setVisibility(8);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void updateFirmwareUpdatingProgressBar(int progressValue) {
        this.firmwareUpdateProgressBar.setProgress(progressValue);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showInstallingFirmwareInfoDialog() {
        this.restoreFirmwareDialogTitle.setText(R.string.settings_load_factory_firmware_dialog_title);
        this.restoreFirmwareDialogDescription.setText(R.string.settings_load_factory_firmware_dialog_description);
        this.restoreFirmwareDialog.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    public void showLowBatteryForInstallingFirmwareDialog(String zowiName, final boolean isAutoUpdate) {
        this.isConnected = true;
        this.isConnecting = false;
        this.isLowBattery = true;
        this.isDemoMode = false;
        updateStatusBar();
        this.lowBatteryForInstallingFwButtonOk.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.InteractiveBaseActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((InteractiveBasePresenter) InteractiveBaseActivity.this.getPresenter()).installFactoryFirmware(isAutoUpdate);
                InteractiveBaseActivity.this.lowBatteryForInstallingFwDialog.setVisibility(8);
            }
        });
        this.lowBatteryForInstallingFwTitle.setText(getString(R.string.low_battery_when_installing_fw_dialog_title, new Object[]{zowiName}));
        this.lowBatteryForInstallingFwDescription.setText(getString(R.string.low_battery_when_installing_fw_restore_dialog_description, new Object[]{zowiName}));
        if (isAutoUpdate) {
            this.lowBatteryForInstallingFwDescription.setText(getString(R.string.low_battery_when_installing_fw_automatic_dialog_description, new Object[]{zowiName}));
            this.lowBatteryForInstallingFwButtonCancel.setVisibility(8);
            this.lowBatteryForInstallingFwDialog.showCloseButton(false);
        }
        this.lowBatteryForInstallingFwDialog.setVisibility(0);
    }

    @Override // com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getPresenter().manageConnection();
        getPresenter().manageZowiName();
        getPresenter().measureAndManageBatteryLevel();
        getPresenter().checkAndManageZowiAppInstalled();
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.still, R.anim.slide_out);
    }

    protected void updateStatusBar() {
        if (this.isDemoMode) {
            this.zowiStatusImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId("zowi_demo_icon", this));
            this.zowiStatusTextView.setText(R.string.status_demo);
            this.zowiConnectButton.setVisibility(8);
            this.zowiFactoryResetButton.setVisibility(8);
            this.zowiLaunchWizardButton.setVisibility(0);
            this.connectingProgressBar.setVisibility(8);
            disableZowiConnexionDependantViews();
            return;
        }
        if (this.isInstallingFw) {
            this.zowiStatusImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId("zowi_altered_icon", this));
            this.zowiStatusTextView.setText(R.string.status_installing_firmware);
            this.zowiConnectButton.setVisibility(8);
            this.zowiFactoryResetButton.setVisibility(8);
            this.zowiLaunchWizardButton.setVisibility(8);
            this.connectingProgressBar.setVisibility(8);
            disableZowiConnexionDependantViews();
            return;
        }
        if (this.isAltered) {
            this.zowiStatusImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId("zowi_altered_icon", this));
            this.zowiStatusTextView.setText(R.string.status_altered);
            this.zowiConnectButton.setVisibility(8);
            this.zowiFactoryResetButton.setVisibility(0);
            this.zowiLaunchWizardButton.setVisibility(8);
            this.connectingProgressBar.setVisibility(8);
            enabledZowiConnexionDependantViews();
            return;
        }
        if (this.isLowBattery) {
            this.zowiStatusImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId("zowi_low_battery_icon", this));
            this.zowiStatusTextView.setText(R.string.status_low_battery);
            this.zowiConnectButton.setVisibility(8);
            this.zowiFactoryResetButton.setVisibility(8);
            this.zowiLaunchWizardButton.setVisibility(8);
            this.connectingProgressBar.setVisibility(8);
            enabledZowiConnexionDependantViews();
            return;
        }
        if (this.isConnected) {
            this.zowiStatusImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId("zowi_connected_icon", this));
            this.zowiStatusTextView.setText(R.string.status_connected);
            this.zowiConnectButton.setVisibility(8);
            this.zowiFactoryResetButton.setVisibility(8);
            this.zowiLaunchWizardButton.setVisibility(8);
            this.connectingProgressBar.setVisibility(8);
            enabledZowiConnexionDependantViews();
            return;
        }
        if (this.isConnecting) {
            if (this.shouldShowConnectingSpinner) {
                this.shouldShowConnectingSpinner = false;
                this.zowiStatusImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId("zowi_disconnected_icon", this));
                this.zowiStatusTextView.setText(R.string.status_connecting);
                this.zowiConnectButton.setVisibility(8);
                this.zowiFactoryResetButton.setVisibility(8);
                this.zowiLaunchWizardButton.setVisibility(8);
                this.connectingProgressBar.setVisibility(0);
            } else {
                this.zowiStatusImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId("zowi_disconnected_icon", this));
                this.zowiStatusTextView.setText(R.string.status_disconnected);
                this.zowiConnectButton.setVisibility(0);
                this.zowiFactoryResetButton.setVisibility(8);
                this.zowiLaunchWizardButton.setVisibility(8);
                this.connectingProgressBar.setVisibility(8);
            }
            disableZowiConnexionDependantViews();
            return;
        }
        if (!this.isConnected) {
            this.zowiStatusImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId("zowi_disconnected_icon", this));
            this.zowiStatusTextView.setText(R.string.status_disconnected);
            this.zowiConnectButton.setVisibility(0);
            this.zowiFactoryResetButton.setVisibility(8);
            this.zowiLaunchWizardButton.setVisibility(8);
            this.connectingProgressBar.setVisibility(8);
            disableZowiConnexionDependantViews();
        }
    }

    private void enabledZowiConnexionDependantViews() {
        if (this.zowiDependantViews != null) {
            for (View view : this.zowiDependantViews) {
                view.setEnabled(true);
                view.animate().alpha(1.0f);
            }
        }
    }

    private void disableZowiConnexionDependantViews() {
        if (this.zowiDependantViews != null) {
            for (View view : this.zowiDependantViews) {
                if (view != null) {
                    view.setEnabled(false);
                    view.animate().alpha(0.5f);
                }
            }
        }
    }
}
