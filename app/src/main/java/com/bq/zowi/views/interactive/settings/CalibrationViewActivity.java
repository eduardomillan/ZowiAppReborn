package com.bq.zowi.views.interactive.settings;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.components.NonSwipeableViewPager;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.interactive.settings.CalibrationPresenter;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import com.bq.zowi.wireframes.settings.CalibrationWireframe;

/* JADX INFO: loaded from: classes.dex */
public class CalibrationViewActivity extends InteractiveBaseActivity<CalibrationPresenter> implements CalibrationView {
    private View checkMovementButton;
    private View feetCalibrationLeftDecreaseButton;
    private View feetCalibrationLeftIncreaseButton;
    private View feetCalibrationRightDecreaseButton;
    private View feetCalibrationRightIncreaseButton;
    private TextView leftFootText;
    private TextView leftLegText;
    private View legsCalibrationLeftDecreaseButton;
    private View legsCalibrationLeftIncreaseButton;
    private View legsCalibrationRightDecreaseButton;
    private View legsCalibrationRightIncreaseButton;
    private TextView rightFootText;
    private TextView rightLegText;
    private NonSwipeableViewPager viewpager;
    private View warningCancelButton;
    private View warningContinueButton;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration_view);
        this.viewpager = (NonSwipeableViewPager) findViewById(R.id.activity_calibration_view_pager);
        this.viewpager.setAdapter(new CalibrationPagerAdapter());
        this.viewpager.setOffscreenPageLimit(this.viewpager.getChildCount());
        this.warningCancelButton = findViewById(R.id.activity_calibration_warning_cancel_button);
        this.warningCancelButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).warningCancelButtonPressed();
            }
        });
        this.warningContinueButton = findViewById(R.id.activity_calibration_warning_continue_button);
        this.warningContinueButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).warningContinueButtonPressed();
            }
        });
        findViewById(R.id.calibration_legs_next_step).setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CalibrationViewActivity.this.showFeetCalibration();
            }
        });
        this.leftLegText = (TextView) findViewById(R.id.calibration_left_leg_text);
        this.leftLegText.setText(getString(R.string.calibration_left_leg, new Object[]{0}));
        this.rightLegText = (TextView) findViewById(R.id.calibration_right_leg_text);
        this.rightLegText.setText(getString(R.string.calibration_right_leg, new Object[]{0}));
        this.legsCalibrationLeftIncreaseButton = findViewById(R.id.calibration_left_leg_increase_button);
        this.legsCalibrationLeftIncreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).legsCalibrationLeftIncreaseButtonPressed();
            }
        });
        this.legsCalibrationLeftDecreaseButton = findViewById(R.id.calibration_left_leg_decrease_button);
        this.legsCalibrationLeftDecreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).legsCalibrationLeftDecreaseButtonPressed();
            }
        });
        this.legsCalibrationRightIncreaseButton = findViewById(R.id.calibration_right_leg_increase_button);
        this.legsCalibrationRightIncreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).legsCalibrationRightIncreaseButtonPressed();
            }
        });
        this.legsCalibrationRightDecreaseButton = findViewById(R.id.calibration_right_leg_decrease_button);
        this.legsCalibrationRightDecreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).legsCalibrationRightDecreaseButtonPressed();
            }
        });
        findViewById(R.id.calibration_feet_next_step).setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CalibrationViewActivity.this.showCheckCalibration();
            }
        });
        this.leftFootText = (TextView) findViewById(R.id.calibration_left_feet_text);
        this.leftFootText.setText(getString(R.string.calibration_feet_left, new Object[]{0}));
        this.rightFootText = (TextView) findViewById(R.id.calibration_right_feet_text);
        this.rightFootText.setText(getString(R.string.calibration_feet_right, new Object[]{0}));
        this.feetCalibrationLeftIncreaseButton = findViewById(R.id.calibration_left_feet_increase_button);
        this.feetCalibrationLeftIncreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).feetCalibrationLeftIncreaseButtonPressed();
            }
        });
        this.feetCalibrationLeftDecreaseButton = findViewById(R.id.calibration_left_feet_decrease_button);
        this.feetCalibrationLeftDecreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).feetCalibrationLeftDecreaseButtonPressed();
            }
        });
        this.feetCalibrationRightIncreaseButton = findViewById(R.id.calibration_right_feet_increase_button);
        this.feetCalibrationRightIncreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).feetCalibrationRightIncreaseButtonPressed();
            }
        });
        this.feetCalibrationRightDecreaseButton = findViewById(R.id.calibration_right_feet_decrease_button);
        this.feetCalibrationRightDecreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).feetCalibrationRightDecreaseButtonPressed();
            }
        });
        this.checkMovementButton = findViewById(R.id.calibration_check_movement_button);
        this.checkMovementButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).checkCalibrationTestMovementButtonPressed();
            }
        });
        findViewById(R.id.calibration_check_confirm_button).setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).calibrationConfirmedButtonPressed();
            }
        });
        findViewById(R.id.calibration_check_restart_button).setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).warningContinueButtonPressed();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public CalibrationPresenter resolvePresenter() {
        CalibrationPresenter calibrationPresenter = AndroidDependencyInjector.getInstance().provideCalibrationPresenter();
        CalibrationWireframe calibrationWireframe = AndroidDependencyInjector.getInstance().provideCalibrationWireframe(this);
        calibrationPresenter.bindViewAndWireframe(this, calibrationWireframe);
        return calibrationPresenter;
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showLegsCalibration() {
        this.viewpager.setCurrentItem(1);
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showFeetCalibration() {
        this.viewpager.setCurrentItem(2);
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showCheckCalibration() {
        this.viewpager.setCurrentItem(3);
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showLeftLegTrimValue(int trimLeftLegYL) {
        this.leftLegText.setText(getString(R.string.calibration_left_leg, new Object[]{Integer.valueOf(trimLeftLegYL)}));
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showRightLegTrimValue(int trimRightLegYR) {
        this.rightLegText.setText(getString(R.string.calibration_right_leg, new Object[]{Integer.valueOf(trimRightLegYR)}));
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showLeftFootTrimValue(int trimLeftFootRL) {
        this.leftFootText.setText(getString(R.string.calibration_feet_left, new Object[]{Integer.valueOf(trimLeftFootRL)}));
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showRightFootTrimValue(int trimRightFootRR) {
        this.rightFootText.setText(getString(R.string.calibration_feet_right, new Object[]{Integer.valueOf(trimRightFootRR)}));
    }

    static class CalibrationPagerAdapter extends PagerAdapter {
        private int numPages = 4;

        CalibrationPagerAdapter() {
        }

        static class CalibrationPages {
            public static final int CHECK = 3;
            public static final int FEET = 2;
            public static final int LEGS = 1;
            public static final int WARNING = 0;

            CalibrationPages() {
            }
        }

        @Override // android.support.v4.view.PagerAdapter
        public Object instantiateItem(ViewGroup collection, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.activity_calibration_warning_layout;
                    break;
                case 1:
                    resId = R.id.activity_calibration_legs_layout;
                    break;
                case 2:
                    resId = R.id.activity_calibration_feet_layout;
                    break;
                case 3:
                    resId = R.id.activity_calibration_check_layout;
                    break;
            }
            return collection.findViewById(resId);
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return this.numPages;
        }

        @Override // android.support.v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override // android.support.v4.view.PagerAdapter
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
