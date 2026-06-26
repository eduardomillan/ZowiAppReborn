package com.bq.zowi.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import com.bq.zowi.R;

/* JADX INFO: loaded from: classes.dex */
public class GifView extends View {
    private static final int DEFAULT_MOVIEW_DURATION = 1000;
    private int mCurrentAnimationTime;
    private float mLeft;
    private int mMeasuredMovieHeight;
    private int mMeasuredMovieWidth;
    private Movie mMovie;
    private int mMovieResourceId;
    private long mMovieStart;
    private volatile boolean mPaused;
    private float mScale;
    private float mTop;
    private boolean mVisible;

    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCurrentAnimationTime = 0;
        this.mPaused = false;
        this.mVisible = true;
        setViewAttributes(context, attrs, defStyle);
    }

    @SuppressLint({"NewApi"})
    private void setViewAttributes(Context context, AttributeSet attrs, int defStyle) {
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GifView, defStyle, R.style.Widget_GifView);
        this.mMovieResourceId = array.getResourceId(0, -1);
        this.mPaused = array.getBoolean(1, false);
        array.recycle();
        if (this.mMovieResourceId != -1) {
            this.mMovie = Movie.decodeStream(getResources().openRawResource(this.mMovieResourceId));
        }
    }

    public void setMovieResource(int movieResId) {
        this.mMovieResourceId = movieResId;
        this.mMovie = Movie.decodeStream(getResources().openRawResource(this.mMovieResourceId));
        requestLayout();
    }

    public void setMovie(Movie movie) {
        this.mMovie = movie;
        requestLayout();
    }

    public Movie getMovie() {
        return this.mMovie;
    }

    public void setMovieTime(int time) {
        this.mCurrentAnimationTime = time;
        invalidate();
    }

    public void setPaused(boolean paused) {
        this.mPaused = paused;
        if (!paused) {
            this.mMovieStart = SystemClock.uptimeMillis() - ((long) this.mCurrentAnimationTime);
        }
        invalidate();
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maximumHeight;
        int maximumWidth;
        if (this.mMovie != null) {
            int movieWidth = this.mMovie.width();
            int movieHeight = this.mMovie.height();
            float scaleH = 1.0f;
            int measureModeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
            if (measureModeWidth != 0 && movieWidth > (maximumWidth = View.MeasureSpec.getSize(widthMeasureSpec))) {
                scaleH = movieWidth / maximumWidth;
            }
            float scaleW = 1.0f;
            int measureModeHeight = View.MeasureSpec.getMode(heightMeasureSpec);
            if (measureModeHeight != 0 && movieHeight > (maximumHeight = View.MeasureSpec.getSize(heightMeasureSpec))) {
                scaleW = movieHeight / maximumHeight;
            }
            this.mScale = 1.0f / Math.max(scaleH, scaleW);
            this.mMeasuredMovieWidth = (int) (movieWidth * this.mScale);
            this.mMeasuredMovieHeight = (int) (movieHeight * this.mScale);
            setMeasuredDimension(this.mMeasuredMovieWidth, this.mMeasuredMovieHeight);
            return;
        }
        setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mLeft = (getWidth() - this.mMeasuredMovieWidth) / 2.0f;
        this.mTop = (getHeight() - this.mMeasuredMovieHeight) / 2.0f;
        this.mVisible = getVisibility() == 0;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mMovie != null) {
            if (!this.mPaused) {
                updateAnimationTime();
                drawMovieFrame(canvas);
                invalidateView();
                return;
            }
            drawMovieFrame(canvas);
        }
    }

    @SuppressLint({"NewApi"})
    private void invalidateView() {
        if (this.mVisible) {
            if (Build.VERSION.SDK_INT >= 16) {
                postInvalidateOnAnimation();
            } else {
                invalidate();
            }
        }
    }

    private void updateAnimationTime() {
        long now = SystemClock.uptimeMillis();
        if (this.mMovieStart == 0) {
            this.mMovieStart = now;
        }
        int dur = this.mMovie.duration();
        if (dur == 0) {
            dur = 1000;
        }
        this.mCurrentAnimationTime = (int) ((now - this.mMovieStart) % ((long) dur));
    }

    private void drawMovieFrame(Canvas canvas) {
        this.mMovie.setTime(this.mCurrentAnimationTime);
        canvas.save(1);
        canvas.scale(this.mScale, this.mScale);
        this.mMovie.draw(canvas, this.mLeft / this.mScale, this.mTop / this.mScale);
        canvas.restore();
    }

    @Override // android.view.View
    @SuppressLint({"NewApi"})
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        this.mVisible = screenState == 1;
        invalidateView();
    }

    @Override // android.view.View
    @SuppressLint({"NewApi"})
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        this.mVisible = visibility == 0;
        invalidateView();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.mVisible = visibility == 0;
        invalidateView();
    }
}
