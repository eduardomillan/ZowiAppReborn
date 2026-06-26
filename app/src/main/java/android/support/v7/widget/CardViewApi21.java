package android.support.v7.widget;

import android.content.Context;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class CardViewApi21 implements CardViewImpl {
    CardViewApi21() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v7.widget.CardViewImpl
    public void initialize(CardViewDelegate cardViewDelegate, Context context, int backgroundColor, float radius, float elevation, float maxElevation) {
        RoundRectDrawable backgroundDrawable = new RoundRectDrawable(backgroundColor, radius);
        cardViewDelegate.setBackgroundDrawable(backgroundDrawable);
        View view = (View) cardViewDelegate;
        view.setClipToOutline(true);
        view.setElevation(elevation);
        setMaxElevation(cardViewDelegate, maxElevation);
    }

    @Override // android.support.v7.widget.CardViewImpl
    public void setRadius(CardViewDelegate cardView, float radius) {
        ((RoundRectDrawable) cardView.getBackground()).setRadius(radius);
    }

    @Override // android.support.v7.widget.CardViewImpl
    public void initStatic() {
    }

    @Override // android.support.v7.widget.CardViewImpl
    public void setMaxElevation(CardViewDelegate cardView, float maxElevation) {
        ((RoundRectDrawable) cardView.getBackground()).setPadding(maxElevation, cardView.getUseCompatPadding(), cardView.getPreventCornerOverlap());
        updatePadding(cardView);
    }

    @Override // android.support.v7.widget.CardViewImpl
    public float getMaxElevation(CardViewDelegate cardView) {
        return ((RoundRectDrawable) cardView.getBackground()).getPadding();
    }

    @Override // android.support.v7.widget.CardViewImpl
    public float getMinWidth(CardViewDelegate cardView) {
        return getRadius(cardView) * 2.0f;
    }

    @Override // android.support.v7.widget.CardViewImpl
    public float getMinHeight(CardViewDelegate cardView) {
        return getRadius(cardView) * 2.0f;
    }

    @Override // android.support.v7.widget.CardViewImpl
    public float getRadius(CardViewDelegate cardView) {
        return ((RoundRectDrawable) cardView.getBackground()).getRadius();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v7.widget.CardViewImpl
    public void setElevation(CardViewDelegate cardViewDelegate, float elevation) {
        ((View) cardViewDelegate).setElevation(elevation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v7.widget.CardViewImpl
    public float getElevation(CardViewDelegate cardViewDelegate) {
        return ((View) cardViewDelegate).getElevation();
    }

    @Override // android.support.v7.widget.CardViewImpl
    public void updatePadding(CardViewDelegate cardView) {
        if (!cardView.getUseCompatPadding()) {
            cardView.setShadowPadding(0, 0, 0, 0);
            return;
        }
        float elevation = getMaxElevation(cardView);
        float radius = getRadius(cardView);
        int hPadding = (int) Math.ceil(RoundRectDrawableWithShadow.calculateHorizontalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
        int vPadding = (int) Math.ceil(RoundRectDrawableWithShadow.calculateVerticalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
        cardView.setShadowPadding(hPadding, vPadding, hPadding, vPadding);
    }

    @Override // android.support.v7.widget.CardViewImpl
    public void onCompatPaddingChanged(CardViewDelegate cardView) {
        setMaxElevation(cardView, getMaxElevation(cardView));
    }

    @Override // android.support.v7.widget.CardViewImpl
    public void onPreventCornerOverlapChanged(CardViewDelegate cardView) {
        setMaxElevation(cardView, getMaxElevation(cardView));
    }

    @Override // android.support.v7.widget.CardViewImpl
    public void setBackgroundColor(CardViewDelegate cardView, int color) {
        ((RoundRectDrawable) cardView.getBackground()).setColor(color);
    }
}
