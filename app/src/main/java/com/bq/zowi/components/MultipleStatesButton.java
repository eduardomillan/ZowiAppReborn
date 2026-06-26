package com.bq.zowi.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class MultipleStatesButton<T> extends Button implements View.OnClickListener {
    private T currentState;
    private OnStateChangedListener<T> onStateChangedListener;
    private HashMap<T, Drawable> states;

    public interface OnStateChangedListener<T> {
        void onStateChanged(T t);
    }

    public MultipleStatesButton(Context context) {
        super(context);
        init(context);
    }

    public MultipleStatesButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultipleStatesButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setStates(HashMap<T, Drawable> states, T initialState) {
        this.states = states;
        this.currentState = initialState;
        invalidateBackground();
    }

    public T getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(T currentState) {
        this.currentState = currentState;
        invalidateBackground();
    }

    public void setOnStateChangedListener(OnStateChangedListener<T> onStateChangedListener) {
        this.onStateChangedListener = onStateChangedListener;
    }

    private void init(Context context) {
        setOnClickListener(this);
    }

    private void invalidateBackground() {
        if (Build.VERSION.SDK_INT < 16) {
            setBackgroundDrawable(this.states.get(this.currentState));
        } else {
            setBackground(this.states.get(this.currentState));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Iterator<T> keySetIterator = this.states.keySet().iterator();
        while (keySetIterator.hasNext()) {
            T key = keySetIterator.next();
            if (key.equals(this.currentState)) {
                if (keySetIterator.hasNext()) {
                    this.currentState = keySetIterator.next();
                } else {
                    this.currentState = this.states.keySet().iterator().next();
                }
                if (this.onStateChangedListener != null) {
                    this.onStateChangedListener.onStateChanged(this.currentState);
                }
                invalidateBackground();
                return;
            }
        }
    }
}
