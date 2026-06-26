package com.bq.zowi.models;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
public class RankingEntry implements Comparable<RankingEntry> {
    public String playerName;
    public int points;
    public long timestamp = System.currentTimeMillis() / 1000;

    public RankingEntry(int points, String playerName) {
        this.points = points;
        this.playerName = playerName;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull RankingEntry rankingEntry) {
        if (this.points < rankingEntry.points) {
            return -1;
        }
        if (this.points > rankingEntry.points) {
            return 1;
        }
        return 0;
    }
}
