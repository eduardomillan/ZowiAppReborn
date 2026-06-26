package com.bq.zowi.views.interactive.timeline;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialog;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogScrollable;
import com.bq.zowi.components.recyclerview.RecyclerAdapter;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.commands.GridCommand;
import com.bq.zowi.models.commands.TimelineCommand;
import com.bq.zowi.presenters.interactive.timeline.TimelinePresenter;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import com.bq.zowi.views.interactive.timeline.CommandsTileViewHolder;
import com.bq.zowi.views.interactive.timeline.TimelineDraggableItemAdapter;
import com.bq.zowi.wireframes.timeline.TimelineWireframe;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TimelineActivity extends InteractiveBaseActivity<TimelinePresenter> implements TimelineView, CommandsTileViewHolder.CommandItemListener, TimelineDraggableItemAdapter.ItemViewHolder.ItemChangeListener, TimelineDraggableItemAdapter.ItemViewHolder.ItemClickListener, TimelineDraggableItemAdapter.ItemViewHolder.ItemDeleteClickListener, TimelineDraggableItemAdapter.OnMoveItemListener, RecyclerViewDragDropManager.OnItemDragEventListener {
    private Button addAnimationButton;
    private Button addMouthButton;
    private Button addMovementButton;
    private GridLayoutManager commandsLayoutManager;
    private RecyclerView commandsRecyclerView;
    private MakerBoxDialogScrollable commandsSelectorDialog;
    private RecyclerAdapter<GridCommand> commmandsAdapter;
    private LinearLayout emptyTimelineLayout;
    private Button helpButton;
    private Button homeButton;
    private MakerBoxDialog howToPlayLayout;
    private TextView howToPlayText;
    private boolean isTimelinePlaying = false;
    private Button playButton;
    private Button stopButton;
    private TimelineDraggableItemAdapter timelineAdapter;
    private RecyclerView.LayoutManager timelineLayoutManager;
    private RecyclerView timelineRecyclerView;
    private RecyclerViewDragDropManager timelineRecyclerViewDragDropManager;
    private RecyclerView.Adapter timelineWrappedAdapter;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_timeline_view);
        this.howToPlayLayout = (MakerBoxDialog) findViewById(R.id.timeline_how_to_play_layout);
        this.howToPlayText = (TextView) findViewById(R.id.timeline_how_to_play_text);
        this.homeButton = (Button) findViewById(R.id.timeline_home_button);
        this.homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.timeline.TimelineActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((TimelinePresenter) TimelineActivity.this.getPresenter()).homeButtonPressed();
            }
        });
        this.helpButton = (Button) findViewById(R.id.timeline_help_button);
        this.helpButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.timeline.TimelineActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((TimelinePresenter) TimelineActivity.this.getPresenter()).helpButtonPressed();
            }
        });
        this.playButton = (Button) findViewById(R.id.activity_timeline_play_button);
        this.playButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.timeline.TimelineActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TimelineActivity.this.timelineAdapter.getDataProvider().getCount() > 0) {
                    TimelineActivity.this.isTimelinePlaying = true;
                    TimelineActivity.this.timelineAdapter.markAllItemsAsTimelinePlaying(true);
                    ((TimelinePresenter) TimelineActivity.this.getPresenter()).playTimelineButtonPressed(TimelineActivity.this.timelineAdapter.getDataProvider().getTimelineDataCommandsList());
                    TimelineActivity.this.playButton.setVisibility(8);
                    TimelineActivity.this.stopButton.setVisibility(0);
                }
            }
        });
        this.stopButton = (Button) findViewById(R.id.activity_timeline_stop_button);
        this.stopButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.timeline.TimelineActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((TimelinePresenter) TimelineActivity.this.getPresenter()).stopTimelineButtonPressed();
                TimelineActivity.this.playButton.setVisibility(0);
                TimelineActivity.this.stopButton.setVisibility(8);
            }
        });
        this.addMovementButton = (Button) findViewById(R.id.activity_timeline_add_movement_button);
        this.addMovementButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.timeline.TimelineActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((TimelinePresenter) TimelineActivity.this.getPresenter()).addNewMovementCommandButtonClicked();
            }
        });
        this.addAnimationButton = (Button) findViewById(R.id.activity_timeline_add_animation_button);
        this.addAnimationButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.timeline.TimelineActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((TimelinePresenter) TimelineActivity.this.getPresenter()).addNewAnimationCommandButtonClicked();
            }
        });
        this.addMouthButton = (Button) findViewById(R.id.activity_timeline_add_mouth_button);
        this.addMouthButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.timeline.TimelineActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((TimelinePresenter) TimelineActivity.this.getPresenter()).addNewMouthCommandButtonClicked();
            }
        });
        this.emptyTimelineLayout = (LinearLayout) findViewById(R.id.timeline_empty_message);
        this.timelineRecyclerView = (RecyclerView) findViewById(R.id.activity_timeline_recycler_view);
        this.timelineLayoutManager = new LinearLayoutManager(this, 0, false);
        this.timelineRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
        this.timelineRecyclerViewDragDropManager.setInitiateOnLongPress(true);
        this.timelineRecyclerViewDragDropManager.setInitiateOnMove(false);
        this.timelineAdapter = new TimelineDraggableItemAdapter(new TimelineDataProvider(), this, this, this, this);
        this.timelineWrappedAdapter = this.timelineRecyclerViewDragDropManager.createWrappedAdapter(this.timelineAdapter);
        this.timelineRecyclerView.setLayoutManager(this.timelineLayoutManager);
        this.timelineRecyclerView.setAdapter(this.timelineWrappedAdapter);
        this.timelineRecyclerViewDragDropManager.attachRecyclerView(this.timelineRecyclerView);
        this.timelineRecyclerViewDragDropManager.setOnItemDragEventListener(this);
        this.commandsSelectorDialog = (MakerBoxDialogScrollable) findViewById(R.id.timeline_actions_dialog);
        this.commandsRecyclerView = (RecyclerView) findViewById(R.id.activity_timeline_commands_recycler_view);
        this.commandsLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.makerbox_dialog_scrollable_columns));
        this.commandsRecyclerView.setLayoutManager(this.commandsLayoutManager);
        this.commandsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.commmandsAdapter = new RecyclerAdapter<>(new CommandsGridViewHolderResolver(this));
        this.commandsRecyclerView.setAdapter(this.commmandsAdapter);
        this.zowiDependantViews = new View[]{this.playButton};
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ((TimelinePresenter) getPresenter()).gameReady();
        ((TimelinePresenter) getPresenter()).loadAndResumeTimeline();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_TIMELINE));
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseView
    public void showZowiName(String zowiName) {
        super.showZowiName(zowiName);
        this.howToPlayText.setText(getResources().getString(R.string.timeline_how_to_play_text, zowiName));
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineView
    public void showHelp() {
        this.howToPlayLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineView
    public void showCommandIsBeingPlayed(TimelineCommand playingTimelineCommand) {
        if (playingTimelineCommand != null) {
            this.isTimelinePlaying = true;
        }
        List<TimelineCommand> timelineCommands = this.timelineAdapter.getDataProvider().getTimelineDataCommandsList();
        for (TimelineCommand timelineCommand : timelineCommands) {
            if (timelineCommand.equals(playingTimelineCommand)) {
                timelineCommand.setIsBeingPlayed(true);
                int scrollOffset = timelineCommands.indexOf(timelineCommand) > 0 ? 1 : 0;
                this.timelineRecyclerView.smoothScrollToPosition(timelineCommands.indexOf(timelineCommand) + scrollOffset);
            } else {
                timelineCommand.setIsBeingPlayed(false);
            }
        }
        this.timelineAdapter.notifyDataSetChanged();
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineView
    public void showTimelineStoppedPlaying() {
        this.isTimelinePlaying = false;
        showCommandIsBeingPlayed(null);
        this.timelineAdapter.markAllItemsAsTimelinePlaying(false);
        this.playButton.setVisibility(0);
        this.stopButton.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public TimelinePresenter resolvePresenter() {
        TimelinePresenter timelinePresenter = AndroidDependencyInjector.getInstance().provideTimelinePresenter();
        TimelineWireframe wireframe = AndroidDependencyInjector.getInstance().provideTimelineWireframe(this);
        timelinePresenter.bindViewAndWireframe(this, wireframe);
        return timelinePresenter;
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineView
    public void addTimelineCommandToTimeline(TimelineCommand timelineCommand) {
        this.emptyTimelineLayout.setVisibility(8);
        this.timelineAdapter.getDataProvider().addTimelineDataFromCommand(timelineCommand);
        this.timelineAdapter.notifyItemInserted(this.timelineAdapter.getItemCount() - 1);
        this.timelineRecyclerView.smoothScrollToPosition(this.timelineAdapter.getItemCount() - 1);
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineView
    public void addTimelineCommandsToTimeline(List<TimelineCommand> timelineCommands) {
        this.timelineAdapter.getDataProvider().addTimelineDataFromCommadList(timelineCommands);
        this.timelineAdapter.notifyDataSetChanged();
        if (this.timelineAdapter.getItemCount() == 0) {
            this.emptyTimelineLayout.setVisibility(0);
        } else {
            this.emptyTimelineLayout.setVisibility(8);
        }
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineView
    public void showCommandsSelector(List<GridCommand> commandList) {
        this.commandsSelectorDialog.setVisibility(0);
        this.commmandsAdapter.setItems(commandList);
        float rowHeight = getResources().getDimension(R.dimen.achievement_row_item_height);
        int columns = getResources().getInteger(R.integer.makerbox_dialog_scrollable_columns);
        int numberOfRows = commandList.size() / columns;
        if (commandList.size() % columns != 0) {
            numberOfRows++;
        }
        this.commandsRecyclerView.getLayoutParams().height = (int) (numberOfRows * rowHeight);
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineView
    public void hideCommandsSelector() {
        this.commandsSelectorDialog.setVisibility(8);
    }

    @Override // com.bq.zowi.views.BaseActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this.timelineRecyclerViewDragDropManager.cancelDrag();
        super.onPause();
        this.timelineAdapter.markAllItemsAsTimelinePlaying(false);
        ((TimelinePresenter) getPresenter()).saveTimeline(this.timelineAdapter.getDataProvider().getTimelineDataCommandsList());
    }

    @Override // com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        if (this.timelineRecyclerViewDragDropManager != null) {
            this.timelineRecyclerViewDragDropManager.release();
            this.timelineRecyclerViewDragDropManager = null;
        }
        if (this.timelineRecyclerView != null) {
            this.timelineRecyclerView.setItemAnimator(null);
            this.timelineRecyclerView.setAdapter(null);
            this.timelineRecyclerView = null;
        }
        if (this.timelineWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(this.timelineWrappedAdapter);
            this.timelineWrappedAdapter = null;
        }
        this.timelineAdapter = null;
        this.timelineLayoutManager = null;
        super.onDestroy();
    }

    @Override // com.bq.zowi.views.interactive.timeline.CommandsTileViewHolder.CommandItemListener
    public void onCommandSelected(GridCommand command) {
        if (command.isUnlocked()) {
            ((TimelinePresenter) getPresenter()).timelineCommandSelected(command);
        }
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineDraggableItemAdapter.ItemViewHolder.ItemChangeListener
    public void onRepetitionsChanged(long itemId, int repetitions) {
        TimelineCommand timelineCommand = this.timelineAdapter.getDataProvider().getTimelineCommandByTimelineDataId(itemId);
        if (timelineCommand != null) {
            timelineCommand.setRepetitions(repetitions);
        }
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineDraggableItemAdapter.ItemViewHolder.ItemChangeListener
    public void onDurationChanged(long itemId, long duration) {
        TimelineCommand timelineCommand = this.timelineAdapter.getDataProvider().getTimelineCommandByTimelineDataId(itemId);
        if (timelineCommand != null) {
            timelineCommand.getCommand().setDuration(duration);
        }
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineDraggableItemAdapter.ItemViewHolder.ItemChangeListener
    public void onDirectionChanged(long itemId, Command.Direction direction) {
        TimelineCommand timelineCommand = this.timelineAdapter.getDataProvider().getTimelineCommandByTimelineDataId(itemId);
        if (timelineCommand != null) {
            timelineCommand.getCommand().setDirection(direction);
        }
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineDraggableItemAdapter.ItemViewHolder.ItemClickListener
    public void onItemClicked(int position) {
        if (!this.isTimelinePlaying && position != -1) {
            this.isTimelinePlaying = true;
            List<TimelineCommand> singleCommandList = new ArrayList<>();
            singleCommandList.add(this.timelineAdapter.getDataProvider().getItem(position).getTimelineCommand());
            ((TimelinePresenter) getPresenter()).playTimelineButtonPressed(singleCommandList);
            this.playButton.setVisibility(8);
            this.stopButton.setVisibility(0);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager.OnItemDragEventListener
    public void onItemDragStarted(int position) {
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager.OnItemDragEventListener
    public void onItemDragPositionChanged(int fromPosition, int toPosition) {
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager.OnItemDragEventListener
    public void onItemDragFinished(int fromPosition, int toPosition, boolean result) {
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineDraggableItemAdapter.OnMoveItemListener
    public void onMoveItem(int fromPosition, int toPosition) {
    }

    @Override // com.bq.zowi.views.interactive.timeline.TimelineDraggableItemAdapter.ItemViewHolder.ItemDeleteClickListener
    public void onItemDeleteClicked(int position) {
        this.timelineAdapter.getDataProvider().removeItem(position);
        this.timelineAdapter.notifyItemRemoved(position);
        if (this.timelineAdapter.getItemCount() == 0) {
            this.emptyTimelineLayout.setVisibility(0);
        }
    }
}
