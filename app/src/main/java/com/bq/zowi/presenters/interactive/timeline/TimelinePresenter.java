package com.bq.zowi.presenters.interactive.timeline;

import com.bq.zowi.models.commands.GridCommand;
import com.bq.zowi.models.commands.TimelineCommand;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.timeline.TimelineView;
import com.bq.zowi.wireframes.timeline.TimelineWireframe;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface TimelinePresenter extends InteractiveBasePresenter<TimelineView, TimelineWireframe> {
    void addNewAnimationCommandButtonClicked();

    void addNewMouthCommandButtonClicked();

    void addNewMovementCommandButtonClicked();

    void gameReady();

    void helpButtonPressed();

    void homeButtonPressed();

    void loadAndResumeTimeline();

    void playTimelineButtonPressed(List<TimelineCommand> list);

    void saveTimeline(List<TimelineCommand> list);

    void stopTimelineButtonPressed();

    void timelineCommandSelected(GridCommand gridCommand);
}
