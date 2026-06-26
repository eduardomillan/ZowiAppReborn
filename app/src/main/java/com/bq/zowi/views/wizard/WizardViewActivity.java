package com.bq.zowi.views.wizard;

import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.components.EduBar;
import com.bq.zowi.components.NonSwipeableViewPager;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.wizard.WizardPresenter;
import com.bq.zowi.utils.NameValidator;
import com.bq.zowi.utils.ToastUtils;
import com.bq.zowi.views.BaseActivity;
import com.bq.zowi.wireframes.wizard.WizardWireframe;

/* JADX INFO: loaded from: classes.dex */
public class WizardViewActivity extends BaseActivity<WizardPresenter> implements WizardView {
    private Button connectToZowiButton;
    private String connectedZowiDeviceAddress;
    private View dismissWizardButton;
    protected EduBar eduBar;
    private Button findZowisButton;
    private EditText nameEditText;
    private View noZowisFoundWizardLayout;
    private View retryDismissWizardButton;
    private Button retryFindZowisButton;
    private ProgressBar searchingProgressBar;
    private View searchingWizardLayout;
    private Button setNameButton;
    private NonSwipeableViewPager viewPager;

    @Override // com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_wizard_view", R.layout.activity_wizard_view);
        this.viewPager = (NonSwipeableViewPager) findViewById(R.id.activity_wizard_view_pager);
        this.searchingWizardLayout = findViewById(R.id.searching_wizard_layout);
        this.noZowisFoundWizardLayout = findViewById(R.id.no_zowis_found_wizard_layout);
        this.nameEditText = (EditText) findViewById(R.id.wizard_name_edit_text);
        this.eduBar = (EduBar) findViewById(R.id.wizard_edubar);
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_WIZARD_WELCOME));
        this.nameEditText.addTextChangedListener(new TextWatcher() { // from class: com.bq.zowi.views.wizard.WizardViewActivity.1
            private String textBeforeLastChange = "";

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (WizardViewActivity.this.nameEditText.getText().length() <= 0 || NameValidator.isNameValid(WizardViewActivity.this.nameEditText.getText().toString())) {
                    this.textBeforeLastChange = WizardViewActivity.this.nameEditText.getText().toString();
                    return;
                }
                WizardViewActivity.this.nameEditText.setText(this.textBeforeLastChange);
                WizardViewActivity.this.nameEditText.setSelection(this.textBeforeLastChange.length());
                ToastUtils.showNonOverlappingToast(WizardViewActivity.this, R.string.wizard_connecting_set_name_warning_text, 0);
            }
        });
        this.findZowisButton = (Button) findViewById(R.id.wizard_find_zowis_button);
        this.findZowisButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.wizard.WizardViewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((WizardPresenter) WizardViewActivity.this.getPresenter()).findZowis();
            }
        });
        this.dismissWizardButton = findViewById(R.id.wizard_dismiss_button);
        this.dismissWizardButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.wizard.WizardViewActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((WizardPresenter) WizardViewActivity.this.getPresenter()).dismissWizard();
            }
        });
        this.retryDismissWizardButton = findViewById(R.id.wizard_retry_dismiss_button);
        this.retryDismissWizardButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.wizard.WizardViewActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((WizardPresenter) WizardViewActivity.this.getPresenter()).dismissWizard();
            }
        });
        this.retryFindZowisButton = (Button) findViewById(R.id.wizard_retry_find_zowis_button);
        this.retryFindZowisButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.wizard.WizardViewActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((WizardPresenter) WizardViewActivity.this.getPresenter()).findZowis();
            }
        });
        this.connectToZowiButton = (Button) findViewById(R.id.wizard_connect_to_zowi_button);
        this.connectToZowiButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.wizard.WizardViewActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WizardViewActivity.this.connectToZowiButton.setEnabled(false);
                WizardViewActivity.this.connectToZowiButton.setText(WizardViewActivity.this.getString(R.string.wizard_pairing_button_text));
                ((WizardPresenter) WizardViewActivity.this.getPresenter()).connectToZowi(WizardViewActivity.this.connectedZowiDeviceAddress);
            }
        });
        this.setNameButton = (Button) findViewById(R.id.wizard_set_name_button);
        this.setNameButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.wizard.WizardViewActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((WizardPresenter) WizardViewActivity.this.getPresenter()).changeZowiName(WizardViewActivity.this.nameEditText.getText().toString(), WizardViewActivity.this.connectedZowiDeviceAddress);
            }
        });
        this.searchingProgressBar = (ProgressBar) findViewById(R.id.wizard_searching_progress_bar);
        WizardPagerAdapter adapter = new WizardPagerAdapter();
        this.viewPager.setAdapter(adapter);
    }

    @Override // com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public WizardPresenter resolvePresenter() {
        WizardPresenter presenter = AndroidDependencyInjector.getInstance().provideWizardPresenter();
        WizardWireframe wireframe = AndroidDependencyInjector.getInstance().provideWizardWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.wizard.WizardView
    public void showSpinner() {
        this.searchingProgressBar.setVisibility(0);
    }

    @Override // com.bq.zowi.views.wizard.WizardView
    public void hideSpinner() {
        this.searchingProgressBar.setVisibility(4);
    }

    @Override // com.bq.zowi.views.wizard.WizardView
    public void showSearchingForZowis() {
        this.searchingWizardLayout.setVisibility(0);
        this.noZowisFoundWizardLayout.setVisibility(8);
        this.viewPager.setCurrentItem(1);
    }

    @Override // com.bq.zowi.views.wizard.WizardView
    public void showNoZowisFound() {
        this.searchingWizardLayout.setVisibility(8);
        this.noZowisFoundWizardLayout.setVisibility(0);
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_WIZARD_ZOWI_NOT_FOUND));
    }

    @Override // com.bq.zowi.views.wizard.WizardView
    public void showZowiFound(String foundZowiDeviceAddress) {
        this.connectedZowiDeviceAddress = foundZowiDeviceAddress;
        this.viewPager.setCurrentItem(2);
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_WIZARD_ZOWI_FOUND));
    }

    @Override // com.bq.zowi.views.wizard.WizardView
    public void showConnectionSuccessWithEditableName(String connectedZowiDeviceAddress) {
        this.connectedZowiDeviceAddress = connectedZowiDeviceAddress;
        this.viewPager.setCurrentItem(3);
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_WIZARD_SET_NAME));
    }

    @Override // com.bq.zowi.views.wizard.WizardView
    public void showConnectionError() {
        this.connectToZowiButton.setEnabled(true);
        this.connectToZowiButton.setText(getString(R.string.wizard_pair_button_text));
    }

    @Override // com.bq.zowi.views.wizard.WizardView
    public void showInvalidNameError() {
        this.eduBar.show(R.string.settings_change_zowi_name_invalid);
    }

    static class WizardPagerAdapter extends PagerAdapter {
        WizardPagerAdapter() {
        }

        static class WizardPages {
            public static final int CONNECTED = 3;
            public static final int PAIR = 2;
            public static final int SEARCH = 1;
            public static final int START_WIZARD = 0;

            WizardPages() {
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup collection, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.start_wizard_layout;
                    break;
                case 1:
                    resId = R.id.search_wizard_layout;
                    break;
                case 2:
                    resId = R.id.found_wizard_layout;
                    break;
                case 3:
                    resId = R.id.connected_wizard_layout;
                    break;
            }
            return collection.findViewById(resId);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return 4;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}
