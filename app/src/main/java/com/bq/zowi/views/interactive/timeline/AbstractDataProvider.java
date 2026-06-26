package com.bq.zowi.views.interactive.timeline;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractDataProvider {

    public static abstract class Data {
        public abstract long getId();

        public abstract int getSwipeReactionType();

        public abstract String getText();

        public abstract int getViewType();

        public abstract boolean isPinnedToSwipeLeft();

        public abstract boolean isSectionHeader();

        public abstract void setPinnedToSwipeLeft(boolean z);
    }

    public abstract int getCount();

    public abstract Data getItem(int i);

    public abstract void moveItem(int i, int i2);

    public abstract void removeItem(int i);

    public abstract int undoLastRemoval();
}
