package com.bq.zowi.views.interactive.achievements;

import android.content.Context;
import android.content.res.Resources;
import androidx.core.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.components.recyclerview.RecyclerViewHolder;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.utils.ResourceResolver;

/* JADX INFO: loaded from: classes.dex */
public class AchievementRowViewHolder extends RecyclerViewHolder<AchievementViewModel> {
    private ImageView badgeImageView;
    private Context context;
    private TextView descriptionText;
    private TextView titleText;
    private TextView unlockConditionsText;

    public AchievementRowViewHolder(int resourceId, ViewGroup parent, Context context) {
        super(resourceId, parent, context);
        this.context = context;
        this.badgeImageView = (ImageView) this.itemView.findViewById(R.id.achievement_row_badge_image);
        this.titleText = (TextView) this.itemView.findViewById(R.id.achievement_row_title_text);
        this.descriptionText = (TextView) this.itemView.findViewById(R.id.achievement_row_description_text);
        this.unlockConditionsText = (TextView) this.itemView.findViewById(R.id.achievement_row_unlock_condition_text);
    }

    @Override // com.bq.zowi.components.recyclerview.RecyclerViewHolder
    public void bind(AchievementViewModel item) {
        if (item.unlocked) {
            try {
                this.badgeImageView.setImageDrawable(ResourceResolver.getDrawableByResourceId(item.getBadgeImageResourceId(), this.context));
            } catch (Resources.NotFoundException e) {
                this.badgeImageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.achievements_badge));
            }
        } else {
            this.badgeImageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.blocked_achievements_badge));
        }
        this.titleText.setText(ResourceResolver.getStringByResourceId(item.getTitleResourceId(), this.context.getResources(), this.context.getPackageName()));
        this.descriptionText.setText(ResourceResolver.getStringByResourceId(item.getDescriptionResourceId(), this.context.getResources(), this.context.getPackageName()));
        this.unlockConditionsText.setText(ResourceResolver.getStringByResourceId(item.getUnlockConditionResouceId(), this.context.getResources(), this.context.getPackageName()));
        if (getAdapterPosition() % 2 == 0) {
            this.itemView.setBackgroundColor(ResourceResolver.getColorByResourceId("maker_box_odd_row_background", this.context));
        } else {
            this.itemView.setBackgroundColor(ResourceResolver.getColorByResourceId("maker_box_even_row_background", this.context));
        }
    }
}
