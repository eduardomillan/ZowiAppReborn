package com.bq.zowi.views.interactive.zowiapps.minigames;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialog;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogAchievement;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogFailure;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogPointsEarnedEnableRanking;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogRanking;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.models.viewmodels.RankingEntryViewModel;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter;
import com.bq.zowi.utils.ResourceResolver;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class MinigameBaseActivity<T extends MinigameBasePresenter<? extends MinigameBaseView, ?>> extends InteractiveBaseActivity<T> implements MinigameBaseView {
    protected MakerBoxDialogAchievement achievementLayout;
    protected Button helpButton;
    protected Button homeButton;
    protected MakerBoxDialog howToPlayLayout;
    protected TextView howToPlayText;
    protected Button playButton;
    protected MakerBoxDialogFailure pointsEarnedDisableRankingLayout;
    protected MakerBoxDialogPointsEarnedEnableRanking pointsEarnedEnableRankingLayout;
    protected Button rankingButton;
    protected MakerBoxDialogRanking rankingLayout;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        this.howToPlayLayout = (MakerBoxDialog) findViewById(R.id.minigame_how_to_play_layout);
        this.howToPlayText = (TextView) findViewById(R.id.minigame_how_to_play_text);
        this.achievementLayout = (MakerBoxDialogAchievement) findViewById(R.id.minigame_achievement_layout);
        this.pointsEarnedEnableRankingLayout = (MakerBoxDialogPointsEarnedEnableRanking) findViewById(R.id.minigame_points_earned_enable_ranking_layout);
        this.pointsEarnedDisableRankingLayout = (MakerBoxDialogFailure) findViewById(R.id.minigame_points_earned_disable_ranking_layout);
        this.rankingLayout = (MakerBoxDialogRanking) findViewById(R.id.minigame_ranking_layout);
        this.homeButton = (Button) findViewById(R.id.minigame_home_button);
        this.homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((MinigameBasePresenter) MinigameBaseActivity.this.getPresenter()).homeButtonPressed();
            }
        });
        this.helpButton = (Button) findViewById(R.id.minigame_help_button);
        this.helpButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((MinigameBasePresenter) MinigameBaseActivity.this.getPresenter()).helpButtonPressed();
            }
        });
        this.rankingButton = (Button) findViewById(R.id.minigame_ranking_button);
        this.rankingButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((MinigameBasePresenter) MinigameBaseActivity.this.getPresenter()).rankingButtonPressed();
            }
        });
        this.playButton = (Button) findViewById(R.id.minigame_play_button);
        this.playButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((MinigameBasePresenter) MinigameBaseActivity.this.getPresenter()).playButtonPressed();
                MinigameBaseActivity.this.playButton.setVisibility(4);
                MinigameBaseActivity.this.getWindow().addFlags(128);
            }
        });
        super.onPostCreate(savedInstanceState);
        getPresenter().gameReady();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseView
    public void showAchievementUnlock(AchievementViewModel achievementUnlock) {
        this.playButton.setVisibility(0);
        this.achievementLayout.setVisibility(0);
        this.achievementLayout.setAchievementTitle(ResourceResolver.getStringByResourceId(achievementUnlock.getTitleResourceId(), getResources(), getPackageName()));
        this.achievementLayout.setAchievementDescription(ResourceResolver.getStringByResourceId(achievementUnlock.getDescriptionResourceId(), getResources(), getPackageName()));
        this.achievementLayout.setAchievementDrawable(ResourceResolver.getDrawableByResourceId(achievementUnlock.getBadgeImageResourceId(), this));
        this.achievementLayout.setOnContinueButtonClickedListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((MinigameBasePresenter) MinigameBaseActivity.this.getPresenter()).achievementContinueButtonClicked();
            }
        });
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseView
    public void showPoinsEarned(int pointsEarned, boolean enableRanking) {
        this.playButton.setVisibility(0);
        this.achievementLayout.setVisibility(8);
        if (enableRanking) {
            this.pointsEarnedEnableRankingLayout.setPointsEarned(pointsEarned);
            this.pointsEarnedEnableRankingLayout.setVisibility(0);
            this.pointsEarnedEnableRankingLayout.setOnPlayerNameEnteredListener(new MakerBoxDialogPointsEarnedEnableRanking.OnPlayerNameEnteredListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseActivity.6
                @Override // com.bq.zowi.components.makerboxdialogs.MakerBoxDialogPointsEarnedEnableRanking.OnPlayerNameEnteredListener
                public void onPlayerNameEntered(String playerName) {
                    ((MinigameBasePresenter) MinigameBaseActivity.this.getPresenter()).playerNameEnteredForRanking(playerName);
                }
            });
            this.pointsEarnedEnableRankingLayout.setVisibility(0);
            return;
        }
        this.pointsEarnedDisableRankingLayout.setFailureDescriptionText(getString(R.string.games_points_earned_disable_ranking_subtitle));
        this.pointsEarnedDisableRankingLayout.setFailureMainText(getResources().getQuantityString(R.plurals.games_points_earned_text, pointsEarned, Integer.valueOf(pointsEarned)));
        this.pointsEarnedDisableRankingLayout.setOnContinueButtonClickedListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MinigameBaseActivity.this.pointsEarnedDisableRankingLayout.setVisibility(8);
            }
        });
        this.pointsEarnedDisableRankingLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseView
    public void showRanking(ArrayList<RankingEntryViewModel> ranking) {
        this.pointsEarnedEnableRankingLayout.setVisibility(8);
        this.rankingLayout.setRankingItems(ranking);
        this.rankingLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseView
    public void showHelp() {
        this.howToPlayLayout.setVisibility(0);
    }
}
