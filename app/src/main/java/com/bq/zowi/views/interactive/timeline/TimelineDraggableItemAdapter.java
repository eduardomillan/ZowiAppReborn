package com.bq.zowi.views.interactive.timeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.bq.zowi.R;
import com.bq.zowi.components.timeline.SelectedCommandRowView;
import com.bq.zowi.models.commands.AnimationCommand;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.commands.MouthCommand;
import com.bq.zowi.models.commands.TimelineCommand;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.timeline.TimelineDataProvider;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TimelineDraggableItemAdapter extends RecyclerView.Adapter<ItemViewHolder> implements DraggableItemAdapter<ItemViewHolder> {
    private static final int[] EMPTY_STATE = new int[0];
    private Context context;
    private ItemViewHolder.ItemChangeListener itemChangeListener;
    private ItemViewHolder.ItemClickListener itemClickListener;
    private ItemViewHolder.ItemDeleteClickListener itemDeleteClickListener;
    private OnMoveItemListener onMoveItemListener;
    private TimelineDataProvider provider;

    public interface OnMoveItemListener {
        void onMoveItem(int i, int i2);
    }

    public static class ItemViewHolder extends AbstractDraggableItemViewHolder implements SelectedCommandRowView.ChangeListener {
        public boolean clickable;
        public RelativeLayout container;
        public Button deleteButton;
        private ItemChangeListener itemChangeListener;
        public SelectedCommandRowView selectedCommandRowView;
        public View selectedCommandRowViewContent;

        public interface ItemChangeListener {
            void onDirectionChanged(long j, Command.Direction direction);

            void onDurationChanged(long j, long j2);

            void onRepetitionsChanged(long j, int i);
        }

        public interface ItemClickListener {
            void onItemClicked(int i);
        }

        public interface ItemDeleteClickListener {
            void onItemDeleteClicked(int i);
        }

        public ItemViewHolder(View v, ItemChangeListener itemChangeListener, final ItemClickListener itemClickListener, final ItemDeleteClickListener itemDeleteClickListener) {
            super(v);
            this.itemChangeListener = null;
            this.container = (RelativeLayout) v.findViewById(R.id.container);
            this.deleteButton = (Button) v.findViewById(R.id.delete_button);
            this.selectedCommandRowView = (SelectedCommandRowView) v.findViewById(R.id.selected_command_row_view_item);
            this.selectedCommandRowViewContent = this.selectedCommandRowView.findViewById(R.id.selected_command_row_view_content);
            this.itemChangeListener = itemChangeListener;
            this.selectedCommandRowView.setSelectedCommandRowViewChangesListener(this);
            this.container.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.timeline.TimelineDraggableItemAdapter.ItemViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (itemClickListener != null && ItemViewHolder.this.clickable) {
                        itemClickListener.onItemClicked(ItemViewHolder.this.getAdapterPosition());
                    }
                }
            });
            this.deleteButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.timeline.TimelineDraggableItemAdapter.ItemViewHolder.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (itemDeleteClickListener != null && ItemViewHolder.this.clickable) {
                        itemDeleteClickListener.onItemDeleteClicked(ItemViewHolder.this.getAdapterPosition());
                    }
                }
            });
        }

        public void setClickable(boolean clickable) {
            this.clickable = clickable;
        }

        @Override // com.bq.zowi.components.timeline.SelectedCommandRowView.ChangeListener
        public void onRepetitionsChanged(int repetitions) {
            if (this.itemChangeListener != null) {
                this.itemChangeListener.onRepetitionsChanged(getItemId(), repetitions);
            }
        }

        @Override // com.bq.zowi.components.timeline.SelectedCommandRowView.ChangeListener
        public void onDurationChanged(long duration) {
            if (this.itemChangeListener != null) {
                this.itemChangeListener.onDurationChanged(getItemId(), duration);
            }
        }

        @Override // com.bq.zowi.components.timeline.SelectedCommandRowView.ChangeListener
        public void onDirectionChanged(Command.Direction direction) {
            if (this.itemChangeListener != null) {
                this.itemChangeListener.onDirectionChanged(getItemId(), direction);
            }
        }
    }

    public TimelineDraggableItemAdapter(TimelineDataProvider dataProvider, ItemViewHolder.ItemChangeListener itemChangeListener, ItemViewHolder.ItemClickListener itemClickListener, ItemViewHolder.ItemDeleteClickListener itemDeleteClickListener, OnMoveItemListener onMoveItemListener) {
        this.itemChangeListener = null;
        this.itemClickListener = null;
        this.itemDeleteClickListener = null;
        this.onMoveItemListener = null;
        this.provider = dataProvider;
        this.itemChangeListener = itemChangeListener;
        this.itemClickListener = itemClickListener;
        this.itemDeleteClickListener = itemDeleteClickListener;
        this.onMoveItemListener = onMoveItemListener;
        setHasStableIds(true);
    }

    public TimelineDataProvider getDataProvider() {
        return this.provider;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        return this.provider.getItem(position).getId();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return this.provider.getItem(position).getViewType();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View v = inflater.inflate(R.layout.timeline_selected_command_row_view, parent, false);
        return new ItemViewHolder(v, this.itemChangeListener, this.itemClickListener, this.itemDeleteClickListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        TimelineDataProvider.TimelineData item = this.provider.getItem(position);
        TimelineCommand timelineCommand = item.getTimelineCommand();
        Command command = timelineCommand.getCommand();
        holder.selectedCommandRowView.setIcon(TimelineSelectedCommandIconResolver.resolveIcon(this.context, timelineCommand));
        holder.selectedCommandRowView.setIsRepeatable(command.isRepeatible());
        holder.selectedCommandRowView.setAllowedDirections(command.getAllowedDirections());
        holder.selectedCommandRowView.setAllowedDurations(command.getAllowedDurations());
        holder.selectedCommandRowView.setDuration(command.getDuration());
        holder.selectedCommandRowView.setDirection(command.getDirection());
        holder.selectedCommandRowView.setRepetitions(Integer.valueOf(timelineCommand.getRepetitions()));
        if (command instanceof AnimationCommand) {
            holder.selectedCommandRowViewContent.setBackgroundResource(R.drawable.maker_box_animations);
        } else if (command instanceof MouthCommand) {
            holder.selectedCommandRowViewContent.setBackgroundResource(R.drawable.maker_box_faces);
        } else {
            holder.selectedCommandRowViewContent.setBackgroundResource(R.drawable.maker_box_moves);
        }
        if (timelineCommand.isThisCommandBeingPlayed()) {
            holder.selectedCommandRowViewContent.animate().scaleX(1.09f).scaleY(1.09f).setDuration(100L);
        } else {
            holder.selectedCommandRowViewContent.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100L);
        }
        if (timelineCommand.isTimelineBeingPlayed()) {
            holder.selectedCommandRowView.disableMultipleStateButtons();
            holder.deleteButton.setVisibility(4);
            holder.setClickable(false);
            return;
        }
        holder.selectedCommandRowView.enableMultipleStateButtons();
        holder.deleteButton.setVisibility(0);
        holder.setClickable(true);
        int dragState = holder.getDragStateFlags();
        if ((dragState & 1) != 0) {
            holder.deleteButton.setVisibility(4);
        } else {
            holder.deleteButton.setVisibility(0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.provider.getCount();
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
    public void onMoveItem(int fromPosition, int toPosition) {
        Grove.d("onMoveItem(fromPosition = " + fromPosition + ", toPosition = " + toPosition + ")", new Object[0]);
        if (fromPosition != toPosition) {
            this.provider.moveItem(fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            if (this.onMoveItemListener != null) {
                this.onMoveItemListener.onMoveItem(fromPosition, toPosition);
            }
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
    public boolean onCheckCanStartDrag(ItemViewHolder holder, int position, int x, int y) {
        View containerView = holder.container;
        int offsetX = containerView.getLeft() + ((int) (ViewCompat.getTranslationX(containerView) + 0.5f));
        int offsetY = containerView.getTop() + ((int) (ViewCompat.getTranslationY(containerView) + 0.5f));
        return hitTest(containerView, x - offsetX, y - offsetY);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
    public ItemDraggableRange onGetItemDraggableRange(ItemViewHolder holder, int position) {
        return null;
    }

    public void markAllItemsAsTimelinePlaying(boolean isTimelineBeingPlayed) {
        List<TimelineCommand> items = this.provider.getTimelineDataCommandsList();
        for (TimelineCommand timelineCommand : items) {
            timelineCommand.setIsTimelineBeingPlayed(isTimelineBeingPlayed);
        }
    }

    private static void clearState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(EMPTY_STATE);
        }
    }

    private static boolean hitTest(View v, int x, int y) {
        int tx = (int) (ViewCompat.getTranslationX(v) + 0.5f);
        int ty = (int) (ViewCompat.getTranslationY(v) + 0.5f);
        int left = v.getLeft() + tx;
        int right = v.getRight() + tx;
        int top = v.getTop() + ty;
        int bottom = v.getBottom() + ty;
        return x >= left && x <= right && y >= top && y <= bottom;
    }
}
