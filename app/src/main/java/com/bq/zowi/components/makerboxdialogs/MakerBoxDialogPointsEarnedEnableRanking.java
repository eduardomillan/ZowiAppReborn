package com.bq.zowi.components.makerboxdialogs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bq.zowi.R;

/* JADX INFO: loaded from: classes.dex */
public class MakerBoxDialogPointsEarnedEnableRanking extends MakerBoxDialog {
    protected ImageView bgImageView;
    protected Button continueButton;
    private OnPlayerNameEnteredListener onPlayerNameEnteredListener;
    private EditText playerNameEditText;
    protected TextView successDescriptionText;
    protected TextView successMainText;

    public interface OnPlayerNameEnteredListener {
        void onPlayerNameEntered(String str);
    }

    public MakerBoxDialogPointsEarnedEnableRanking(Context context) {
        super(context);
        init(context);
    }

    public MakerBoxDialogPointsEarnedEnableRanking(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MakerBoxDialogPointsEarnedEnableRanking(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(resolveLayoutId("component_makerbox_points_earned_enable_ranking", R.layout.component_makerbox_points_earned_enable_ranking), this);
            this.bgImageView = (ImageView) findResolvedView("makerbox_dialog_success_bg_image", R.id.makerbox_dialog_success_bg_image);
            if (this.bgImageView != null) {
                Animation rotation = AnimationUtils.loadAnimation(getContext(), resolveAnimId("rotate_infinite", R.anim.rotate_infinite));
                this.bgImageView.startAnimation(rotation);
            }
            this.successMainText = (TextView) findResolvedView("makerbox_success_main_text", R.id.makerbox_success_main_text);
            this.continueButton = (Button) findResolvedView("makerbox_dialog_success_continue_button", R.id.makerbox_dialog_success_continue_button);
            this.playerNameEditText = (EditText) findResolvedView("makerbox_dialog_points_earned_enable_ranking_player_name_edittext", R.id.makerbox_dialog_points_earned_enable_ranking_player_name_edittext);
            if (this.continueButton != null) {
                this.continueButton.setEnabled(false);
            }
            if (this.playerNameEditText != null && this.continueButton != null) {
                this.playerNameEditText.addTextChangedListener(new TextWatcher() { // from class: com.bq.zowi.components.makerboxdialogs.MakerBoxDialogPointsEarnedEnableRanking.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        MakerBoxDialogPointsEarnedEnableRanking.this.continueButton.setEnabled(MakerBoxDialogPointsEarnedEnableRanking.this.playerNameEditText.getText().length() > 0);
                    }
                });
            }
            if (this.continueButton != null) {
                this.continueButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.components.makerboxdialogs.MakerBoxDialogPointsEarnedEnableRanking.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (MakerBoxDialogPointsEarnedEnableRanking.this.onPlayerNameEnteredListener != null && MakerBoxDialogPointsEarnedEnableRanking.this.playerNameEditText != null) {
                            MakerBoxDialogPointsEarnedEnableRanking.this.onPlayerNameEnteredListener.onPlayerNameEntered(MakerBoxDialogPointsEarnedEnableRanking.this.playerNameEditText.getText().toString());
                        }
                    }
                });
            }
        }
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setPointsEarned(int pointsEarned) {
        if (this.successMainText != null) {
            this.successMainText.setText(getContext().getResources().getQuantityString(R.plurals.games_points_earned_text, pointsEarned, Integer.valueOf(pointsEarned)));
        }
    }

    public void setOnPlayerNameEnteredListener(OnPlayerNameEnteredListener onPlayerNameEnteredListener) {
        this.onPlayerNameEnteredListener = onPlayerNameEnteredListener;
    }
}
