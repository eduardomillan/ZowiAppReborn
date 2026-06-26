package com.h6ah4i.android.widget.advrecyclerview.event;

import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerViewRecyclerEventDistributor extends BaseRecyclerViewEventDistributor<RecyclerView.RecyclerListener> {
    private InternalRecyclerListener mInternalRecyclerListener = new InternalRecyclerListener(this);

    @Override // com.h6ah4i.android.widget.advrecyclerview.event.BaseRecyclerViewEventDistributor
    protected void onRecyclerViewAttached(RecyclerView rv) {
        super.onRecyclerViewAttached(rv);
        rv.setRecyclerListener(this.mInternalRecyclerListener);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.event.BaseRecyclerViewEventDistributor
    protected void onRelease() {
        super.onRelease();
        if (this.mInternalRecyclerListener != null) {
            this.mInternalRecyclerListener.release();
            this.mInternalRecyclerListener = null;
        }
    }

    void handleOnViewRecycled(RecyclerView.ViewHolder holder) {
        if (this.mListeners != null) {
            for (RecyclerView.RecyclerListener listener : this.mListeners) {
                listener.onViewRecycled(holder);
            }
        }
    }

    private static class InternalRecyclerListener implements RecyclerView.RecyclerListener {
        private WeakReference<RecyclerViewRecyclerEventDistributor> mRefDistributor;

        public InternalRecyclerListener(RecyclerViewRecyclerEventDistributor distributor) {
            this.mRefDistributor = new WeakReference<>(distributor);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.RecyclerListener
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            RecyclerViewRecyclerEventDistributor distributor = this.mRefDistributor.get();
            if (distributor != null) {
                distributor.handleOnViewRecycled(holder);
            }
        }

        public void release() {
            this.mRefDistributor.clear();
        }
    }
}
