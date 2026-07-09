package com.bq.zowi.components.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.utils.ResourceResolver;

/* JADX INFO: loaded from: classes.dex */
public class ZowiAppView extends LinearLayout {
    private Button button;
    private TextView label;

    public ZowiAppView(Context context) {
        super(context);
        init(context, null);
    }

    public ZowiAppView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZowiAppView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setButtonBackground(String drawableName, Context context) {
        Drawable backgroundDrawable = ResourceResolver.getDrawableByResourceId(drawableName, context);
        if (backgroundDrawable == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 16) {
            this.button.setBackgroundDrawable(backgroundDrawable);
        } else {
            this.button.setBackground(backgroundDrawable);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ZowiAppView, 0, 0);
        try {
            String labelText = a.getString(R.styleable.ZowiAppView_label);
            Drawable buttonBackgroundDrawable = a.getDrawable(R.styleable.ZowiAppView_buttonBackground);
            boolean enabled = true;
            if (!isInEditMode()) {
                int layoutId = context.getResources().getIdentifier("component_zowi_app", "layout", context.getPackageName());
                LayoutInflater.from(context).inflate(layoutId != 0 ? layoutId : R.layout.component_zowi_app, this);
                int buttonId = context.getResources().getIdentifier("zowi_app_button", "id", context.getPackageName());
                int labelId = context.getResources().getIdentifier("zowi_app_label", "id", context.getPackageName());
                this.button = (Button) findViewById(buttonId != 0 ? buttonId : R.id.zowi_app_button);
                this.label = (TextView) findViewById(labelId != 0 ? labelId : R.id.zowi_app_label);
                if (labelText != null) {
                    this.label.setText(labelText);
                }
                if (buttonBackgroundDrawable != null) {
                    if (Build.VERSION.SDK_INT < 16) {
                        this.button.setBackgroundDrawable(buttonBackgroundDrawable);
                    } else {
                        this.button.setBackground(buttonBackgroundDrawable);
                    }
                }
                this.button.setFocusable(false);
                this.label.setFocusable(false);
                setEnabled(enabled);
                setClickable(enabled);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener l) {
        super.setOnClickListener(l);
        if (this.button != null) {
            this.button.setOnClickListener(l);
        }
        if (this.label != null) {
            this.label.setOnClickListener(l);
        }
    }

    @Override // android.view.View
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        if (this.button != null) {
            this.button.setClickable(clickable);
        }
        if (this.label != null) {
            this.label.setClickable(clickable);
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (this.button != null) {
            this.button.setEnabled(enabled);
        }
        if (this.label != null) {
            this.label.setEnabled(enabled);
        }
    }
}
