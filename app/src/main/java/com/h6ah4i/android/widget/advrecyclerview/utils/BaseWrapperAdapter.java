package com.h6ah4i.android.widget.advrecyclerview.utils;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class BaseWrapperAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private static final boolean LOCAL_LOGD = false;
    private static final String TAG = "ARVBaseWrapperAdapter";
    private BridgeObserver mBridgeObserver = new BridgeObserver(this);
    private RecyclerView.Adapter<VH> mWrappedAdapter;

    public BaseWrapperAdapter(RecyclerView.Adapter<VH> adapter) {
        this.mWrappedAdapter = adapter;
        this.mWrappedAdapter.registerAdapterDataObserver(this.mBridgeObserver);
        super.setHasStableIds(this.mWrappedAdapter.hasStableIds());
    }

    private boolean isWrappedAdapterAlive() {
        return this.mWrappedAdapter != null;
    }

    public void release() {
        onRelease();
        if (this.mWrappedAdapter != null && this.mBridgeObserver != null) {
            this.mWrappedAdapter.unregisterAdapterDataObserver(this.mBridgeObserver);
        }
        this.mWrappedAdapter = null;
        this.mBridgeObserver = null;
    }

    protected void onRelease() {
    }

    public RecyclerView.Adapter<VH> getWrappedAdapter() {
        return this.mWrappedAdapter;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(VH holder) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onViewAttachedToWindow(holder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(VH holder) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onViewDetachedFromWindow(holder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(VH holder) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onViewRecycled(holder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.setHasStableIds(hasStableIds);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        return (VH) this.mWrappedAdapter.onCreateViewHolder(viewGroup, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(VH holder, int position) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (isWrappedAdapterAlive()) {
            return this.mWrappedAdapter.getItemCount();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        return this.mWrappedAdapter.getItemId(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return this.mWrappedAdapter.getItemViewType(position);
    }

    protected void onHandleWrappedAdapterChanged() {
        notifyDataSetChanged();
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount, Object payload) {
        notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        if (itemCount != 1) {
            throw new IllegalStateException("itemCount should be always 1  (actual: " + itemCount + ")");
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    final void onWrappedAdapterChanged() {
        onHandleWrappedAdapterChanged();
    }

    final void onWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount);
    }

    final void onWrappedAdapterItemRangeChanged(int positionStart, int itemCount, Object payload) {
        onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount, payload);
    }

    final void onWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        onHandleWrappedAdapterItemRangeInserted(positionStart, itemCount);
    }

    final void onWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        onHandleWrappedAdapterItemRangeRemoved(positionStart, itemCount);
    }

    final void onWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        onHandleWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
    }

    private static final class BridgeObserver<VH extends RecyclerView.ViewHolder> extends RecyclerView.AdapterDataObserver {
        private WeakReference<BaseWrapperAdapter<VH>> mRefHolder;

        public BridgeObserver(BaseWrapperAdapter<VH> holder) {
            this.mRefHolder = new WeakReference<>(holder);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            BaseWrapperAdapter<VH> holder = this.mRefHolder.get();
            if (holder != null) {
                holder.onWrappedAdapterChanged();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int positionStart, int itemCount) {
            BaseWrapperAdapter<VH> holder = this.mRefHolder.get();
            if (holder != null) {
                holder.onWrappedAdapterItemRangeChanged(positionStart, itemCount);
            }
        }

        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            BaseWrapperAdapter<VH> holder = this.mRefHolder.get();
            if (holder != null) {
                holder.onWrappedAdapterItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int positionStart, int itemCount) {
            BaseWrapperAdapter<VH> holder = this.mRefHolder.get();
            if (holder != null) {
                holder.onWrappedAdapterItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            BaseWrapperAdapter<VH> holder = this.mRefHolder.get();
            if (holder != null) {
                holder.onWrappedAdapterItemRangeRemoved(positionStart, itemCount);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            BaseWrapperAdapter<VH> holder = this.mRefHolder.get();
            if (holder != null) {
                holder.onWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
            }
        }
    }
}
