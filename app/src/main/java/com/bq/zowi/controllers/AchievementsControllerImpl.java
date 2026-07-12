package com.bq.zowi.controllers;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.utils.FileReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Iterator;
import rx.Single;
import rx.SingleSubscriber;

/* JADX INFO: loaded from: classes.dex */
public class AchievementsControllerImpl implements AchievementsController {
    private AssetManager assetManager;
    private SharedPreferences sharedPreferences;
    private final String SP_ACHIEVEMENTS_LIST = "achievementsList";
    private final String SP_ACHIEVEMENTS_LIST_VERSION = "achievementsListVersion";
    private final int ACHIEVEMENTS_LIST_VERSION = 2;
    private final String INITIAL_ACHIEVEMENTS_JSON_FILE_PATH = "achievements/initial_list.json";

    public AchievementsControllerImpl(SharedPreferences sharedPreferences, AssetManager assetManager) {
        this.sharedPreferences = sharedPreferences;
        this.assetManager = assetManager;
        bootstrapAchievements();
    }

    @Override // com.bq.zowi.controllers.AchievementsController
    public void resetAchievementsList() {
        String initialAchievementsJsonString = FileReader.readFielAsString(this.assetManager, "achievements/initial_list.json");
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString("achievementsList", initialAchievementsJsonString);
        editor.commit();
    }

    @Override // com.bq.zowi.controllers.AchievementsController
    public Single<ArrayList<Achievement>> getAchievementsList() {
        return Single.create(new Single.OnSubscribe<ArrayList<Achievement>>() { // from class: com.bq.zowi.controllers.AchievementsControllerImpl.1
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super ArrayList<Achievement>> singleSubscriber) {
                singleSubscriber.onSuccess(AchievementsControllerImpl.this.retrieveAchievementsFromSharedPreferences());
            }
        });
    }

    @Override // com.bq.zowi.controllers.AchievementsController
    public Single<Achievement> unlockAchievement(final Achievement.Id achievementId) {
        return Single.create(new Single.OnSubscribe<Achievement>() { // from class: com.bq.zowi.controllers.AchievementsControllerImpl.2
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Achievement> singleSubscriber) {
                ArrayList<Achievement> achievementsList = AchievementsControllerImpl.this.retrieveAchievementsFromSharedPreferences();
                Achievement unlockedAchievement = null;
                Iterator<Achievement> it = achievementsList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Achievement achievement = it.next();
                    if (achievement.id.equals(achievementId.toString())) {
                        achievement.unlocked = true;
                        unlockedAchievement = achievement;
                        break;
                    }
                }
                Gson gson = new Gson();
                String serializedList = gson.toJson(achievementsList);
                SharedPreferences.Editor editor = AchievementsControllerImpl.this.sharedPreferences.edit();
                editor.putString("achievementsList", serializedList);
                editor.commit();
                if (unlockedAchievement != null) {
                    singleSubscriber.onSuccess(unlockedAchievement);
                } else {
                    singleSubscriber.onError(new IllegalStateException("The achievement ID could not be found in achievement list retrievedfrom Shared Preferences."));
                }
            }
        });
    }

    @Override // com.bq.zowi.controllers.AchievementsController
    public Single<Achievement> getAchievement(final Achievement.Id achievementId) {
        return Single.create(new Single.OnSubscribe<Achievement>() { // from class: com.bq.zowi.controllers.AchievementsControllerImpl.3
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Achievement> singleSubscriber) {
                ArrayList<Achievement> achievementsList = AchievementsControllerImpl.this.retrieveAchievementsFromSharedPreferences();
                for (Achievement achievement : achievementsList) {
                    if (achievement.id.equals(achievementId.toString())) {
                        singleSubscriber.onSuccess(achievement);
                    }
                }
            }
        });
    }

    @Override // com.bq.zowi.controllers.AchievementsController
    public Single<Void> unlockAllAchievements() {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.AchievementsControllerImpl.4
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                ArrayList<Achievement> achievementsList = AchievementsControllerImpl.this.retrieveAchievementsFromSharedPreferences();
                for (Achievement achievement : achievementsList) {
                    achievement.unlocked = true;
                }
                Gson gson = new Gson();
                String serializedList = gson.toJson(achievementsList);
                SharedPreferences.Editor editor = AchievementsControllerImpl.this.sharedPreferences.edit();
                editor.putString("achievementsList", serializedList);
                editor.commit();
                singleSubscriber.onSuccess(null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<Achievement> retrieveAchievementsFromSharedPreferences() {
        String initialAchievementsJsonString = FileReader.readFielAsString(this.assetManager, "achievements/initial_list.json");
        String serializedAchievements = this.sharedPreferences.getString("achievementsList", initialAchievementsJsonString);
        Gson gson = new Gson();
        return (ArrayList) gson.fromJson(serializedAchievements, new TypeToken<ArrayList<Achievement>>() { // from class: com.bq.zowi.controllers.AchievementsControllerImpl.5
        }.getType());
    }

    private void bootstrapAchievements() {
        int currentVersion = this.sharedPreferences.getInt("achievementsListVersion", -1);
        String currentSerializedAchievements = this.sharedPreferences.getString("achievementsList", null);
        if (currentVersion == -1) {
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putInt("achievementsListVersion", 2);
            editor.commit();
        }
        if (currentSerializedAchievements == null) {
            resetAchievementsList();
        }
        if (currentSerializedAchievements != null && currentVersion < 2) {
            Gson gson = new Gson();
            ArrayList<Achievement> currentAchievementsList = (ArrayList) gson.fromJson(currentSerializedAchievements, new TypeToken<ArrayList<Achievement>>() { // from class: com.bq.zowi.controllers.AchievementsControllerImpl.6
            }.getType());
            ArrayList<Achievement> newAchievementsList = (ArrayList) gson.fromJson(FileReader.readFielAsString(this.assetManager, "achievements/initial_list.json"), new TypeToken<ArrayList<Achievement>>() { // from class: com.bq.zowi.controllers.AchievementsControllerImpl.7
            }.getType());
            ArrayList<Achievement> updateAchievementsList = new ArrayList<>();
            for (Achievement a : currentAchievementsList) {
                updateAchievementsList.add(a);
            }
            for (Achievement newAchievement : newAchievementsList) {
                boolean exist = false;
                for (Achievement currentAchievement : currentAchievementsList) {
                    if (newAchievement.id.equals(currentAchievement.id)) {
                        exist = true;
                        if (newAchievement.unlocked) {
                            currentAchievement.unlocked = true;
                        }
                    }
                }
                if (!exist) {
                    updateAchievementsList.add(newAchievement);
                }
            }
            String updatedSerializedAchievements = gson.toJson(updateAchievementsList);
            SharedPreferences.Editor editor2 = this.sharedPreferences.edit();
            editor2.putString("achievementsList", updatedSerializedAchievements);
            editor2.commit();
        }
    }
}
