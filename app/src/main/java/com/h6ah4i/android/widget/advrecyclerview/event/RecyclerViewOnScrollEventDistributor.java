package com.h6ah4i.android.widget.advrecyclerview.event;

import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class RecyclerViewOnScrollEventDistributor extends BaseRecyclerViewEventDistributor<RecyclerView.OnScrollListener> {
    private InternalOnScrollListener mInternalOnScrollListener = new InternalOnScrollListener(this);

    @Override // com.h6ah4i.android.widget.advrecyclerview.event.BaseRecyclerViewEventDistributor
    protected void onRecyclerViewAttached(RecyclerView rv) {
        super.onRecyclerViewAttached(rv);
        rv.addOnScrollListener(this.mInternalOnScrollListener);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.event.BaseRecyclerViewEventDistributor
    protected void onRelease() {
        if (this.mInternalOnScrollListener != null) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.removeOnScrollListener(this.mInternalOnScrollListener);
            }
            this.mInternalOnScrollListener.release();
            this.mInternalOnScrollListener = null;
        }
        super.onRelease();
    }

    void handleOnScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (this.mListeners != null) {
            for (T listener : this.mListeners) {
                listener.onScrollStateChanged(recyclerView, newState);
            }
        }
    }

    void handleOnScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (this.mListeners != null) {
            for (T listener : this.mListeners) {
                listener.onScrolled(recyclerView, dx, dy);
            }
        }
    }

    private static class InternalOnScrollListener extends RecyclerView.OnScrollListener {
        private WeakReference<RecyclerViewOnScrollEventDistributor> mRefDistributor;

        public InternalOnScrollListener(RecyclerViewOnScrollEventDistributor distributor) {
            this.mRefDistributor = new WeakReference<>(distributor);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            RecyclerViewOnScrollEventDistributor distributor = this.mRefDistributor.get();
            if (distributor != null) {
                distributor.handleOnScrollStateChanged(recyclerView, newState);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            RecyclerViewOnScrollEventDistributor holder = this.mRefDistributor.get();
            if (holder != null) {
                holder.handleOnScrolled(recyclerView, dx, dy);
            }
        }

        public void release() {
            this.mRefDistributor.clear();
        }
    }
}
