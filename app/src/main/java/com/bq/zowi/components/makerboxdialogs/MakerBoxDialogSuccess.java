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
public class MakerBoxDialogSuccess extends MakerBoxDialog {
    protected ImageView bgImageView;
    protected Button continueButton;
    protected TextView successDescriptionText;
    protected TextView successMainText;

    public MakerBoxDialogSuccess(Context context) {
        super(context);
        init(context);
    }

    public MakerBoxDialogSuccess(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MakerBoxDialogSuccess(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(R.layout.component_makerbox_success, this);
            this.bgImageView = (ImageView) findViewById(R.id.makerbox_dialog_success_bg_image);
            Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_infinite);
            this.bgImageView.startAnimation(rotation);
            this.successDescriptionText = (TextView) findViewById(R.id.makerbox_success_description);
            this.successMainText = (TextView) findViewById(R.id.makerbox_success_main_text);
            this.continueButton = (Button) findViewById(R.id.makerbox_dialog_success_continue_button);
        }
    }

    public void setSuccessDescriptionText(String description) {
        this.successDescriptionText.setText(description);
    }

    public void setSuccessMainText(String description) {
        this.successMainText.setText(description);
    }

    public void setOnContinueButtonClickedListener(View.OnClickListener onClickListener) {
        this.continueButton.setOnClickListener(onClickListener);
    }
}
