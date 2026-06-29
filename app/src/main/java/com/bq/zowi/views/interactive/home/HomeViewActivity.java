package com.bq.zowi.views.interactive.home;

import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.components.home.ZowiAppView;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.interactive.home.HomePresenter;
import com.bq.zowi.utils.ResourceResolver;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import com.bq.zowi.wireframes.home.HomeWireframe;

/* JADX INFO: loaded from: classes.dex */
public class HomeViewActivity extends InteractiveBaseActivity<HomePresenter> implements HomeView {
    private ZowiAppView loadMouthsEditorButton;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_home_view", R.layout.activity_home_view);
        ViewPager viewpager = (ViewPager) findResolvedView("activity_home_view_pager", R.id.activity_home_view_pager);
        if (viewpager != null) {
            viewpager.setAdapter(new HomePagerAdapter());
            int margin = (int) (ResourceResolver.getDimensionByResourceId("sections_pager_horizontal_margin", this) * 2.0f);
            viewpager.setPageMargin(-margin);
        }
        Button settingsButton = (Button) findResolvedView("activity_home_settings_button", R.id.activity_home_settings_button);
        if (settingsButton != null) {
            settingsButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadSettings();
                }
            });
        }
        Button achievementsButton = (Button) findResolvedView("activity_home_achievements_button", R.id.activity_home_achievements_button);
        if (achievementsButton != null) {
            achievementsButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadAchievements();
                }
            });
        }
        View loadGamepadButton = findResolvedView("zowiapps_load_gamepad_button", R.id.zowiapps_load_gamepad_button);
        if (loadGamepadButton != null) {
            loadGamepadButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadGamepad();
                }
            });
        }
        View loadTimelineButton = findResolvedView("zowiapps_load_timeline_button", R.id.zowiapps_load_timeline_button);
        if (loadTimelineButton != null) {
            loadTimelineButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadTimeline();
                }
            });
        }
        View loadZowiSaysMinigameButton = findResolvedView("zowiapps_load_zowi_says_minigame_button", R.id.zowiapps_load_zowi_says_minigame_button);
        if (loadZowiSaysMinigameButton != null) {
            loadZowiSaysMinigameButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadZowiSaysMinigame();
                }
            });
        }
        View loadMouthsMinigameButton = findResolvedView("zowiapps_load_mouths_minigame_button", R.id.zowiapps_load_mouths_minigame_button);
        if (loadMouthsMinigameButton != null) {
            loadMouthsMinigameButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadMouthsMinigame();
                }
            });
        }
        this.loadMouthsEditorButton = (ZowiAppView) findResolvedView("zowiapps_load_mouths_editor_button", R.id.zowiapps_load_mouths_editor_button);
        if (this.loadMouthsEditorButton != null) {
            this.loadMouthsEditorButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (HomeViewActivity.this.loadMouthsEditorButton.isClickable()) {
                        ((HomePresenter) HomeViewActivity.this.getPresenter()).loadMouthsEditor();
                    }
                }
            });
        }
        View projectMueveButton = findResolvedView("zowiapps_load_project_mueve_button", R.id.zowiapps_load_project_mueve_button);
        if (projectMueveButton != null) {
            projectMueveButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("01_project_mueve");
                }
            });
        }
        View projectChoreographyButton = findResolvedView("zowiapps_load_project_choreography_button", R.id.zowiapps_load_project_choreography_button);
        if (projectChoreographyButton != null) {
            projectChoreographyButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("02_project_choreography");
                }
            });
        }
        View projectFormButton = findResolvedView("zowiapps_load_project_form_button", R.id.zowiapps_load_project_form_button);
        if (projectFormButton != null) {
            projectFormButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("03_project_forma");
                }
            });
        }
        View projectBio1Button = findResolvedView("zowiapps_load_project_bio1_button", R.id.zowiapps_load_project_bio1_button);
        if (projectBio1Button != null) {
            projectBio1Button.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.11
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("04_project_bio1");
                }
            });
        }
        View projectPaintButton = findResolvedView("zowiapps_load_project_bio3_button", R.id.zowiapps_load_project_bio3_button);
        if (projectPaintButton != null) {
            projectPaintButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.12
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("05_project_bio3");
                }
            });
        }
        View projectRepgrogramButton = findResolvedView("zowiapps_load_project_reprogram_button", R.id.zowiapps_load_project_reprogram_button);
        if (projectRepgrogramButton != null) {
            projectRepgrogramButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.13
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("06_project_reprogram");
                }
            });
        }
        View projectHelloWorldButton = findResolvedView("zowiapps_load_project_helloworld_button", R.id.zowiapps_load_project_helloworld_button);
        if (projectHelloWorldButton != null) {
            projectHelloWorldButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.14
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("07_project_helloworld");
                }
            });
        }
        View projectBitbloq2Button = findResolvedView("zowiapps_load_project_bitbloq2_button", R.id.zowiapps_load_project_bitbloq2_button);
        if (projectBitbloq2Button != null) {
            projectBitbloq2Button.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.15
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("08_project_bitbloq2");
                }
            });
        }
        View projectAdivinawiButton = findResolvedView("zowiapps_load_project_adivinawi_button", R.id.zowiapps_load_project_adivinawi_button);
        if (projectAdivinawiButton != null) {
            projectAdivinawiButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.16
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("09_project_adivinawi");
                }
            });
        }
        View projectGravityButton = findResolvedView("zowiapps_load_project_gravity_button", R.id.zowiapps_load_project_gravity_button);
        if (projectGravityButton != null) {
            projectGravityButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.home.HomeViewActivity.17
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((HomePresenter) HomeViewActivity.this.getPresenter()).loadProject("10_project_gravity");
                }
            });
        }
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ((HomePresenter) getPresenter()).logAppStarted();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_HOME));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public HomePresenter resolvePresenter() {
        HomePresenter presenter = AndroidDependencyInjector.getInstance().provideHomePresenter();
        HomeWireframe wireframe = AndroidDependencyInjector.getInstance().provideHomeWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.interactive.home.HomeView
    public void setUnlockStatusMouthsEditor(boolean unlock) {
        if (this.loadMouthsEditorButton == null) {
            return;
        }
        this.loadMouthsEditorButton.setClickable(unlock);
        int drawable = unlock ? R.drawable.mouths_editor_button_selector : R.drawable.blocked_option_button;
        this.loadMouthsEditorButton.setButtonBackground(drawable, this);
    }

    static class HomePagerAdapter extends PagerAdapter {
        HomePagerAdapter() {
        }

        static class HomePages {
            public static final int PROJECTS = 1;
            public static final int ZOWI_APPS = 0;

            HomePages() {
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup collection, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = resolvePageId(collection, "activity_home_games_layout", R.id.activity_home_games_layout);
                    break;
                case 1:
                    resId = resolvePageId(collection, "activity_home_projects_layout", R.id.activity_home_projects_layout);
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
            return 2;
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
