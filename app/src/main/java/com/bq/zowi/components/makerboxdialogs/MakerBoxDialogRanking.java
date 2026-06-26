package com.bq.zowi.components.makerboxdialogs;

import android.content.Context;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.bq.zowi.R;
import com.bq.zowi.components.recyclerview.RecyclerAdapter;
import com.bq.zowi.models.viewmodels.RankingEntryViewModel;
import com.bq.zowi.views.interactive.zowiapps.minigames.RankingViewHolderResolver;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MakerBoxDialogRanking extends MakerBoxDialogScrollable {
    private View emptyView;
    private View listView;
    private RecyclerView rankingRecyclerView;

    public MakerBoxDialogRanking(Context context) {
        super(context);
        init(context);
    }

    public MakerBoxDialogRanking(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MakerBoxDialogRanking(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(R.layout.component_makerbox_ranking, this);
            this.listView = findViewById(R.id.makerbox_dialog_ranking_list_layout);
            this.emptyView = findViewById(R.id.makerbox_dialog_ranking_empty_layout);
            this.rankingRecyclerView = (RecyclerView) findViewById(R.id.makerbox_dialog_ranking_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            this.rankingRecyclerView.setLayoutManager(layoutManager);
            this.rankingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    public void setRankingItems(ArrayList<RankingEntryViewModel> rankingItems) {
        if (rankingItems.size() > 0) {
            this.listView.setVisibility(0);
            this.emptyView.setVisibility(8);
            RecyclerAdapter<RankingEntryViewModel> adapter = new RecyclerAdapter<>(rankingItems, new RankingViewHolderResolver());
            this.rankingRecyclerView.setAdapter(adapter);
            float rankingEntryRowHeight = getResources().getDimension(R.dimen.maker_box_dialog_ranking_row_height);
            this.rankingRecyclerView.getLayoutParams().height = (int) (rankingItems.size() * rankingEntryRowHeight);
            for (RankingEntryViewModel item : rankingItems) {
                if (item.isLatestEntry) {
                    this.rankingRecyclerView.scrollToPosition(rankingItems.indexOf(item));
                    return;
                }
            }
            return;
        }
        this.listView.setVisibility(8);
        this.emptyView.setVisibility(0);
    }
}
