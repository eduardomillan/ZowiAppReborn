package android.support.design.widget;

import android.R;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
abstract class FloatingActionButtonImpl {
    final ShadowViewDelegate mShadowViewDelegate;
    final View mView;
    static final int[] PRESSED_ENABLED_STATE_SET = {R.attr.state_pressed, R.attr.state_enabled};
    static final int[] FOCUSED_ENABLED_STATE_SET = {R.attr.state_focused, R.attr.state_enabled};
    static final int[] EMPTY_STATE_SET = new int[0];

    abstract void jumpDrawableToCurrentState();

    abstract void onDrawableStateChanged(int[] iArr);

    abstract void setBackgroundDrawable(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode, int i, int i2);

    abstract void setBackgroundTintList(ColorStateList colorStateList);

    abstract void setBackgroundTintMode(PorterDuff.Mode mode);

    abstract void setElevation(float f);

    abstract void setPressedTranslationZ(float f);

    abstract void setRippleColor(int i);

    FloatingActionButtonImpl(View view, ShadowViewDelegate shadowViewDelegate) {
        this.mView = view;
        this.mShadowViewDelegate = shadowViewDelegate;
    }

    Drawable createBorderDrawable(int borderWidth, ColorStateList backgroundTint) {
        Resources resources = this.mView.getResources();
        CircularBorderDrawable borderDrawable = newCircularDrawable();
        borderDrawable.setGradientColors(resources.getColor(android.support.design.R.color.fab_stroke_top_outer_color), resources.getColor(android.support.design.R.color.fab_stroke_top_inner_color), resources.getColor(android.support.design.R.color.fab_stroke_end_inner_color), resources.getColor(android.support.design.R.color.fab_stroke_end_outer_color));
        borderDrawable.setBorderWidth(borderWidth);
        Drawable d = DrawableCompat.wrap(borderDrawable);
        DrawableCompat.setTintList(d, backgroundTint);
        DrawableCompat.setTintMode(d, PorterDuff.Mode.DST_OVER);
        return d;
    }

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }
}
