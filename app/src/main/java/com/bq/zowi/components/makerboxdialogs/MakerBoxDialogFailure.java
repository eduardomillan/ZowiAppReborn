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
            LayoutInflater.from(context).inflate(R.layout.component_makerbox_failure, this);
            this.bgImageView = (ImageView) findViewById(R.id.makerbox_dialog_points_earned_disable_ranking_bg_image);
            Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.swing_infinite);
            this.bgImageView.startAnimation(rotation);
            this.failureDescriptionText = (TextView) findViewById(R.id.makerbox_dialog_failure_description_text);
            this.failureMainText = (TextView) findViewById(R.id.makerbox_dialog_failure_main_text);
            this.failureContentText = (TextView) findViewById(R.id.makerbox_dialog_failure_content_text);
            this.continueButton = (Button) findViewById(R.id.makerbox_dialog_failure_continue_button);
        }
    }

    public void setFailureDescriptionText(String descriptionText) {
        this.failureDescriptionText.setText(descriptionText);
    }

    public void setFailureMainText(String mainText) {
        this.failureMainText.setText(mainText);
    }

    public void setFailureContentText(String contentText) {
        this.failureContentText.setText(contentText);
    }

    public void setOnContinueButtonClickedListener(View.OnClickListener onClickListener) {
        this.continueButton.setOnClickListener(onClickListener);
    }
}
