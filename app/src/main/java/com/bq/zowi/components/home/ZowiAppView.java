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

    public void setButtonBackground(int drawableId, Context context) {
        if (Build.VERSION.SDK_INT < 16) {
            this.button.setBackgroundDrawable(ContextCompat.getDrawable(context, drawableId));
        } else {
            this.button.setBackground(ContextCompat.getDrawable(context, drawableId));
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ZowiAppView, 0, 0);
        try {
            String labelText = a.getString(1);
            Drawable buttonBackgroundDrawable = a.getDrawable(0);
            boolean enabled = a.getBoolean(2, true);
            a.recycle();
            if (!isInEditMode()) {
                LayoutInflater.from(context).inflate(R.layout.component_zowi_app, this);
                this.button = (Button) findViewById(R.id.zowi_app_button);
                this.label = (TextView) findViewById(R.id.zowi_app_label);
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
                this.button.setEnabled(enabled);
            }
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener l) {
        this.button.setOnClickListener(l);
    }
}
