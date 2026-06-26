package com.bq.zowi.presenters.interactive.home;

import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.home.HomeView;
import com.bq.zowi.wireframes.home.HomeWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface HomePresenter extends InteractiveBasePresenter<HomeView, HomeWireframe> {
    void loadAchievements();

    void loadGamepad();

    void loadMouthsEditor();

    void loadMouthsMinigame();

    void loadProject(String str);

    void loadSettings();

    void loadTimeline();

    void loadZowiRunnerMinigame();

    void loadZowiSaysMinigame();

    void logAppStarted();
}
