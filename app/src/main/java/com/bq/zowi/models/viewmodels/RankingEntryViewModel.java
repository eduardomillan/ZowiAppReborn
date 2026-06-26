package com.bq.zowi.models.viewmodels;

import com.bq.zowi.models.RankingEntry;

/* JADX INFO: loaded from: classes.dex */
public class RankingEntryViewModel {
    public boolean isLatestEntry;
    public String playerName;
    public int points;
    public int position = -1;

    public RankingEntryViewModel(int points, String playerName) {
        this.points = points;
        this.playerName = playerName;
    }

    public void setIsLatestEntry(boolean isLatestEntry) {
        this.isLatestEntry = isLatestEntry;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static RankingEntryViewModel rankingEntryViewModelFromRankingEntry(RankingEntry rankingEntry) {
        return new RankingEntryViewModel(rankingEntry.points, rankingEntry.playerName);
    }
}
