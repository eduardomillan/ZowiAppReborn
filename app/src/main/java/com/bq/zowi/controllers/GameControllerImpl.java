package com.bq.zowi.controllers;

import android.content.SharedPreferences;
import com.bq.zowi.controllers.GameController;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.Map;
import rx.Single;
import rx.SingleSubscriber;

/* JADX INFO: loaded from: classes.dex */
public class GameControllerImpl implements GameController {
    private static final String GAME_IS_FIRST_PLAY_SHARED_PREFERENCE_SUFFIX = "_is_first_play";
    private static final String GAME_PROGRESS_SHARED_PREFERENCE_SUFFIX = "_progress";
    private Gson gson = buildGsonToSerializeDeserializeProgress();
    private SharedPreferences sharedPreferences;

    public GameControllerImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override // com.bq.zowi.controllers.GameController
    public Single<Void> resetGamesProgress() {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.GameControllerImpl.1
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                Map<String, ?> allPrefs = GameControllerImpl.this.sharedPreferences.getAll();
                SharedPreferences.Editor editor = GameControllerImpl.this.sharedPreferences.edit();
                for (String prefKey : allPrefs.keySet()) {
                    if (prefKey.endsWith(GameControllerImpl.GAME_IS_FIRST_PLAY_SHARED_PREFERENCE_SUFFIX) || prefKey.endsWith(GameControllerImpl.GAME_PROGRESS_SHARED_PREFERENCE_SUFFIX)) {
                        editor.remove(prefKey);
                    }
                }
                editor.commit();
                singleSubscriber.onSuccess(null);
            }
        });
    }

    @Override // com.bq.zowi.controllers.GameController
    public Single<Boolean> isFirstPlay(final GameController.GAME_ID gameId, final boolean updateFlag) {
        return Single.create(new Single.OnSubscribe<Boolean>() { // from class: com.bq.zowi.controllers.GameControllerImpl.2
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
                boolean isFirstPlayStoredFlag = GameControllerImpl.this.sharedPreferences.getBoolean(GameControllerImpl.getSharefPrefIsFirstPlayForGame(gameId), true);
                if (isFirstPlayStoredFlag && updateFlag) {
                    SharedPreferences.Editor editor = GameControllerImpl.this.sharedPreferences.edit();
                    editor.putBoolean(GameControllerImpl.getSharefPrefIsFirstPlayForGame(gameId), false);
                    editor.commit();
                }
                singleSubscriber.onSuccess(Boolean.valueOf(isFirstPlayStoredFlag));
            }
        });
    }

    @Override // com.bq.zowi.controllers.GameController
    public Single<Void> saveProgress(final GameController.GAME_ID gameId, final Object progressObject) {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.GameControllerImpl.3
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                String serializedProgressObject = GameControllerImpl.this.gson.toJson(progressObject);
                SharedPreferences.Editor editor = GameControllerImpl.this.sharedPreferences.edit();
                editor.putString(GameControllerImpl.getSharefPrefProgressForGame(gameId), serializedProgressObject);
                editor.commit();
                singleSubscriber.onSuccess(null);
            }
        });
    }

    @Override // com.bq.zowi.controllers.GameController
    public <T> Single<T> loadProgress(final GameController.GAME_ID gameId, final Type typeOfT) {
        return Single.create(new Single.OnSubscribe<T>() { // from class: com.bq.zowi.controllers.GameControllerImpl.4
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super T> singleSubscriber) {
                String string = GameControllerImpl.this.sharedPreferences.getString(GameControllerImpl.getSharefPrefProgressForGame(gameId), null);
                if (string == null) {
                    singleSubscriber.onSuccess(null);
                } else {
                    singleSubscriber.onSuccess((T) GameControllerImpl.this.gson.fromJson(string, typeOfT));
                }
            }
        });
    }

    @Override // com.bq.zowi.controllers.GameController
    public <T> Single<T> loadProgress(final GameController.GAME_ID gameId, final Class<T> classOfT) {
        return Single.create(new Single.OnSubscribe<T>() { // from class: com.bq.zowi.controllers.GameControllerImpl.5
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super T> singleSubscriber) {
                String string = GameControllerImpl.this.sharedPreferences.getString(GameControllerImpl.getSharefPrefProgressForGame(gameId), null);
                if (string == null) {
                    singleSubscriber.onSuccess(null);
                } else {
                    singleSubscriber.onSuccess((T) GameControllerImpl.this.gson.fromJson(string, (Class) classOfT));
                }
            }
        });
    }

    protected Gson buildGsonToSerializeDeserializeProgress() {
        return new Gson();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSharefPrefIsFirstPlayForGame(GameController.GAME_ID gameId) {
        return gameId.toString() + GAME_IS_FIRST_PLAY_SHARED_PREFERENCE_SUFFIX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSharefPrefProgressForGame(GameController.GAME_ID gameId) {
        return gameId.toString() + GAME_PROGRESS_SHARED_PREFERENCE_SUFFIX;
    }
}
