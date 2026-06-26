package com.bq.zowi.views.interactive.timeline;

import com.bq.zowi.models.commands.GridCommand;
import com.bq.zowi.models.commands.TimelineCommand;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.views.interactive.InteractiveBaseView;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface TimelineView extends InteractiveBaseView {
    void addTimelineCommandToTimeline(TimelineCommand timelineCommand);

    void addTimelineCommandsToTimeline(List<TimelineCommand> list);

    void hideCommandsSelector();

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    void showAchievementUnlock(AchievementViewModel achievementViewModel);

    void showCommandIsBeingPlayed(TimelineCommand timelineCommand);

    void showCommandsSelector(List<GridCommand> list);

    void showHelp();

    void showTimelineStoppedPlaying();
}
