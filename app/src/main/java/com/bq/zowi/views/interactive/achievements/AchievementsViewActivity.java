package com.bq.zowi.views.interactive.achievements;

import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.components.recyclerview.RecyclerAdapter;
import com.bq.zowi.components.recyclerview.WrapContentLinearLayoutManager;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.presenters.interactive.achievements.AchievementsPresenter;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import com.bq.zowi.wireframes.achievements.AchievementsWireframe;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AchievementsViewActivity extends InteractiveBaseActivity<AchievementsPresenter> implements AchievementsView {
    private RecyclerView animationsRecyclerView;
    private RecyclerView gamesRecyclerView;
    private Button homeButton;
    private RecyclerView movementsRecyclerView;
    private Button unlocker1;
    private Button unlocker2;
    private Button unlocker3;
    private ViewPager viewpager;
    private boolean unlockerCondition1 = false;
    private boolean unlockerCondition2 = false;
    private boolean achievementsUnlocked = false;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_view);
        this.viewpager = (ViewPager) findViewById(R.id.activity_achievements_view_pager);
        int margin = (int) (getResources().getDimension(R.dimen.sections_pager_horizontal_margin) * 2.0f);
        this.viewpager.setPageMargin(-margin);
        this.viewpager.setOffscreenPageLimit(this.viewpager.getChildCount());
        this.viewpager.setAdapter(new AchievementsPagerAdapter());
        this.movementsRecyclerView = (RecyclerView) findViewById(R.id.activity_achievements_movements_list);
        WrapContentLinearLayoutManager movementsContentLinearLayoutManager = new WrapContentLinearLayoutManager(this);
        this.movementsRecyclerView.setLayoutManager(movementsContentLinearLayoutManager);
        this.animationsRecyclerView = (RecyclerView) findViewById(R.id.activity_achievements_animations_list);
        WrapContentLinearLayoutManager animationsContentLinearLayoutManager = new WrapContentLinearLayoutManager(this);
        this.animationsRecyclerView.setLayoutManager(animationsContentLinearLayoutManager);
        this.gamesRecyclerView = (RecyclerView) findViewById(R.id.activity_achievements_games_list);
        WrapContentLinearLayoutManager gamesContentLinearLayoutManager = new WrapContentLinearLayoutManager(this);
        this.gamesRecyclerView.setLayoutManager(gamesContentLinearLayoutManager);
        this.homeButton = (Button) findViewById(R.id.activity_achievements_home_button);
        this.homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.achievements.AchievementsViewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((AchievementsPresenter) AchievementsViewActivity.this.getPresenter()).homeButtonPressed();
            }
        });
        this.unlocker1 = (Button) findViewById(R.id.unlocker_button1);
        this.unlocker1.setOnTouchListener(new View.OnTouchListener() { // from class: com.bq.zowi.views.interactive.achievements.AchievementsViewActivity.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Grove.d("EASTER EGG: unlock 1", new Object[0]);
                AchievementsViewActivity.this.unlockerCondition1 = true;
                return false;
            }
        });
        this.unlocker2 = (Button) findViewById(R.id.unlocker_button2);
        this.unlocker2.setOnTouchListener(new View.OnTouchListener() { // from class: com.bq.zowi.views.interactive.achievements.AchievementsViewActivity.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Grove.d("EASTER EGG: unlock 2", new Object[0]);
                if (AchievementsViewActivity.this.unlockerCondition1) {
                    AchievementsViewActivity.this.unlockerCondition2 = true;
                }
                return false;
            }
        });
        this.unlocker3 = (Button) findViewById(R.id.unlocker_button3);
        this.unlocker3.setOnTouchListener(new View.OnTouchListener() { // from class: com.bq.zowi.views.interactive.achievements.AchievementsViewActivity.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Grove.d("EASTER EGG: unlock 3", new Object[0]);
                if (AchievementsViewActivity.this.unlockerCondition1 && AchievementsViewActivity.this.unlockerCondition2 && !AchievementsViewActivity.this.achievementsUnlocked) {
                    ((AchievementsPresenter) AchievementsViewActivity.this.getPresenter()).easterEggCombinationPressed();
                    AchievementsViewActivity.this.achievementsUnlocked = true;
                    Grove.d("EASTER EGG unlocked!!!", new Object[0]);
                }
                return false;
            }
        });
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ((AchievementsPresenter) getPresenter()).loadAchievements();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_ACHIEVEMENTS));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public AchievementsPresenter resolvePresenter() {
        AchievementsPresenter presenter = AndroidDependencyInjector.getInstance().provideAchievementsPresenter();
        AchievementsWireframe wireframe = AndroidDependencyInjector.getInstance().provideAchievementsWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.interactive.achievements.AchievementsView
    public void paintAchievements(List<AchievementViewModel> movementsList, List<AchievementViewModel> animationsList, List<AchievementViewModel> gamesList) {
        RecyclerAdapter<AchievementViewModel> movementsAdapter = new RecyclerAdapter<>(movementsList, new AchievementViewHolderResolver());
        this.movementsRecyclerView.setAdapter(movementsAdapter);
        RecyclerAdapter<AchievementViewModel> animationsAdapter = new RecyclerAdapter<>(animationsList, new AchievementViewHolderResolver());
        this.animationsRecyclerView.setAdapter(animationsAdapter);
        RecyclerAdapter<AchievementViewModel> gamesAdapter = new RecyclerAdapter<>(gamesList, new AchievementViewHolderResolver());
        this.gamesRecyclerView.setAdapter(gamesAdapter);
        float achievementRowHeight = getResources().getDimension(R.dimen.achievement_row_item_height);
        this.movementsRecyclerView.getLayoutParams().height = (int) (movementsList.size() * achievementRowHeight);
        this.animationsRecyclerView.getLayoutParams().height = (int) (animationsList.size() * achievementRowHeight);
        this.gamesRecyclerView.getLayoutParams().height = (int) (gamesList.size() * achievementRowHeight);
    }

    static class AchievementsPagerAdapter extends PagerAdapter {
        private final int NUM_PAGES = 3;

        AchievementsPagerAdapter() {
        }

        static class AchievementsPages {
            public static final int ANIMATIONS = 1;
            public static final int GAMES = 2;
            public static final int MOVEMENTS = 0;

            AchievementsPages() {
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup collection, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.activity_achievements_movements_layout;
                    break;
                case 1:
                    resId = R.id.activity_achievements_animations_layout;
                    break;
                case 2:
                    resId = R.id.activity_achievements_games_layout;
                    break;
            }
            return collection.findViewById(resId);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return 3;
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
