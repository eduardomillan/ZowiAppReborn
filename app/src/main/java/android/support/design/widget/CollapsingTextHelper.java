package android.support.design.widget;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.R;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private static final Paint DEBUG_DRAW_PAINT;
    private static final boolean USE_SCALING_TEXTURE;
    private final Rect mCollapsedBounds;
    private int mCollapsedTextColor;
    private float mCollapsedTextSize;
    private float mCollapsedTop;
    private float mCurrentLeft;
    private float mCurrentRight;
    private float mCurrentTextSize;
    private float mCurrentTop;
    private final Rect mExpandedBounds;
    private float mExpandedFraction;
    private int mExpandedTextColor;
    private float mExpandedTextSize;
    private Bitmap mExpandedTitleTexture;
    private float mExpandedTop;
    private Interpolator mPositionInterpolator;
    private float mScale;
    private CharSequence mText;
    private Interpolator mTextSizeInterpolator;
    private CharSequence mTextToDraw;
    private float mTextWidth;
    private float mTextureAscent;
    private float mTextureDescent;
    private Paint mTexturePaint;
    private boolean mUseTexture;
    private final View mView;
    private int mExpandedTextVerticalGravity = 16;
    private int mCollapsedTextVerticalGravity = 16;
    private final TextPaint mTextPaint = new TextPaint();

    static {
        USE_SCALING_TEXTURE = Build.VERSION.SDK_INT < 18;
        DEBUG_DRAW_PAINT = null;
        if (DEBUG_DRAW_PAINT != null) {
            DEBUG_DRAW_PAINT.setAntiAlias(true);
            DEBUG_DRAW_PAINT.setColor(-65281);
        }
    }

    public CollapsingTextHelper(View view) {
        this.mView = view;
        this.mTextPaint.setAntiAlias(true);
        this.mCollapsedBounds = new Rect();
        this.mExpandedBounds = new Rect();
    }

    void setTextSizeInterpolator(Interpolator interpolator) {
        this.mTextSizeInterpolator = interpolator;
        recalculate();
    }

    void setPositionInterpolator(Interpolator interpolator) {
        this.mPositionInterpolator = interpolator;
        recalculate();
    }

    void setExpandedTextSize(float textSize) {
        if (this.mExpandedTextSize != textSize) {
            this.mExpandedTextSize = textSize;
            recalculate();
        }
    }

    void setCollapsedTextSize(float textSize) {
        if (this.mCollapsedTextSize != textSize) {
            this.mCollapsedTextSize = textSize;
            recalculate();
        }
    }

    void setCollapsedTextColor(int textColor) {
        if (this.mCollapsedTextColor != textColor) {
            this.mCollapsedTextColor = textColor;
            recalculate();
        }
    }

    void setExpandedTextColor(int textColor) {
        if (this.mExpandedTextColor != textColor) {
            this.mExpandedTextColor = textColor;
            recalculate();
        }
    }

    void setExpandedBounds(int left, int top, int right, int bottom) {
        this.mExpandedBounds.set(left, top, right, bottom);
        recalculate();
    }

    void setCollapsedBounds(int left, int top, int right, int bottom) {
        this.mCollapsedBounds.set(left, top, right, bottom);
        recalculate();
    }

    void setExpandedTextVerticalGravity(int gravity) {
        int gravity2 = gravity & 112;
        if (this.mExpandedTextVerticalGravity != gravity2) {
            this.mExpandedTextVerticalGravity = gravity2;
            recalculate();
        }
    }

    void setCollapsedTextVerticalGravity(int gravity) {
        int gravity2 = gravity & 112;
        if (this.mCollapsedTextVerticalGravity != gravity2) {
            this.mCollapsedTextVerticalGravity = gravity2;
            recalculate();
        }
    }

    void setCollapsedTextAppearance(int resId) {
        TypedArray a = this.mView.getContext().obtainStyledAttributes(resId, R.styleable.TextAppearance);
        if (a.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mCollapsedTextColor = a.getColor(R.styleable.TextAppearance_android_textColor, 0);
        }
        if (a.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mCollapsedTextSize = a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        }
        a.recycle();
        recalculate();
    }

    void setExpandedTextAppearance(int resId) {
        TypedArray a = this.mView.getContext().obtainStyledAttributes(resId, R.styleable.TextAppearance);
        if (a.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mExpandedTextColor = a.getColor(R.styleable.TextAppearance_android_textColor, 0);
        }
        if (a.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mExpandedTextSize = a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        }
        a.recycle();
        recalculate();
    }

    void setExpansionFraction(float fraction) {
        float fraction2 = MathUtils.constrain(fraction, 0.0f, 1.0f);
        if (fraction2 != this.mExpandedFraction) {
            this.mExpandedFraction = fraction2;
            calculateOffsets();
        }
    }

    float getExpansionFraction() {
        return this.mExpandedFraction;
    }

    float getCollapsedTextSize() {
        return this.mCollapsedTextSize;
    }

    float getExpandedTextSize() {
        return this.mExpandedTextSize;
    }

    private void calculateOffsets() {
        float fraction = this.mExpandedFraction;
        this.mCurrentLeft = interpolate(this.mExpandedBounds.left, this.mCollapsedBounds.left, fraction, this.mPositionInterpolator);
        this.mCurrentTop = interpolate(this.mExpandedTop, this.mCollapsedTop, fraction, this.mPositionInterpolator);
        this.mCurrentRight = interpolate(this.mExpandedBounds.right, this.mCollapsedBounds.right, fraction, this.mPositionInterpolator);
        setInterpolatedTextSize(interpolate(this.mExpandedTextSize, this.mCollapsedTextSize, fraction, this.mTextSizeInterpolator));
        if (this.mCollapsedTextColor != this.mExpandedTextColor) {
            this.mTextPaint.setColor(blendColors(this.mExpandedTextColor, this.mCollapsedTextColor, fraction));
        } else {
            this.mTextPaint.setColor(this.mCollapsedTextColor);
        }
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    private void calculateBaselines() {
        this.mTextPaint.setTextSize(this.mCollapsedTextSize);
        switch (this.mCollapsedTextVerticalGravity) {
            case 48:
                this.mCollapsedTop = this.mCollapsedBounds.top - this.mTextPaint.ascent();
                break;
            case 80:
                this.mCollapsedTop = this.mCollapsedBounds.bottom;
                break;
            default:
                float textHeight = this.mTextPaint.descent() - this.mTextPaint.ascent();
                float textOffset = (textHeight / 2.0f) - this.mTextPaint.descent();
                this.mCollapsedTop = this.mCollapsedBounds.centerY() + textOffset;
                break;
        }
        this.mTextPaint.setTextSize(this.mExpandedTextSize);
        switch (this.mExpandedTextVerticalGravity) {
            case 48:
                this.mExpandedTop = this.mExpandedBounds.top - this.mTextPaint.ascent();
                break;
            case 80:
                this.mExpandedTop = this.mExpandedBounds.bottom;
                break;
            default:
                float textHeight2 = this.mTextPaint.descent() - this.mTextPaint.ascent();
                float textOffset2 = (textHeight2 / 2.0f) - this.mTextPaint.descent();
                this.mExpandedTop = this.mExpandedBounds.centerY() + textOffset2;
                break;
        }
        this.mTextureAscent = this.mTextPaint.ascent();
        this.mTextureDescent = this.mTextPaint.descent();
        clearTexture();
    }

    public void draw(Canvas canvas) {
        float ascent;
        int saveCount = canvas.save();
        if (this.mTextToDraw != null) {
            boolean isRtl = ViewCompat.getLayoutDirection(this.mView) == 1;
            float x = isRtl ? this.mCurrentRight : this.mCurrentLeft;
            float y = this.mCurrentTop;
            boolean drawTexture = this.mUseTexture && this.mExpandedTitleTexture != null;
            this.mTextPaint.setTextSize(this.mCurrentTextSize);
            if (drawTexture) {
                ascent = this.mTextureAscent * this.mScale;
                float f = this.mTextureDescent * this.mScale;
            } else {
                ascent = this.mTextPaint.ascent() * this.mScale;
                float fDescent = this.mTextPaint.descent() * this.mScale;
            }
            if (drawTexture) {
                y += ascent;
            }
            if (this.mScale != 1.0f) {
                canvas.scale(this.mScale, this.mScale, x, y);
            }
            if (isRtl) {
                x -= this.mTextWidth;
            }
            if (drawTexture) {
                canvas.drawBitmap(this.mExpandedTitleTexture, x, y, this.mTexturePaint);
            } else {
                canvas.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), x, y, this.mTextPaint);
            }
        }
        canvas.restoreToCount(saveCount);
    }

    private void setInterpolatedTextSize(float textSize) {
        float availableWidth;
        float newTextSize;
        if (this.mText != null) {
            boolean updateDrawText = false;
            if (isClose(textSize, this.mCollapsedTextSize)) {
                availableWidth = this.mCollapsedBounds.width();
                newTextSize = this.mCollapsedTextSize;
                this.mScale = 1.0f;
            } else {
                availableWidth = this.mExpandedBounds.width();
                newTextSize = this.mExpandedTextSize;
                if (isClose(textSize, this.mExpandedTextSize)) {
                    this.mScale = 1.0f;
                } else {
                    this.mScale = textSize / this.mExpandedTextSize;
                }
            }
            if (availableWidth > 0.0f) {
                updateDrawText = this.mCurrentTextSize != newTextSize;
                this.mCurrentTextSize = newTextSize;
            }
            if (this.mTextToDraw == null || updateDrawText) {
                this.mTextPaint.setTextSize(this.mCurrentTextSize);
                CharSequence title = TextUtils.ellipsize(this.mText, this.mTextPaint, availableWidth, TextUtils.TruncateAt.END);
                if (this.mTextToDraw == null || !this.mTextToDraw.equals(title)) {
                    this.mTextToDraw = title;
                }
                this.mTextWidth = this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length());
            }
            this.mUseTexture = USE_SCALING_TEXTURE && this.mScale != 1.0f;
            if (this.mUseTexture) {
                ensureExpandedTexture();
            }
            ViewCompat.postInvalidateOnAnimation(this.mView);
        }
    }

    private void ensureExpandedTexture() {
        if (this.mExpandedTitleTexture == null && !this.mExpandedBounds.isEmpty() && !TextUtils.isEmpty(this.mTextToDraw)) {
            this.mTextPaint.setTextSize(this.mExpandedTextSize);
            this.mTextPaint.setColor(this.mExpandedTextColor);
            int w = Math.round(this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()));
            int h = Math.round(this.mTextPaint.descent() - this.mTextPaint.ascent());
            this.mTextWidth = w;
            if (w > 0 || h > 0) {
                this.mExpandedTitleTexture = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(this.mExpandedTitleTexture);
                c.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), 0.0f, h - this.mTextPaint.descent(), this.mTextPaint);
                if (this.mTexturePaint == null) {
                    this.mTexturePaint = new Paint();
                    this.mTexturePaint.setAntiAlias(true);
                    this.mTexturePaint.setFilterBitmap(true);
                }
            }
        }
    }

    private void recalculate() {
        if (ViewCompat.isLaidOut(this.mView)) {
            calculateBaselines();
            calculateOffsets();
        }
    }

    void setText(CharSequence text) {
        if (text == null || !text.equals(this.mText)) {
            this.mText = text;
            clearTexture();
            recalculate();
        }
    }

    CharSequence getText() {
        return this.mText;
    }

    private void clearTexture() {
        if (this.mExpandedTitleTexture != null) {
            this.mExpandedTitleTexture.recycle();
            this.mExpandedTitleTexture = null;
        }
    }

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        recalculate();
    }

    private static boolean isClose(float value, float targetValue) {
        return Math.abs(value - targetValue) < 0.001f;
    }

    int getExpandedTextColor() {
        return this.mExpandedTextColor;
    }

    int getCollapsedTextColor() {
        return this.mCollapsedTextColor;
    }

    private static int blendColors(int color1, int color2, float ratio) {
        float inverseRatio = 1.0f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

    private static float interpolate(float startValue, float endValue, float fraction, Interpolator interpolator) {
        if (interpolator != null) {
            fraction = interpolator.getInterpolation(fraction);
        }
        return AnimationUtils.lerp(startValue, endValue, fraction);
    }
}
