package com.bq.zowi.views.interactive.zowiapps.minigames;

import android.content.Context;
import android.view.ViewGroup;
import com.bq.zowi.R;
import com.bq.zowi.components.recyclerview.RecyclerResolver;
import com.bq.zowi.components.recyclerview.RecyclerViewHolder;
import com.bq.zowi.models.viewmodels.RankingEntryViewModel;

/* JADX INFO: loaded from: classes.dex */
public class RankingViewHolderResolver extends RecyclerResolver<RankingEntryViewModel> {
    @Override // com.bq.zowi.components.recyclerview.RecyclerResolver
    protected RecyclerViewHolder<? extends RankingEntryViewModel> getViewHolderFromViewType(int layoutId, ViewGroup parent, Context context) {
        switch (layoutId) {
            case R.layout.ranking_entry_row_view /* 2130968662 */:
                return new RankingRowViewHolder(layoutId, parent, context);
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.components.recyclerview.RecyclerResolver
    public int getItemViewType(RankingEntryViewModel item) {
        return R.layout.ranking_entry_row_view;
    }
}
