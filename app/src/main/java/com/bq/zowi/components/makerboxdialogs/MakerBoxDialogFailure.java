package com.bq.zowi.components.makerboxdialogs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bq.zowi.R;

/* JADX INFO: loaded from: classes.dex */
public class MakerBoxDialogFailure extends MakerBoxDialog {
    private ImageView bgImageView;
    private Button continueButton;
    private TextView failureContentText;
    private TextView failureDescriptionText;
    private TextView failureMainText;

    public MakerBoxDialogFailure(Context context) {
        super(context);
        init(context);
    }

    public MakerBoxDialogFailure(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MakerBoxDialogFailure(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(resolveLayoutId("component_makerbox_failure", R.layout.component_makerbox_failure), this);
            this.bgImageView = (ImageView) findResolvedView("makerbox_dialog_points_earned_disable_ranking_bg_image", R.id.makerbox_dialog_points_earned_disable_ranking_bg_image);
            if (this.bgImageView != null) {
                Animation rotation = AnimationUtils.loadAnimation(getContext(), resolveAnimId("swing_infinite", R.anim.swing_infinite));
                this.bgImageView.startAnimation(rotation);
            }
            this.failureDescriptionText = (TextView) findResolvedView("makerbox_dialog_failure_description_text", R.id.makerbox_dialog_failure_description_text);
            this.failureMainText = (TextView) findResolvedView("makerbox_dialog_failure_main_text", R.id.makerbox_dialog_failure_main_text);
            this.failureContentText = (TextView) findResolvedView("makerbox_dialog_failure_content_text", R.id.makerbox_dialog_failure_content_text);
            this.continueButton = (Button) findResolvedView("makerbox_dialog_failure_continue_button", R.id.makerbox_dialog_failure_continue_button);
        }
    }

    public void setFailureDescriptionText(String descriptionText) {
        if (this.failureDescriptionText != null) {
            this.failureDescriptionText.setText(descriptionText);
        }
    }

    public void setFailureMainText(String mainText) {
        if (this.failureMainText != null) {
            this.failureMainText.setText(mainText);
        }
    }

    public void setFailureContentText(String contentText) {
        if (this.failureContentText != null) {
            this.failureContentText.setText(contentText);
        }
    }

    public void setOnContinueButtonClickedListener(View.OnClickListener onClickListener) {
        if (this.continueButton != null) {
            this.continueButton.setOnClickListener(onClickListener);
        }
    }
}
