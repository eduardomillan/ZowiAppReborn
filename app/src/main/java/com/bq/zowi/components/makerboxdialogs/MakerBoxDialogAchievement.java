package com.bq.zowi.components.makerboxdialogs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bq.zowi.R;

/* JADX INFO: loaded from: classes.dex */
public class MakerBoxDialogAchievement extends MakerBoxDialog {
    private ImageView achievementContentImageView;
    private TextView achievementDescription;
    private TextView achievementTitle;
    private Button continueButton;

    public MakerBoxDialogAchievement(Context context) {
        super(context);
        init(context);
    }

    public MakerBoxDialogAchievement(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MakerBoxDialogAchievement(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(R.layout.component_makerbox_achievement, this);
            this.achievementContentImageView = (ImageView) findViewById(R.id.makerbox_dialog_achievement_content_imageview);
            this.achievementTitle = (TextView) findViewById(R.id.makerbox_dialog_achievement_title_text);
            this.achievementDescription = (TextView) findViewById(R.id.makerbox_dialog_achievement_description_text);
            this.continueButton = (Button) findViewById(R.id.makerbox_dialog_achievement_continue_button);
        }
    }

    public void setAchievementTitle(String title) {
        this.achievementTitle.setText(title);
    }

    public void setAchievementDescription(String description) {
        this.achievementDescription.setText(description);
    }

    public void setAchievementDrawable(Drawable achievementDrawable) {
        this.achievementContentImageView.setImageDrawable(achievementDrawable);
        setCenterTopImageViewDrawable(achievementDrawable);
    }

    public void setOnContinueButtonClickedListener(View.OnClickListener onClickListener) {
        this.continueButton.setOnClickListener(onClickListener);
    }
}
