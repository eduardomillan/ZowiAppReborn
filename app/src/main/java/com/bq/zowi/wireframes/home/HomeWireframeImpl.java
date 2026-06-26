package com.bq.zowi.wireframes.home;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.bq.zowi.views.interactive.achievements.AchievementsViewActivity;
import com.bq.zowi.views.interactive.pad.PadViewActivity;
import com.bq.zowi.views.interactive.projects.ProjectViewActivity;
import com.bq.zowi.views.interactive.settings.SettingsViewActivity;
import com.bq.zowi.views.interactive.timeline.TimelineActivity;
import com.bq.zowi.views.interactive.zowiapps.MouthsEditorActivity;
import com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMinigameActivity;
import com.bq.zowi.views.interactive.zowiapps.minigames.ZowiRunnerMinigameActivity;
import com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameActivity;
import com.bq.zowi.wireframes.interactive.InteractiveWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class HomeWireframeImpl extends InteractiveWireframeImpl implements HomeWireframe {
    public HomeWireframeImpl(FragmentActivity activity) {
        super(activity);
    }

    @Override // com.bq.zowi.wireframes.home.HomeWireframe
    public void presentProject(String projectId) {
        Intent i = new Intent(this.activity, (Class<?>) ProjectViewActivity.class);
        i.putExtra(ProjectViewActivity.PROJECT_ID_EXTRA, projectId);
        this.activity.startActivity(i);
    }

    @Override // com.bq.zowi.wireframes.home.HomeWireframe
    public void presentTimelineView() {
        Intent i = new Intent(this.activity, (Class<?>) TimelineActivity.class);
        this.activity.startActivity(i);
    }

    @Override // com.bq.zowi.wireframes.home.HomeWireframe
    public void presentPadView() {
        Intent i = new Intent(this.activity, (Class<?>) PadViewActivity.class);
        this.activity.startActivity(i);
    }

    @Override // com.bq.zowi.wireframes.home.HomeWireframe
    public void presentZowiSaysMinigameView() {
        Intent i = new Intent(this.activity, (Class<?>) ZowiSaysMinigameActivity.class);
        this.activity.startActivity(i);
    }

    @Override // com.bq.zowi.wireframes.home.HomeWireframe
    public void presentMouthsMinigameView() {
        Intent i = new Intent(this.activity, (Class<?>) MouthsMinigameActivity.class);
        this.activity.startActivity(i);
    }

    @Override // com.bq.zowi.wireframes.home.HomeWireframe
    public void presentMouthsEditorView() {
        Intent i = new Intent(this.activity, (Class<?>) MouthsEditorActivity.class);
        this.activity.startActivity(i);
    }

    @Override // com.bq.zowi.wireframes.home.HomeWireframe
    public void presentZowiRunnerMinigameView() {
        Intent i = new Intent(this.activity, (Class<?>) ZowiRunnerMinigameActivity.class);
        this.activity.startActivity(i);
    }

    @Override // com.bq.zowi.wireframes.home.HomeWireframe
    public void presentSettings() {
        Intent i = new Intent(this.activity, (Class<?>) SettingsViewActivity.class);
        this.activity.startActivity(i);
    }

    @Override // com.bq.zowi.wireframes.home.HomeWireframe
    public void presentAchievements() {
        Intent i = new Intent(this.activity, (Class<?>) AchievementsViewActivity.class);
        this.activity.startActivity(i);
    }
}
