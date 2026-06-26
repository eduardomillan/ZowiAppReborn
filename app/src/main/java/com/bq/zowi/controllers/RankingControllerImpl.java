package com.bq.zowi.controllers;

import android.content.SharedPreferences;
import com.bq.zowi.controllers.GameController;
import com.bq.zowi.models.RankingEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class RankingControllerImpl implements RankingController {
    private static final String GAME_RANKING_SHARED_PREFERENCE_SUFFIX = "_ranking";
    private SharedPreferences sharedPreferences;

    public RankingControllerImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override // com.bq.zowi.controllers.RankingController
    public Single<ArrayList<RankingEntry>> saveRankingEntry(final GameController.GAME_ID gameId, final RankingEntry rankingEntry) {
        return getRanking(gameId).map(new Func1<ArrayList<RankingEntry>, ArrayList<RankingEntry>>() { // from class: com.bq.zowi.controllers.RankingControllerImpl.1
            @Override // rx.functions.Func1
            public ArrayList<RankingEntry> call(ArrayList<RankingEntry> currentRanking) {
                currentRanking.add(0, rankingEntry);
                Gson gson = new Gson();
                String updatedRankingString = gson.toJson(currentRanking);
                SharedPreferences.Editor editor = RankingControllerImpl.this.sharedPreferences.edit();
                editor.putString(RankingControllerImpl.getSharefPrefRankingKeyForGame(gameId), updatedRankingString);
                editor.commit();
                Collections.sort(currentRanking, Collections.reverseOrder());
                return currentRanking;
            }
        });
    }

    @Override // com.bq.zowi.controllers.RankingController
    public Single<ArrayList<RankingEntry>> getRanking(final GameController.GAME_ID gameId) {
        return Single.create(new Single.OnSubscribe<ArrayList<RankingEntry>>() { // from class: com.bq.zowi.controllers.RankingControllerImpl.2
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super ArrayList<RankingEntry>> singleSubscriber) {
                String serializedRanking = RankingControllerImpl.this.sharedPreferences.getString(RankingControllerImpl.getSharefPrefRankingKeyForGame(gameId), null);
                if (serializedRanking == null) {
                    ArrayList<RankingEntry> emptyRanking = new ArrayList<>();
                    singleSubscriber.onSuccess(emptyRanking);
                }
                Gson gson = new Gson();
                ArrayList<RankingEntry> loadedRanking = (ArrayList) gson.fromJson(serializedRanking, new TypeToken<ArrayList<RankingEntry>>() { // from class: com.bq.zowi.controllers.RankingControllerImpl.2.1
                }.getType());
                Collections.sort(loadedRanking, Collections.reverseOrder());
                singleSubscriber.onSuccess(loadedRanking);
            }
        });
    }

    @Override // com.bq.zowi.controllers.RankingController
    public Single<Boolean> isScoreInTop10(GameController.GAME_ID gameId, final int points) {
        return getRanking(gameId).map(new Func1<ArrayList<RankingEntry>, Boolean>() { // from class: com.bq.zowi.controllers.RankingControllerImpl.3
            @Override // rx.functions.Func1
            public Boolean call(ArrayList<RankingEntry> currentRanking) {
                RankingEntry testRankingEntry = new RankingEntry(points, "");
                currentRanking.add(0, testRankingEntry);
                Collections.sort(currentRanking, Collections.reverseOrder());
                return Boolean.valueOf(currentRanking.indexOf(testRankingEntry) < 10);
            }
        });
    }

    @Override // com.bq.zowi.controllers.RankingController
    public Single<Void> resetAllRankings() {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.RankingControllerImpl.4
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                Map<String, ?> allPrefs = RankingControllerImpl.this.sharedPreferences.getAll();
                SharedPreferences.Editor editor = RankingControllerImpl.this.sharedPreferences.edit();
                for (String prefKey : allPrefs.keySet()) {
                    if (prefKey.endsWith(RankingControllerImpl.GAME_RANKING_SHARED_PREFERENCE_SUFFIX)) {
                        editor.remove(prefKey);
                    }
                }
                editor.commit();
                singleSubscriber.onSuccess(null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSharefPrefRankingKeyForGame(GameController.GAME_ID gameId) {
        return gameId.toString() + GAME_RANKING_SHARED_PREFERENCE_SUFFIX;
    }
}
