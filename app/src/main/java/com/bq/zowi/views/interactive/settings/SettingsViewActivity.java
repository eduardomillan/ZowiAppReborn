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

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_view);
        Button homeButton = (Button) findViewById(R.id.activity_settings_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).homeButtonPressed();
            }
        });
        this.loadFactoryFirmwareOption = (RelativeLayout) findViewById(R.id.activity_settings_load_factory_firmware_option);
        this.loadFactoryFirmwareOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).manageLowBatteryForInstallingFirmware(false);
            }
        });
        Button loadFactoryFirmwareButton = (Button) findViewById(R.id.activity_settings_load_factory_firmware_button);
        loadFactoryFirmwareButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).manageLowBatteryForInstallingFirmware(false);
            }
        });
        this.changeZowiNameOption = (RelativeLayout) findViewById(R.id.activity_settings_change_zowi_name_option);
        this.changeZowiNameOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SettingsViewActivity.this.changeNameDialog.setVisibility(0);
            }
        });
        Button changeZowiNameButton = (Button) findViewById(R.id.activity_settings_change_zowi_name_button);
        changeZowiNameButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SettingsViewActivity.this.changeNameDialog.setVisibility(0);
            }
        });
        this.changeNameDialog = (MakerBoxDialog) findViewById(R.id.activity_settings_change_zowi_name_dialog);
        this.zowiNameEditText = (EditText) findViewById(R.id.activity_settings_change_zowi_name_dialog_edit_text);
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
                if (SettingsViewActivity.this.zowiNameEditText.getText().length() <= 0 || NameValidator.isNameValid(SettingsViewActivity.this.zowiNameEditText.getText().toString())) {
                    this.textBeforeLastChange = SettingsViewActivity.this.zowiNameEditText.getText().toString();
                    return;
                }
                SettingsViewActivity.this.zowiNameEditText.setText(this.textBeforeLastChange);
                SettingsViewActivity.this.zowiNameEditText.setSelection(this.textBeforeLastChange.length());
                ToastUtils.showNonOverlappingToast(SettingsViewActivity.this, R.string.wizard_connecting_set_name_warning_text, 0);
            }
        });
        this.changeNameButtonOk = (MakerBoxButton) findViewById(R.id.activity_settings_change_zowi_name_dialog_button_ok);
        this.changeNameButtonOk.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).changeZowiName(SettingsViewActivity.this.zowiNameEditText.getText().toString());
                SettingsViewActivity.this.changeNameDialog.setVisibility(8);
            }
        });
        this.lookForUpdatesOption = (RelativeLayout) findViewById(R.id.activity_settings_look_for_updates_option);
        this.lookForUpdatesOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).lookForAppUpdates();
            }
        });
        Button lookForUpdatesButton = (Button) findViewById(R.id.activity_settings_look_for_updates_button);
        lookForUpdatesButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).lookForAppUpdates();
            }
        });
        this.forgetPlayingHistoryOption = (RelativeLayout) findViewById(R.id.activity_settings_forget_playing_history_option);
        this.forgetPlayingHistoryOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SettingsViewActivity.this.forgetPlayingHistoryDialog.setVisibility(0);
            }
        });
        Button forgetPlayingHistoryButton = (Button) findViewById(R.id.activity_settings_forget_playing_history_button);
        forgetPlayingHistoryButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SettingsViewActivity.this.forgetPlayingHistoryDialog.setVisibility(0);
            }
        });
        this.forgetPlayingHistoryDialog = (MakerBoxDialog) findViewById(R.id.activity_settings_forget_playing_history_button_dialog);
        this.forgetPlayingHistoryButtonOk = (MakerBoxButton) findViewById(R.id.activity_settings_forget_playing_history_button_dialog_button_ok);
        this.forgetPlayingHistoryButtonOk.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).forgetPlayingHistory();
                SettingsViewActivity.this.forgetPlayingHistoryDialog.setVisibility(8);
            }
        });
        this.forgetPlayingHistoryButtonCancel = (TextView) findViewById(R.id.activity_settings_forget_playing_history_button_dialog_button_cancel);
        this.forgetPlayingHistoryButtonCancel.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SettingsViewActivity.this.forgetPlayingHistoryDialog.setVisibility(8);
            }
        });
        this.forgetZowiOption = (RelativeLayout) findViewById(R.id.activity_settings_forget_zowi_option);
        this.forgetZowiOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SettingsViewActivity.this.forgetZowiDialog.setVisibility(0);
            }
        });
        Button forgetZowiButton = (Button) findViewById(R.id.activity_settings_forget_zowi_button);
        forgetZowiButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SettingsViewActivity.this.forgetZowiDialog.setVisibility(0);
            }
        });
        this.forgetZowiDialog = (MakerBoxDialog) findViewById(R.id.activity_settings_forget_zowi_button_dialog);
        this.forgetZowiButtonOk = (MakerBoxButton) findViewById(R.id.activity_settings_forget_zowi_button_dialog_button_ok);
        this.forgetZowiButtonOk.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).forgetZowi();
                SettingsViewActivity.this.forgetZowiDialog.setVisibility(8);
            }
        });
        this.forgetZowiButtonCancel = (TextView) findViewById(R.id.activity_settings_forget_zowi_button_dialog_button_cancel);
        this.forgetZowiButtonCancel.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SettingsViewActivity.this.forgetZowiDialog.setVisibility(8);
            }
        });
        this.calibrateZowiOption = (RelativeLayout) findViewById(R.id.activity_settings_calibrate_zowi_option);
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
        Button calibrateZowiButton = (Button) findViewById(R.id.activity_settings_calibrate_zowi_button);
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
        this.hospitalOption = (RelativeLayout) findViewById(R.id.activity_settings_hospital_option);
        this.hospitalOption.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).visitHospital();
            }
        });
        Button hospitalButton = (Button) findViewById(R.id.activity_settings_hospital_button);
        hospitalButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.SettingsViewActivity.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((SettingsPresenter) SettingsViewActivity.this.getPresenter()).visitHospital();
            }
        });
        this.zowiDependantViews = new View[]{this.loadFactoryFirmwareOption, loadFactoryFirmwareButton, this.changeZowiNameOption, changeZowiNameButton, this.calibrateZowiOption, calibrateZowiButton};
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseView
    public void showZowiName(String zowiName) {
        super.showZowiName(zowiName);
        this.zowiNameEditText.setHint(zowiName);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v4.app.FragmentActivity, android.app.Activity
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
        this.eduBar.show(R.string.settings_change_zowi_name_success);
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showNameChangeError() {
        this.eduBar.show(R.string.settings_change_zowi_name_fail);
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showInvalidNameError() {
        this.eduBar.show(R.string.settings_change_zowi_name_invalid);
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showForgetPlayingHistorySuccess() {
        this.eduBar.show(R.string.settings_forget_playing_history_success);
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showForgetPlayingHistoryError() {
        this.eduBar.show(R.string.settings_forget_playing_history_fail);
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showForgetZowiSuccess() {
        this.eduBar.show(R.string.settings_forget_zowi_success);
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showForgetZowiError() {
        this.eduBar.show(R.string.settings_forget_zowi_fail);
    }

    @Override // com.bq.zowi.views.interactive.settings.SettingsView
    public void showRestoringInfoWhenCalibratingAlteredZowi() {
        this.restoreFirmwareDialogTitle.setText(R.string.settings_calibration_altered_zowi_dialog_title);
        this.restoreFirmwareDialogDescription.setText(R.string.settings_calibration_altered_zowi_dialog_description);
        this.restoreFirmwareDialog.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseView
    public void showLowBatteryForInstallingFirmwareDialog(String zowiName, boolean isAutoUpdate) {
        this.lowBatteryForInstallingFwDescription.setText(getString(R.string.low_battery_when_installing_fw_restore_dialog_description, new Object[]{zowiName}));
        this.lowBatteryForInstallingFwButtonOk.setText(isAutoUpdate ? R.string.calibration_warning_continue_button : R.string.settings_load_factory_firmware_dialog_button_ok);
        this.lowBatteryForInstallingFwButtonCancel.setText(R.string.settings_load_factory_firmware_dialog_button_cancel);
        super.showLowBatteryForInstallingFirmwareDialog(zowiName, isAutoUpdate);
    }
}
