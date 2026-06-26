package com.bq.zowi.views.interactive.timeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.bq.zowi.R;
import com.bq.zowi.models.commands.GridCommand;

/* JADX INFO: loaded from: classes.dex */
public class GridCommandResourceResolver {
    public static Drawable resolveDrawable(Context context, GridCommand gridCommand) {
        int resolvedDrawableResourceId;
        switch (gridCommand.getCommand().getAction()) {
            case WALK:
                resolvedDrawableResourceId = R.drawable.walk_selector;
                break;
            case TURN:
                resolvedDrawableResourceId = R.drawable.turn_selector;
                break;
            case UPDOWN:
                resolvedDrawableResourceId = R.drawable.updown_selector;
                break;
            case MOONWALKER:
                resolvedDrawableResourceId = R.drawable.moonwalker_selector;
                break;
            case SWING:
                resolvedDrawableResourceId = R.drawable.swing_selector;
                break;
            case CRUSAITO:
                resolvedDrawableResourceId = R.drawable.crusaito_selector;
                break;
            case FLAPPING:
                resolvedDrawableResourceId = R.drawable.flapping_selector;
                break;
            case TIP_TOE:
                resolvedDrawableResourceId = R.drawable.tip_toe_selector;
                break;
            case BEND:
                resolvedDrawableResourceId = R.drawable.bend_selector;
                break;
            case SHAKE_LEG:
                resolvedDrawableResourceId = R.drawable.shake_leg_selector;
                break;
            case JITTER:
                resolvedDrawableResourceId = R.drawable.jitter_selector;
                break;
            case ASCENDING_TURN:
                resolvedDrawableResourceId = R.drawable.ascending_turn_selector;
                break;
            case MOUTH_SMILE:
                resolvedDrawableResourceId = R.drawable.smile_button_selector;
                break;
            case MOUTH_SAD:
                resolvedDrawableResourceId = R.drawable.sad_button_selector;
                break;
            case MOUTH_CONFUSED:
                resolvedDrawableResourceId = R.drawable.confused_button_selector;
                break;
            case MOUTH_BIG_SURPRISE:
                resolvedDrawableResourceId = R.drawable.big_surprise_button_selector;
                break;
            case MOUTH_SMALL_SURPRISE:
                resolvedDrawableResourceId = R.drawable.small_surprise_button_selector;
                break;
            case MOUTH_HAPPY_OPEN:
                resolvedDrawableResourceId = R.drawable.happy_open_button_selector;
                break;
            case MOUTH_SAD_OPEN:
                resolvedDrawableResourceId = R.drawable.sad_open_button_selector;
                break;
            case MOUTH_SAD_CLOSED:
                resolvedDrawableResourceId = R.drawable.sad_closed_button_selector;
                break;
            case MOUTH_HEART:
                resolvedDrawableResourceId = R.drawable.heart_button_selector;
                break;
            case MOUTH_THUNDER:
                resolvedDrawableResourceId = R.drawable.thunder_button_selector;
                break;
            case MOUTH_X:
                resolvedDrawableResourceId = R.drawable.x_mouth_button_selector;
                break;
            case MOUTH_INTERROGATION:
                resolvedDrawableResourceId = R.drawable.interrogation_button_selector;
                break;
            case MOUTH_TONGUE_OUT:
                resolvedDrawableResourceId = R.drawable.tongue_out_button_selector;
                break;
            case MOUTH_DIAGONAL:
                resolvedDrawableResourceId = R.drawable.diagonal_button_selector;
                break;
            case MOUTH_ANGRY:
                resolvedDrawableResourceId = R.drawable.angry_button_selector;
                break;
            case MOUTH_CULITO:
                resolvedDrawableResourceId = R.drawable.culito_button_selector;
                break;
            case MOUTH_OK:
                resolvedDrawableResourceId = R.drawable.ok_mouth_button_selector;
                break;
            case MOUTH_LINE:
                resolvedDrawableResourceId = R.drawable.line_mouth_button_selector;
                break;
            case MOUTH_VAMP1:
                resolvedDrawableResourceId = R.drawable.vamp1_button_selector;
                break;
            case MOUTH_VAMP2:
                resolvedDrawableResourceId = R.drawable.vamp2_button_selector;
                break;
            case HAPPY:
                resolvedDrawableResourceId = R.drawable.animation_happy_button_selector;
                break;
            case SUPER_HAPPY:
                resolvedDrawableResourceId = R.drawable.animation_super_happy_button_selector;
                break;
            case SAD:
                resolvedDrawableResourceId = R.drawable.animation_sad_button_selector;
                break;
            case SLEEPY:
                resolvedDrawableResourceId = R.drawable.animation_sleepy_button_selector;
                break;
            case FART:
                resolvedDrawableResourceId = R.drawable.animation_fart_button_selector;
                break;
            case CONFUSED:
                resolvedDrawableResourceId = R.drawable.animation_confused_button_selector;
                break;
            case IN_LOVE:
                resolvedDrawableResourceId = R.drawable.animation_in_love_button_selector;
                break;
            case ANGRY:
                resolvedDrawableResourceId = R.drawable.animation_angry_button_selector;
                break;
            case ANXIOUS:
                resolvedDrawableResourceId = R.drawable.animation_anxious_button_selector;
                break;
            case MAGIC:
                resolvedDrawableResourceId = R.drawable.animation_magic_button_selector;
                break;
            case WAVE:
                resolvedDrawableResourceId = R.drawable.animation_wave_button_selector;
                break;
            default:
                resolvedDrawableResourceId = R.drawable.interrogation_button_selector;
                break;
        }
        Drawable resolvedDrawable = ContextCompat.getDrawable(context, resolvedDrawableResourceId);
        return resolvedDrawable;
    }
}
