package com.bq.zowi.components.games;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.bq.zowi.R;

/* JADX INFO: loaded from: classes.dex */
public class MouthGridItemView extends FrameLayout {
    public int column_position;
    private Context context;
    private ImageView image;
    public boolean isLedOn;
    public int row_position;

    public MouthGridItemView(Context context, int column_position, int row_position) {
        super(context);
        this.isLedOn = false;
        init(context, column_position, row_position);
    }

    public void handleTouch(boolean moveAction, boolean lastItemIsON) {
        if (moveAction) {
            if (lastItemIsON) {
                if (!this.isLedOn) {
                    this.image.setBackgroundResource(R.drawable.mouths_led_on);
                    this.isLedOn = true;
                    return;
                }
                return;
            }
            if (this.isLedOn) {
                this.image.setBackgroundResource(R.drawable.mouths_led_off);
                this.isLedOn = false;
                return;
            }
            return;
        }
        if (this.isLedOn) {
            this.image.setBackgroundResource(R.drawable.mouths_led_off);
            this.isLedOn = false;
        } else {
            this.image.setBackgroundResource(R.drawable.mouths_led_on);
            this.isLedOn = true;
        }
    }

    public void setState(boolean isLedOn) {
        this.image.setBackgroundResource(isLedOn ? R.drawable.mouths_led_on : R.drawable.mouths_led_off);
        this.isLedOn = isLedOn;
    }

    private void init(Context context, int column_position, int row_position) {
        this.column_position = column_position;
        this.row_position = row_position;
        this.context = context;
        this.image = (ImageView) LayoutInflater.from(context).inflate(R.layout.mouth_grid_item, this).findViewById(R.id.mouth_grid_item_image);
    }
}
