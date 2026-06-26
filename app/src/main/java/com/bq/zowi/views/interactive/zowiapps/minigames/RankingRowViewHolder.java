package com.bq.zowi.views.interactive.zowiapps.minigames;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.components.recyclerview.RecyclerViewHolder;
import com.bq.zowi.models.viewmodels.RankingEntryViewModel;

/* JADX INFO: loaded from: classes.dex */
public class RankingRowViewHolder extends RecyclerViewHolder<RankingEntryViewModel> {
    private Context context;
    private TextView playerNameTextView;
    private TextView pointsTextView;
    private TextView positionTextView;

    public RankingRowViewHolder(int resourceId, ViewGroup parent, Context context) {
        super(resourceId, parent, context);
        this.context = context;
        this.positionTextView = (TextView) this.itemView.findViewById(R.id.ranking_entry_position_textview);
        this.playerNameTextView = (TextView) this.itemView.findViewById(R.id.ranking_entry_row_player_name_textview);
        this.pointsTextView = (TextView) this.itemView.findViewById(R.id.ranking_entry_row_points_textview);
    }

    @Override // com.bq.zowi.components.recyclerview.RecyclerViewHolder
    public void bind(RankingEntryViewModel item) {
        this.positionTextView.setText(String.valueOf(item.position));
        this.playerNameTextView.setText(item.playerName);
        this.pointsTextView.setText(this.context.getResources().getQuantityString(R.plurals.ranking_points_text, item.points, Integer.valueOf(item.points)));
        if (item.isLatestEntry) {
            this.itemView.setBackgroundColor(this.context.getResources().getColor(R.color.maker_box_ranking_selected_row));
        } else if (item.position % 2 == 0) {
            this.itemView.setBackgroundColor(this.context.getResources().getColor(R.color.maker_box_even_row_background));
        } else {
            this.itemView.setBackgroundColor(this.context.getResources().getColor(R.color.maker_box_odd_row_background));
        }
    }
}
