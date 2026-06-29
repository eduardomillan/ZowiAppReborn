package com.bq.zowi.views.interactive.settings;

import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
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

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_calibration_view", R.layout.activity_calibration_view);
        this.viewpager = (NonSwipeableViewPager) findResolvedView("activity_calibration_view_pager", R.id.activity_calibration_view_pager);
        if (this.viewpager != null) {
            this.viewpager.setAdapter(new CalibrationPagerAdapter());
            this.viewpager.setOffscreenPageLimit(this.viewpager.getChildCount());
        }
        this.warningCancelButton = findResolvedView("activity_calibration_warning_cancel_button", R.id.activity_calibration_warning_cancel_button);
        if (this.warningCancelButton != null) {
            this.warningCancelButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).warningCancelButtonPressed();
                }
            });
        }
        this.warningContinueButton = findResolvedView("activity_calibration_warning_continue_button", R.id.activity_calibration_warning_continue_button);
        if (this.warningContinueButton != null) {
            this.warningContinueButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).warningContinueButtonPressed();
                }
            });
        }
        View legsNextStepButton = findResolvedView("calibration_legs_next_step", R.id.calibration_legs_next_step);
        if (legsNextStepButton != null) {
            legsNextStepButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CalibrationViewActivity.this.showFeetCalibration();
                }
            });
        }
        this.leftLegText = (TextView) findResolvedView("calibration_left_leg_text", R.id.calibration_left_leg_text);
        if (this.leftLegText != null) {
            this.leftLegText.setText(getString(R.string.calibration_left_leg, new Object[]{0}));
        }
        this.rightLegText = (TextView) findResolvedView("calibration_right_leg_text", R.id.calibration_right_leg_text);
        if (this.rightLegText != null) {
            this.rightLegText.setText(getString(R.string.calibration_right_leg, new Object[]{0}));
        }
        this.legsCalibrationLeftIncreaseButton = findResolvedView("calibration_left_leg_increase_button", R.id.calibration_left_leg_increase_button);
        if (this.legsCalibrationLeftIncreaseButton != null) {
            this.legsCalibrationLeftIncreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).legsCalibrationLeftIncreaseButtonPressed();
                }
            });
        }
        this.legsCalibrationLeftDecreaseButton = findResolvedView("calibration_left_leg_decrease_button", R.id.calibration_left_leg_decrease_button);
        if (this.legsCalibrationLeftDecreaseButton != null) {
            this.legsCalibrationLeftDecreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).legsCalibrationLeftDecreaseButtonPressed();
                }
            });
        }
        this.legsCalibrationRightIncreaseButton = findResolvedView("calibration_right_leg_increase_button", R.id.calibration_right_leg_increase_button);
        if (this.legsCalibrationRightIncreaseButton != null) {
            this.legsCalibrationRightIncreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).legsCalibrationRightIncreaseButtonPressed();
                }
            });
        }
        this.legsCalibrationRightDecreaseButton = findResolvedView("calibration_right_leg_decrease_button", R.id.calibration_right_leg_decrease_button);
        if (this.legsCalibrationRightDecreaseButton != null) {
            this.legsCalibrationRightDecreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).legsCalibrationRightDecreaseButtonPressed();
                }
            });
        }
        View feetNextStepButton = findResolvedView("calibration_feet_next_step", R.id.calibration_feet_next_step);
        if (feetNextStepButton != null) {
            feetNextStepButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CalibrationViewActivity.this.showCheckCalibration();
                }
            });
        }
        this.leftFootText = (TextView) findResolvedView("calibration_left_feet_text", R.id.calibration_left_feet_text);
        if (this.leftFootText != null) {
            this.leftFootText.setText(getString(R.string.calibration_feet_left, new Object[]{0}));
        }
        this.rightFootText = (TextView) findResolvedView("calibration_right_feet_text", R.id.calibration_right_feet_text);
        if (this.rightFootText != null) {
            this.rightFootText.setText(getString(R.string.calibration_feet_right, new Object[]{0}));
        }
        this.feetCalibrationLeftIncreaseButton = findResolvedView("calibration_left_feet_increase_button", R.id.calibration_left_feet_increase_button);
        if (this.feetCalibrationLeftIncreaseButton != null) {
            this.feetCalibrationLeftIncreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).feetCalibrationLeftIncreaseButtonPressed();
                }
            });
        }
        this.feetCalibrationLeftDecreaseButton = findResolvedView("calibration_left_feet_decrease_button", R.id.calibration_left_feet_decrease_button);
        if (this.feetCalibrationLeftDecreaseButton != null) {
            this.feetCalibrationLeftDecreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).feetCalibrationLeftDecreaseButtonPressed();
                }
            });
        }
        this.feetCalibrationRightIncreaseButton = findResolvedView("calibration_right_feet_increase_button", R.id.calibration_right_feet_increase_button);
        if (this.feetCalibrationRightIncreaseButton != null) {
            this.feetCalibrationRightIncreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.11
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).feetCalibrationRightIncreaseButtonPressed();
                }
            });
        }
        this.feetCalibrationRightDecreaseButton = findResolvedView("calibration_right_feet_decrease_button", R.id.calibration_right_feet_decrease_button);
        if (this.feetCalibrationRightDecreaseButton != null) {
            this.feetCalibrationRightDecreaseButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.12
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).feetCalibrationRightDecreaseButtonPressed();
                }
            });
        }
        this.checkMovementButton = findResolvedView("calibration_check_movement_button", R.id.calibration_check_movement_button);
        if (this.checkMovementButton != null) {
            this.checkMovementButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.13
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).checkCalibrationTestMovementButtonPressed();
                }
            });
        }
        View confirmButton = findResolvedView("calibration_check_confirm_button", R.id.calibration_check_confirm_button);
        if (confirmButton != null) {
            confirmButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.14
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).calibrationConfirmedButtonPressed();
                }
            });
        }
        View restartButton = findResolvedView("calibration_check_restart_button", R.id.calibration_check_restart_button);
        if (restartButton != null) {
            restartButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.settings.CalibrationViewActivity.15
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((CalibrationPresenter) CalibrationViewActivity.this.getPresenter()).warningContinueButtonPressed();
                }
            });
        }
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
        if (this.viewpager != null) {
            this.viewpager.setCurrentItem(1);
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showFeetCalibration() {
        if (this.viewpager != null) {
            this.viewpager.setCurrentItem(2);
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showCheckCalibration() {
        if (this.viewpager != null) {
            this.viewpager.setCurrentItem(3);
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showLeftLegTrimValue(int trimLeftLegYL) {
        if (this.leftLegText != null) {
            this.leftLegText.setText(getString(R.string.calibration_left_leg, new Object[]{Integer.valueOf(trimLeftLegYL)}));
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showRightLegTrimValue(int trimRightLegYR) {
        if (this.rightLegText != null) {
            this.rightLegText.setText(getString(R.string.calibration_right_leg, new Object[]{Integer.valueOf(trimRightLegYR)}));
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showLeftFootTrimValue(int trimLeftFootRL) {
        if (this.leftFootText != null) {
            this.leftFootText.setText(getString(R.string.calibration_feet_left, new Object[]{Integer.valueOf(trimLeftFootRL)}));
        }
    }

    @Override // com.bq.zowi.views.interactive.settings.CalibrationView
    public void showRightFootTrimValue(int trimRightFootRR) {
        if (this.rightFootText != null) {
            this.rightFootText.setText(getString(R.string.calibration_feet_right, new Object[]{Integer.valueOf(trimRightFootRR)}));
        }
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

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup collection, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = resolvePageId(collection, "activity_calibration_warning_layout", R.id.activity_calibration_warning_layout);
                    break;
                case 1:
                    resId = resolvePageId(collection, "activity_calibration_legs_layout", R.id.activity_calibration_legs_layout);
                    break;
                case 2:
                    resId = resolvePageId(collection, "activity_calibration_feet_layout", R.id.activity_calibration_feet_layout);
                    break;
                case 3:
                    resId = resolvePageId(collection, "activity_calibration_check_layout", R.id.activity_calibration_check_layout);
                    break;
            }
            return collection.findViewById(resId);
        }

        private int resolvePageId(ViewGroup collection, String viewIdName, int fallbackViewId) {
            int viewId = collection.getResources().getIdentifier(viewIdName, "id", collection.getContext().getPackageName());
            return viewId != 0 ? viewId : fallbackViewId;
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
