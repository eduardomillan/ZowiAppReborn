package com.bq.zowi.views.interactive.timeline;

import com.bq.zowi.models.commands.TimelineCommand;
import com.bq.zowi.views.interactive.timeline.AbstractDataProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TimelineDataProvider extends AbstractDataProvider {
    private TimelineData lastRemovedData;
    private int lastRemovedPosition = -1;
    private int addedElementCount = 0;
    private List<TimelineData> timelineDataList = new LinkedList();

    public List<TimelineCommand> getTimelineDataCommandsList() {
        ArrayList<TimelineCommand> timelineCommandArrayList = new ArrayList<>();
        for (TimelineData timelineData : this.timelineDataList) {
            timelineCommandArrayList.add(timelineData.getTimelineCommand());
        }
        return timelineCommandArrayList;
    }

    public void addTimelineDataFromCommand(TimelineCommand command) {
        List<TimelineData> list = this.timelineDataList;
        int i = this.addedElementCount;
        this.addedElementCount = i + 1;
        list.add(new TimelineData(command, i));
    }

    public void addTimelineDataFromCommadList(List<TimelineCommand> commandList) {
        for (TimelineCommand command : commandList) {
            addTimelineDataFromCommand(command);
        }
    }

    public TimelineCommand getTimelineCommandByTimelineDataId(long timelineDataId) {
        for (TimelineData timelineData : this.timelineDataList) {
            if (timelineData.getId() == timelineDataId) {
                return timelineData.getTimelineCommand();
            }
        }
        return null;
    }

    @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider
    public int getCount() {
        return this.timelineDataList.size();
    }

    @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider
    public TimelineData getItem(int index) {
        if (index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }
        return this.timelineDataList.get(index);
    }

    @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider
    public int undoLastRemoval() {
        int insertedPosition;
        if (this.lastRemovedData == null) {
            return -1;
        }
        if (this.lastRemovedPosition >= 0 && this.lastRemovedPosition < this.timelineDataList.size()) {
            insertedPosition = this.lastRemovedPosition;
        } else {
            insertedPosition = this.timelineDataList.size();
        }
        this.timelineDataList.add(insertedPosition, this.lastRemovedData);
        this.lastRemovedData = null;
        this.lastRemovedPosition = -1;
        return insertedPosition;
    }

    @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider
    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition != toPosition) {
            TimelineData item = this.timelineDataList.remove(fromPosition);
            this.timelineDataList.add(toPosition, item);
            this.lastRemovedPosition = -1;
        }
    }

    @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider
    public void removeItem(int position) {
        if (position >= 0 && position < this.timelineDataList.size()) {
            TimelineData removedItem = this.timelineDataList.remove(position);
            this.lastRemovedData = removedItem;
            this.lastRemovedPosition = position;
        }
    }

    public void removeItemsByIds(List<Long> idsToDelete) {
        for (Long idToDelete : idsToDelete) {
            Iterator<TimelineData> it = this.timelineDataList.iterator();
            while (true) {
                if (it.hasNext()) {
                    TimelineData timelineData = it.next();
                    if (timelineData.getId() == idToDelete.longValue()) {
                        removeItem(this.timelineDataList.indexOf(timelineData));
                        break;
                    }
                }
            }
        }
    }

    protected static final class TimelineData extends AbstractDataProvider.Data {
        private final long id;
        private final TimelineCommand timelineCommand;

        public TimelineData(TimelineCommand timelineCommand, long id) {
            this.timelineCommand = timelineCommand;
            this.id = id;
        }

        public TimelineCommand getTimelineCommand() {
            return this.timelineCommand;
        }

        @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider.Data
        public boolean isSectionHeader() {
            return false;
        }

        @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider.Data
        public int getViewType() {
            return 0;
        }

        @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider.Data
        public long getId() {
            return this.id;
        }

        public String toString() {
            return this.timelineCommand.getCommand().toString();
        }

        @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider.Data
        public int getSwipeReactionType() {
            return 8192;
        }

        @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider.Data
        public String getText() {
            return this.timelineCommand.getCommand().toString();
        }

        @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider.Data
        public boolean isPinnedToSwipeLeft() {
            return false;
        }

        @Override // com.bq.zowi.views.interactive.timeline.AbstractDataProvider.Data
        public void setPinnedToSwipeLeft(boolean pinedToSwipeLeft) {
        }
    }
}
