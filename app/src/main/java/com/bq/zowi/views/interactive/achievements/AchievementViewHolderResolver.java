package com.bq.zowi.views.interactive.achievements;

import android.content.Context;
import android.view.ViewGroup;
import com.bq.zowi.R;
import com.bq.zowi.components.recyclerview.RecyclerResolver;
import com.bq.zowi.components.recyclerview.RecyclerViewHolder;
import com.bq.zowi.models.viewmodels.AchievementViewModel;

/* JADX INFO: loaded from: classes.dex */
public class AchievementViewHolderResolver extends RecyclerResolver<AchievementViewModel> {
    @Override // com.bq.zowi.components.recyclerview.RecyclerResolver
    protected RecyclerViewHolder<? extends AchievementViewModel> getViewHolderFromViewType(int layoutId, ViewGroup parent, Context context) {
        switch (layoutId) {
            case R.layout.achievement_row_view /* 2130968601 */:
                return new AchievementRowViewHolder(layoutId, parent, context);
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.components.recyclerview.RecyclerResolver
    public int getItemViewType(AchievementViewModel item) {
        return R.layout.achievement_row_view;
    }
}
