package com.bq.zowi.views.interactive.zowiapps.minigames;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialog;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenter;
import com.bq.zowi.utils.AnimationUtils;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframe;

/* JADX INFO: loaded from: classes.dex */
public class ZowiSaysMinigameActivity extends MinigameBaseActivity<ZowiSaysMinigamePresenter> implements ZowiSaysMinigameView {
    private ImageButton bottomLeftActionButton;
    private ImageButton bottomRightActionButton;
    private MakerBoxDialog lookAtZowiLayout;
    private ProgressBar progressBar;
    private View progressLayout;
    private TextView progressText;
    private ImageButton topLeftActionButton;
    private ImageButton topRightActionButton;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zowi_says_minigame_view);
        this.lookAtZowiLayout = (MakerBoxDialog) findViewById(R.id.zowi_says_look_at_zowi_layout);
        this.topLeftActionButton = (ImageButton) findViewById(R.id.zowi_says_top_left_button);
        this.topLeftActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ZowiSaysMinigamePresenter) ZowiSaysMinigameActivity.this.getPresenter()).topLeftActionPressed();
            }
        });
        this.topRightActionButton = (ImageButton) findViewById(R.id.zowi_says_top_right_button);
        this.topRightActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ZowiSaysMinigamePresenter) ZowiSaysMinigameActivity.this.getPresenter()).topRightActionPressed();
            }
        });
        this.bottomLeftActionButton = (ImageButton) findViewById(R.id.zowi_says_bottom_left_button);
        this.bottomLeftActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ZowiSaysMinigamePresenter) ZowiSaysMinigameActivity.this.getPresenter()).bottomLeftActionPressed();
            }
        });
        this.bottomRightActionButton = (ImageButton) findViewById(R.id.zowi_says_bottom_right_button);
        this.bottomRightActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ZowiSaysMinigamePresenter) ZowiSaysMinigameActivity.this.getPresenter()).bottomRightActionPressed();
            }
        });
        this.progressLayout = findViewById(R.id.zowi_says_progress_layout);
        this.progressBar = (ProgressBar) findViewById(R.id.zowi_says_progressbar);
        this.progressText = (TextView) findViewById(R.id.zowi_says_progress_text);
        this.zowiDependantViews = new View[]{this.playButton, this.topLeftActionButton, this.topRightActionButton, this.bottomRightActionButton, this.bottomLeftActionButton};
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_ZOWI_SAYS));
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public ZowiSaysMinigamePresenter resolvePresenter() {
        ZowiSaysMinigamePresenter presenter = AndroidDependencyInjector.getInstance().provideZowiSaysMinigamePresenter();
        MinigameBaseWireframe wireframe = AndroidDependencyInjector.getInstance().provideMinigameWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseView
    public void showZowiName(String zowiName) {
        super.showZowiName(zowiName);
        this.howToPlayText.setText(getResources().getString(R.string.zowi_says_how_to_play_text, zowiName));
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameView
    public void blockUserControls() {
        this.lookAtZowiLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameView
    public void showUserControls() {
        this.lookAtZowiLayout.setVisibility(8);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameView
    public void showProgress() {
        this.progressLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameView
    public void hideProgress() {
        this.progressLayout.setVisibility(8);
        getWindow().clearFlags(128);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameView
    public void setProgressValue(int progressValue, int userMovement, int totalMovements) {
        AnimationUtils.animateProgressBarToProgress(this.progressBar, progressValue);
        this.progressText.setText(userMovement + " / " + totalMovements);
    }
}
