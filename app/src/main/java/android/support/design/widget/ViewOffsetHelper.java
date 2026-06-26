package android.support.design.widget;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class ViewOffsetHelper {
    private int mLayoutLeft;
    private int mLayoutTop;
    private int mOffsetLeft;
    private int mOffsetTop;
    private final View mView;

    public ViewOffsetHelper(View view) {
        this.mView = view;
    }

    public void onViewLayout() {
        this.mLayoutTop = this.mView.getTop();
        this.mLayoutLeft = this.mView.getLeft();
        updateOffsets();
    }

    private void updateOffsets() {
        if (Build.VERSION.SDK_INT == 22) {
            ViewCompat.setTranslationY(this.mView, this.mOffsetTop);
            ViewCompat.setTranslationX(this.mView, this.mOffsetLeft);
        } else {
            ViewCompat.offsetTopAndBottom(this.mView, (this.mOffsetTop - this.mView.getTop()) - this.mLayoutTop);
            ViewCompat.offsetLeftAndRight(this.mView, (this.mOffsetLeft - this.mView.getLeft()) - this.mLayoutLeft);
        }
    }

    public boolean setTopAndBottomOffset(int offset) {
        if (this.mOffsetTop == offset) {
            return false;
        }
        this.mOffsetTop = offset;
        updateOffsets();
        return true;
    }

    public boolean setLeftAndRightOffset(int offset) {
        if (this.mOffsetLeft == offset) {
            return false;
        }
        this.mOffsetLeft = offset;
        updateOffsets();
        return true;
    }

    public int getTopAndBottomOffset() {
        return this.mOffsetTop;
    }

    public int getLeftAndRightOffset() {
        return this.mOffsetLeft;
    }
}
