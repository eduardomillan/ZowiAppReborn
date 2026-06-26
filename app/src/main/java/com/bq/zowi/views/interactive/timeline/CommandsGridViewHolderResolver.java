package com.bq.zowi.views.interactive.timeline;

import android.content.Context;
import android.view.ViewGroup;
import com.bq.zowi.R;
import com.bq.zowi.components.recyclerview.RecyclerResolver;
import com.bq.zowi.components.recyclerview.RecyclerViewHolder;
import com.bq.zowi.models.commands.GridCommand;
import com.bq.zowi.views.interactive.timeline.CommandsTileViewHolder;

/* JADX INFO: loaded from: classes.dex */
public class CommandsGridViewHolderResolver extends RecyclerResolver<GridCommand> {
    private CommandsTileViewHolder.CommandItemListener itemListener;

    public CommandsGridViewHolderResolver(CommandsTileViewHolder.CommandItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override // com.bq.zowi.components.recyclerview.RecyclerResolver
    protected RecyclerViewHolder<? extends GridCommand> getViewHolderFromViewType(int layoutId, ViewGroup parent, Context context) {
        switch (layoutId) {
            case R.layout.command_tile_view /* 2130968617 */:
                return new CommandsTileViewHolder(layoutId, parent, this.itemListener, context);
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.components.recyclerview.RecyclerResolver
    public int getItemViewType(GridCommand item) {
        return R.layout.command_tile_view;
    }
}
