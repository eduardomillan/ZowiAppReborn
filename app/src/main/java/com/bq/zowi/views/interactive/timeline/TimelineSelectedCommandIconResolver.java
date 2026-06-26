package com.bq.zowi.views.interactive.timeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.bq.zowi.R;
import com.bq.zowi.models.commands.TimelineCommand;

/* JADX INFO: loaded from: classes.dex */
public class TimelineSelectedCommandIconResolver {
    public static Drawable resolveIcon(Context context, TimelineCommand timelineCommand) {
        int resolvedDrawableResourceId;
        switch (timelineCommand.getCommand().getAction()) {
            case WALK:
                resolvedDrawableResourceId = R.drawable.timeline_walk;
                break;
            case ASCENDING_TURN:
                resolvedDrawableResourceId = R.drawable.timeline_ascending_turn;
                break;
            case UPDOWN:
                resolvedDrawableResourceId = R.drawable.timeline_updown;
                break;
            case BEND:
                resolvedDrawableResourceId = R.drawable.timeline_bend;
                break;
            case SHAKE_LEG:
                resolvedDrawableResourceId = R.drawable.timeline_shake_leg;
                break;
            case JITTER:
                resolvedDrawableResourceId = R.drawable.timeline_jitter;
                break;
            case SWING:
                resolvedDrawableResourceId = R.drawable.timeline_swing;
                break;
            case FLAPPING:
                resolvedDrawableResourceId = R.drawable.timeline_flapping;
                break;
            case CRUSAITO:
                resolvedDrawableResourceId = R.drawable.timeline_crusaito;
                break;
            case FALL:
                resolvedDrawableResourceId = R.drawable.timeline_fall;
                break;
            case MOONWALKER:
                resolvedDrawableResourceId = R.drawable.timeline_moonwalk;
                break;
            case TIP_TOE:
                resolvedDrawableResourceId = R.drawable.timeline_tip_toe;
                break;
            case TURN:
                resolvedDrawableResourceId = R.drawable.timeline_turn;
                break;
            case HAPPY:
                resolvedDrawableResourceId = R.drawable.animation_happy_icon;
                break;
            case SUPER_HAPPY:
                resolvedDrawableResourceId = R.drawable.animation_super_happy_icon;
                break;
            case SAD:
                resolvedDrawableResourceId = R.drawable.animation_sad_icon;
                break;
            case SLEEPY:
                resolvedDrawableResourceId = R.drawable.animation_sleepy_icon;
                break;
            case FART:
                resolvedDrawableResourceId = R.drawable.animation_fart_icon;
                break;
            case CONFUSED:
                resolvedDrawableResourceId = R.drawable.animation_confused_icon;
                break;
            case IN_LOVE:
                resolvedDrawableResourceId = R.drawable.animation_in_love_icon;
                break;
            case ANGRY:
                resolvedDrawableResourceId = R.drawable.animation_angry_icon;
                break;
            case ANXIOUS:
                resolvedDrawableResourceId = R.drawable.animation_anxious_icon;
                break;
            case MAGIC:
                resolvedDrawableResourceId = R.drawable.animation_magic_icon;
                break;
            case WAVE:
                resolvedDrawableResourceId = R.drawable.animation_wave_icon;
                break;
            case MOUTH_SMILE:
                resolvedDrawableResourceId = R.drawable.smile_icon;
                break;
            case MOUTH_SAD:
                resolvedDrawableResourceId = R.drawable.sad_icon;
                break;
            case MOUTH_CONFUSED:
                resolvedDrawableResourceId = R.drawable.confused_icon;
                break;
            case MOUTH_BIG_SURPRISE:
                resolvedDrawableResourceId = R.drawable.big_surprise_icon;
                break;
            case MOUTH_SMALL_SURPRISE:
                resolvedDrawableResourceId = R.drawable.small_surprise_icon;
                break;
            case MOUTH_HAPPY_OPEN:
                resolvedDrawableResourceId = R.drawable.happy_open_icon;
                break;
            case MOUTH_SAD_OPEN:
                resolvedDrawableResourceId = R.drawable.sad_open_icon;
                break;
            case MOUTH_SAD_CLOSED:
                resolvedDrawableResourceId = R.drawable.sad_closed_icon;
                break;
            case MOUTH_HEART:
                resolvedDrawableResourceId = R.drawable.heart_icon;
                break;
            case MOUTH_THUNDER:
                resolvedDrawableResourceId = R.drawable.thunder_icon;
                break;
            case MOUTH_X:
                resolvedDrawableResourceId = R.drawable.x_mouth_icon;
                break;
            case MOUTH_INTERROGATION:
                resolvedDrawableResourceId = R.drawable.interrogation_icon;
                break;
            case MOUTH_TONGUE_OUT:
                resolvedDrawableResourceId = R.drawable.tongue_out_icon;
                break;
            case MOUTH_DIAGONAL:
                resolvedDrawableResourceId = R.drawable.diagonal_icon;
                break;
            case MOUTH_ANGRY:
                resolvedDrawableResourceId = R.drawable.angry_icon;
                break;
            case MOUTH_CULITO:
                resolvedDrawableResourceId = R.drawable.culito_icon;
                break;
            case MOUTH_OK:
                resolvedDrawableResourceId = R.drawable.ok_mouth_icon;
                break;
            case MOUTH_LINE:
                resolvedDrawableResourceId = R.drawable.line_mouth_icon;
                break;
            case MOUTH_VAMP1:
                resolvedDrawableResourceId = R.drawable.vamp1_icon;
                break;
            case MOUTH_VAMP2:
                resolvedDrawableResourceId = R.drawable.vamp2_icon;
                break;
            default:
                resolvedDrawableResourceId = R.drawable.interrogation_button_selector;
                break;
        }
        Drawable resolvedDrawable = ContextCompat.getDrawable(context, resolvedDrawableResourceId);
        return resolvedDrawable;
    }
}
