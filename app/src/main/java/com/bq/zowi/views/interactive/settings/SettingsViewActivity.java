package com.bq.zowi.views.interactive.settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.components.makerboxdialogs.MakerBoxButton;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialog;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.interactive.settings.SettingsPresenter;
import com.bq.zowi.utils.NameValidator;
import com.bq.zowi.utils.ToastUtils;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import com.bq.zowi.wireframes.settings.SettingsWireframe;

/* JADX INFO: loaded from: classes.dex */
public class SettingsViewActivity extends InteractiveBaseActivity<SettingsPresenter> implements SettingsView {
    private RelativeLayout calibrateZowiOption;
    private MakerBoxButton changeNameButtonOk;
    private MakerBoxDialog changeNameDialog;
    private RelativeLayout changeZowiNameOption;
    private TextView forgetPlayingHistoryButtonCancel;
    private MakerBoxButton forgetPlayingHistoryButtonOk;
    private MakerBoxDialog forgetPlayingHistoryDialog;
    private RelativeLayout forgetPlayingHistoryOption;
    private TextView forgetZowiButtonCancel;
    private MakerBoxButton forgetZowiButtonOk;
    private MakerBoxDialog forgetZowiDialog;
    private RelativeLayout forgetZowiOption;
    private RelativeLayout hospitalOption;
    private boolean isInvalidCharacterToastVisible = false;
    private RelativeLayout loadFactoryFirmwareOption;
    private RelativeLayout lookForUpdatesOption;
    private EditText zowiNameEditText;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_settings_view", R.layout.activity_settings_view);
        Button homeButton = (Button) findResolvedView("activity_settings_home_button", R.id.activity_settings_home_button);
        if (homeButton != null) {
            homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).homeButtonPressed();
                }
            });
        }
        this.loadFactoryFirmwareOption = (RelativeLayout) findResolvedView("activity_settings_load_factory_firmware_option", R.id.activity_settings_load_factory_firmware_option);
        if (this.loadFactoryFirmwareOption != null) {
            this.loadFactoryFirmwareOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).manageLowBatteryForInstallingFirmware(false);
                }
            });
        }
        Button loadFactoryFirmwareButton = (Button) findResolvedView("activity_settings_load_factory_firmware_button", R.id.activity_settings_load_factory_firmware_button);
        if (loadFactoryFirmwareButton != null) {
            loadFactoryFirmwareButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).manageLowBatteryForInstallingFirmware(false);
                }
            });
        }
        this.changeZowiNameOption = (RelativeLayout) findResolvedView("activity_settings_change_zowi_name_option", R.id.activity_settings_change_zowi_name_option);
        if (this.changeZowiNameOption != null) {
            this.changeZowiNameOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.changeNameDialog != null) {
                        SettingsViewActivity.this.changeNameDialog.setVisibility(0);
                    }
                }
            });
        }
        Button changeZowiNameButton = (Button) findResolvedView("activity_settings_change_zowi_name_button", R.id.activity_settings_change_zowi_name_button);
        if (changeZowiNameButton != null) {
            changeZowiNameButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.changeNameDialog != null) {
                        SettingsViewActivity.this.changeNameDialog.setVisibility(0);
                    }
                }
            });
        }
        this.changeNameDialog = (MakerBoxDialog) findResolvedView("activity_settings_change_zowi_name_dialog", R.id.activity_settings_change_zowi_name_dialog);
        this.zowiNameEditText = (EditText) findResolvedView("activity_settings_change_zowi_name_dialog_edit_text", R.id.activity_settings_change_zowi_name_dialog_edit_text);
        if (this.zowiNameEditText != null) {
            this.zowiNameEditText.addTextChangedListener(new TextWatcher() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.6
            private String textBeforeLastChange = "";

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (SettingsViewActivity.this.zowiNameEditText == null || SettingsViewActivity.this.zowiNameEditText.getText().length() <= 0 || NameValidator.isNameValid(SettingsViewActivity.this.zowiNameEditText.getText().toString())) {
                    this.textBeforeLastChange = SettingsViewActivity.this.zowiNameEditText.getText().toString();
                    return;
                }
                SettingsViewActivity.this.zowiNameEditText.setText(this.textBeforeLastChange);
                SettingsViewActivity.this.zowiNameEditText.setSelection(this.textBeforeLastChange.length());
                ToastUtils.showNonOverlappingToast(SettingsViewActivity.this, R.string.wizard_connecting_set_name_warning_text, 0);
            }
            });
        }
        this.changeNameButtonOk = (MakerBoxButton) findResolvedView("activity_settings_change_zowi_name_dialog_button_ok", R.id.activity_settings_change_zowi_name_dialog_button_ok);
        if (this.changeNameButtonOk != null) {
            this.changeNameButtonOk.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.zowiNameEditText != null && SettingsViewActivity.this.changeNameDialog != null) {
                        ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).changeZowiName(SettingsViewActivity.this.zowiNameEditText.getText().toString());
                        SettingsViewActivity.this.changeNameDialog.setVisibility(8);
                    }
                }
            });
        }
        this.lookForUpdatesOption = (RelativeLayout) findResolvedView("activity_settings_look_for_updates_option", R.id.activity_settings_look_for_updates_option);
        if (this.lookForUpdatesOption != null) {
            this.lookForUpdatesOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).lookForAppUpdates();
                }
            });
        }
        Button lookForUpdatesButton = (Button) findResolvedView("activity_settings_look_for_updates_button", R.id.activity_settings_look_for_updates_button);
        if (lookForUpdatesButton != null) {
            lookForUpdatesButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).lookForAppUpdates();
                }
            });
        }
        this.forgetPlayingHistoryOption = (RelativeLayout) findResolvedView("activity_settings_forget_playing_history_option", R.id.activity_settings_forget_playing_history_option);
        if (this.forgetPlayingHistoryOption != null) {
            this.forgetPlayingHistoryOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.forgetPlayingHistoryDialog != null) {
                        SettingsViewActivity.this.forgetPlayingHistoryDialog.setVisibility(0);
                    }
                }
            });
        }
        Button forgetPlayingHistoryButton = (Button) findResolvedView("activity_settings_forget_playing_history_button", R.id.activity_settings_forget_playing_history_button);
        if (forgetPlayingHistoryButton != null) {
            forgetPlayingHistoryButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.11
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.forgetPlayingHistoryDialog != null) {
                        SettingsViewActivity.this.forgetPlayingHistoryDialog.setVisibility(0);
                    }
                }
            });
        }
        this.forgetPlayingHistoryDialog = (MakerBoxDialog) findResolvedView("activity_settings_forget_playing_history_button_dialog", R.id.activity_settings_forget_playing_history_button_dialog);
        this.forgetPlayingHistoryButtonOk = (MakerBoxButton) findResolvedView("activity_settings_forget_playing_history_button_dialog_button_ok", R.id.activity_settings_forget_playing_history_button_dialog_button_ok);
        if (this.forgetPlayingHistoryButtonOk != null) {
            this.forgetPlayingHistoryButtonOk.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.12
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).forgetPlayingHistory();
                    if (SettingsViewActivity.this.forgetPlayingHistoryDialog != null) {
                        SettingsViewActivity.this.forgetPlayingHistoryDialog.setVisibility(8);
                    }
                }
            });
        }
        this.forgetPlayingHistoryButtonCancel = (TextView) findResolvedView("activity_settings_forget_playing_history_button_dialog_button_cancel", R.id.activity_settings_forget_playing_history_button_dialog_button_cancel);
        if (this.forgetPlayingHistoryButtonCancel != null) {
            this.forgetPlayingHistoryButtonCancel.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.13
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.forgetPlayingHistoryDialog != null) {
                        SettingsViewActivity.this.forgetPlayingHistoryDialog.setVisibility(8);
                    }
                }
            });
        }
        this.forgetZowiOption = (RelativeLayout) findResolvedView("activity_settings_forget_zowi_option", R.id.activity_settings_forget_zowi_option);
        if (this.forgetZowiOption != null) {
            this.forgetZowiOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.14
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.forgetZowiDialog != null) {
                        SettingsViewActivity.this.forgetZowiDialog.setVisibility(0);
                    }
                }
            });
        }
        Button forgetZowiButton = (Button) findResolvedView("activity_settings_forget_zowi_button", R.id.activity_settings_forget_zowi_button);
        if (forgetZowiButton != null) {
            forgetZowiButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.15
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.forgetZowiDialog != null) {
                        SettingsViewActivity.this.forgetZowiDialog.setVisibility(0);
                    }
                }
            });
        }
        this.forgetZowiDialog = (MakerBoxDialog) findResolvedView("activity_settings_forget_zowi_button_dialog", R.id.activity_settings_forget_zowi_button_dialog);
        this.forgetZowiButtonOk = (MakerBoxButton) findResolvedView("activity_settings_forget_zowi_button_dialog_button_ok", R.id.activity_settings_forget_zowi_button_dialog_button_ok);
        if (this.forgetZowiButtonOk != null) {
            this.forgetZowiButtonOk.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.16
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).forgetZowi();
                    if (SettingsViewActivity.this.forgetZowiDialog != null) {
                        SettingsViewActivity.this.forgetZowiDialog.setVisibility(8);
                    }
                }
            });
        }
        this.forgetZowiButtonCancel = (TextView) findResolvedView("activity_settings_forget_zowi_button_dialog_button_cancel", R.id.activity_settings_forget_zowi_button_dialog_button_cancel);
        if (this.forgetZowiButtonCancel != null) {
            this.forgetZowiButtonCancel.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.17
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.forgetZowiDialog != null) {
                        SettingsViewActivity.this.forgetZowiDialog.setVisibility(8);
                    }
                }
            });
        }
        this.calibrateZowiOption = (RelativeLayout) findResolvedView("activity_settings_calibrate_zowi_option", R.id.activity_settings_calibrate_zowi_option);
        if (this.calibrateZowiOption != null) {
            this.calibrateZowiOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.18
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.isZowiAltered()) {
                        ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).manageLowBatteryWhenCalibratingForInstallingFirmware();
                    } else {
                        ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).calibrateZowi();
                    }
                }
            });
        }
        Button calibrateZowiButton = (Button) findResolvedView("activity_settings_calibrate_zowi_button", R.id.activity_settings_calibrate_zowi_button);
        if (calibrateZowiButton != null) {
            calibrateZowiButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.19
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SettingsViewActivity.this.isZowiAltered()) {
                        ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).manageLowBatteryWhenCalibratingForInstallingFirmware();
                    } else {
                        ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).calibrateZowi();
                    }
                }
            });
        }
        this.hospitalOption = (RelativeLayout) findResolvedView("activity_settings_hospital_option", R.id.activity_settings_hospital_option);
        if (this.hospitalOption != null) {
            this.hospitalOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.20
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).visitHospital();
                }
            });
        }
        Button hospitalButton = (Button) findResolvedView("activity_settings_hospital_button", R.id.activity_settings_hospital_button);
        if (hospitalButton != null) {
            hospitalButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.21
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).visitHospital();
                }
            });
        }
        this.zowiDependantViews = new View[]{this.loadFactoryFirmwareOption, loadFactoryFirmwareButton, this.changeZowiNameOption, changeZowiNameButton, this.calibrateZowiOption, calibrateZowiButton};
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseView
    public void showZowiName(String zowiName) {
        super.showZowiName(zowiName);
        if (this.zowiNameEditText != null) {
            this.zowiNameEditText.setHint(zowiName);
        }
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_SETTINGS));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public SettingsPresenter resolvePresenter() {
        SettingsPresenter presenter = AndroidDependencyInjector.getInstance().provideSettingsPresenter();
        SettingsWireframe settingsWireframe = AndroidDependencyInjector.getInstance().provideSettingsWireframe(this);
        presenter.bindViewAndWireframe(this, settingsWireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showNameChangeSuccess() {
        if (this.eduBar != null) {
            this.eduBar.show(resolveStringText("settings_change_zowi_name_success", R.string.settings_change_zowi_name_success).toString());
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showNameChangeError() {
        if (this.eduBar != null) {
            this.eduBar.show(resolveStringText("settings_change_zowi_name_fail", R.string.settings_change_zowi_name_fail).toString());
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showInvalidNameError() {
        if (this.eduBar != null) {
            this.eduBar.show(resolveStringText("settings_change_zowi_name_invalid", R.string.settings_change_zowi_name_invalid).toString());
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showForgetPlayingHistorySuccess() {
        if (this.eduBar != null) {
            this.eduBar.show(resolveStringText("settings_forget_playing_history_success", R.string.settings_forget_playing_history_success).toString());
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showForgetPlayingHistoryError() {
        if (this.eduBar != null) {
            this.eduBar.show(resolveStringText("settings_forget_playing_history_fail", R.string.settings_forget_playing_history_fail).toString());
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showForgetZowiSuccess() {
        if (this.eduBar != null) {
            this.eduBar.show(resolveStringText("settings_forget_zowi_success", R.string.settings_forget_zowi_success).toString());
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showForgetZowiError() {
        if (this.eduBar != null) {
            this.eduBar.show(resolveStringText("settings_forget_zowi_fail", R.string.settings_forget_zowi_fail).toString());
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showRestoringInfoWhenCalibratingAlteredZowi() {
        if (this.restoreFirmwareDialogTitle != null) {
            this.restoreFirmwareDialogTitle.setText(resolveStringText("settings_calibration_altered_zowi_dialog_title", R.string.settings_calibration_altered_zowi_dialog_title));
        }
        if (this.restoreFirmwareDialogDescription != null) {
            this.restoreFirmwareDialogDescription.setText(resolveStringText("settings_calibration_altered_zowi_dialog_description", R.string.settings_calibration_altered_zowi_dialog_description));
        }
        if (this.restoreFirmwareDialog != null) {
            this.restoreFirmwareDialog.setVisibility(0);
        }
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseView
    public void showLowBatteryForInstallingFirmwareDialog(String zowiName, boolean isAutoUpdate) {
        if (this.lowBatteryForInstallingFwDescription != null) {
            this.lowBatteryForInstallingFwDescription.setText(getString(R.string.low_battery_when_installing_fw_restore_dialog_description, new Object[]{zowiName}));
        }
        if (this.lowBatteryForInstallingFwButtonOk != null) {
            this.lowBatteryForInstallingFwButtonOk.setText(isAutoUpdate ? R.string.calibration_warning_continue_button : R.string.settings_load_factory_firmware_dialog_button_ok);
        }
        if (this.lowBatteryForInstallingFwButtonCancel != null) {
            this.lowBatteryForInstallingFwButtonCancel.setText(R.string.settings_load_factory_firmware_dialog_button_cancel);
        }
        super.showLowBatteryForInstallingFirmwareDialog(zowiName, isAutoUpdate);
    }
}
