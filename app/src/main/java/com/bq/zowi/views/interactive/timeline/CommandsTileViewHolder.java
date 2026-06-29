package com.bq.zowi.views.interactive.timeline;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import com.bq.zowi.R;
import com.bq.zowi.components.recyclerview.RecyclerViewHolder;
import com.bq.zowi.components.timeline.CommandTileView;
import com.bq.zowi.models.commands.GridCommand;

/* JADX INFO: loaded from: classes.dex */
public class CommandsTileViewHolder extends RecyclerViewHolder<GridCommand> implements View.OnClickListener {
    public CommandTileView commandTileView;
    private Context context;
    private GridCommand gridCommand;
    private final CommandItemListener itemListener;

    public interface CommandItemListener {
        void onCommandSelected(GridCommand gridCommand);
    }

    public CommandsTileViewHolder(int layoutId, ViewGroup parent, CommandItemListener itemListener, Context context) {
        super(layoutId, parent, context);
        this.context = context;
        this.commandTileView = (CommandTileView) this.itemView.findViewById(resolveViewId(this.itemView, "command_tile_view_item", R.id.command_tile_view_item));
        this.itemListener = itemListener;
        if (this.commandTileView != null) {
            this.commandTileView.setOnClickListener(this);
        }
    }

    @Override // com.bq.zowi.components.recyclerview.RecyclerViewHolder
    public void bind(GridCommand item) {
        this.gridCommand = item;
        if (item.isUnlocked()) {
            this.commandTileView.setCommandDrawable(GridCommandResourceResolver.resolveDrawable(this.context, item));
        } else {
            this.commandTileView.setCommandDrawable(ContextCompat.getDrawable(this.context, R.drawable.blocked_option_button));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.itemListener != null) {
            this.itemListener.onCommandSelected(this.gridCommand);
        }
    }

    private int resolveViewId(View root, String viewIdName, int fallbackViewId) {
        int viewId = root.getResources().getIdentifier(viewIdName, "id", root.getContext().getPackageName());
        return viewId != 0 ? viewId : fallbackViewId;
    }
}
