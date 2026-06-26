package com.bq.zowi.views.interactive.zowiapps.minigames;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.components.games.MouthGridItemView;
import com.bq.zowi.components.games.MouthGridLayout;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.MouthsMinigamePresenter;
import com.bq.zowi.utils.AnimationUtils;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframe;

/* JADX INFO: loaded from: classes.dex */
public class MouthsMinigameActivity extends MinigameBaseActivity<MouthsMinigamePresenter> implements MouthsMiniGameView {
    private final int GRID_COLUMNS = 6;
    private final int GRID_ROWS = 5;
    private MouthGridLayout mouthGrid;
    private ProgressBar progressBar;
    private View progressLayout;
    private TextView progressText;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_mouths_minigame_view", R.layout.activity_mouths_minigame_view);
        this.mouthGrid = (MouthGridLayout) findViewById(R.id.minigame_mouths_grid);
        this.mouthGrid.setMouthGridLayoutTouchListener(new MouthGridLayout.MouthGridLayoutTouchListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMinigameActivity.1
            @Override // com.bq.zowi.components.games.MouthGridLayout.MouthGridLayoutTouchListener
            public void sendCurrentMouth() {
                ((MouthsMinigamePresenter) MouthsMinigameActivity.this.getPresenter()).checkLedMouth(MouthsMinigameActivity.this.getBinaryGridRepresentation());
            }
        });
        isMouthGridTouchable(false);
        initializeMouthGrid(6, 5);
        this.progressLayout = findViewById(R.id.mouths_progress_layout);
        this.progressBar = (ProgressBar) findViewById(R.id.mouths_progressbar);
        this.progressText = (TextView) findViewById(R.id.mouths_progress_text);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_MOUTHS_GAME));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public MouthsMinigamePresenter resolvePresenter() {
        MouthsMinigamePresenter presenter = AndroidDependencyInjector.getInstance().provideMouthsMinigamePresenter();
        MinigameBaseWireframe wireframe = AndroidDependencyInjector.getInstance().provideMinigameWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMiniGameView
    public void showProgress() {
        this.progressLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMiniGameView
    public void hideProgress() {
        this.progressLayout.setVisibility(8);
        getWindow().clearFlags(128);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMiniGameView
    public void updateProgress(int progressValue) {
        AnimationUtils.animateProgressBarToProgress(this.progressBar, progressValue);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMiniGameView
    public void updateLevel(int level) {
        this.progressText.setText(getString(R.string.mouths_level_text, new Object[]{Integer.valueOf(level)}));
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMiniGameView
    public void isMouthGridTouchable(boolean state) {
        this.mouthGrid.setIsTouchable(state);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMiniGameView
    public void resetMouthGrid() {
        this.mouthGrid.resetGrid();
    }

    private void initializeMouthGrid(int numOfColumns, int numOfRows) {
        this.mouthGrid.setColumnCount(numOfColumns);
        this.mouthGrid.setRowCount(numOfRows);
        for (int y = 0; y < numOfRows; y++) {
            for (int x = 0; x < numOfColumns; x++) {
                this.mouthGrid.addView(new MouthGridItemView(this, x, y));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getBinaryGridRepresentation() {
        String binaryGrid = "";
        for (int i = 0; i < this.mouthGrid.getChildCount(); i++) {
            MouthGridItemView item = (MouthGridItemView) this.mouthGrid.getChildAt(i);
            binaryGrid = binaryGrid + (item.isLedOn ? "1" : "0");
        }
        return binaryGrid;
    }
}
