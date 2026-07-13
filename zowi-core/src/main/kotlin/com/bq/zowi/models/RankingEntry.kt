package com.bq.zowi.models

data class RankingEntry(
    val points: Int,
    val playerName: String,
    val timestamp: Long = System.currentTimeMillis() / 1000
) : Comparable<RankingEntry> {

    override fun compareTo(other: RankingEntry): Int {
        return when {
            points < other.points -> -1
            points > other.points -> 1
            else -> 0
        }
    }
}
