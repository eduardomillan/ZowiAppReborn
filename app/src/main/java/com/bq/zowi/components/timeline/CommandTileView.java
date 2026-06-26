package com.bq.zowi.components.timeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bq.zowi.R;

/* JADX INFO: loaded from: classes.dex */
public class CommandTileView extends RelativeLayout {
    private Drawable commandDrawable;
    private ImageView commandImageView;

    public CommandTileView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CommandTileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CommandTileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setCommandDrawable(Drawable commandDrawable) {
        this.commandDrawable = commandDrawable;
        invalidateCommandDrawable();
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(R.layout.component_command_tile_view, (ViewGroup) this, true);
        this.commandImageView = (ImageView) findViewById(R.id.command_tile_view_title_image_view);
    }

    private void invalidateCommandDrawable() {
        this.commandImageView.setImageDrawable(this.commandDrawable);
    }
}
