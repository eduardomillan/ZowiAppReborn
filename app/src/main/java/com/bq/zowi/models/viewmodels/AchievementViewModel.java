package com.bq.zowi.models.viewmodels;

/* JADX INFO: loaded from: classes.dex */
public class AchievementViewModel {
    private String badgeImageResourceId;
    private String descriptionResourceId;
    public String id;
    private String titleResourceId;
    private String unlockConditionResouceId;
    public boolean unlocked;
    private final String TITLE_RESOURCE_NAMING_CONVENTION = "achievement_title_";
    private final String DESCRIPTION_RESOURCE_NAMING_CONVENTION = "achievement_description_";
    private final String UNLOCK_CONDITION_RESOURCE_NAMING_CONVENTION = "achievement_unlock_condition_";
    private final String BADGE_IMAGE_RESOURCE_NAMING_CONVENTION = "_badge";

    public AchievementViewModel(String id, boolean unlocked) {
        this.id = id;
        this.unlocked = unlocked;
    }

    public String getTitleResourceId() {
        return "achievement_title_" + this.id;
    }

    public String getDescriptionResourceId() {
        return "achievement_description_" + this.id;
    }

    public String getUnlockConditionResouceId() {
        return "achievement_unlock_condition_" + this.id;
    }

    public String getBadgeImageResourceId() {
        return this.id + "_badge";
    }
}
