package com.bq.zowi.components.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder<T>> {
    protected final List<T> items;
    protected final RecyclerResolver<T> resolver;

    public RecyclerAdapter(RecyclerResolver<T> resolver) {
        this.items = new ArrayList();
        this.resolver = resolver;
    }

    public RecyclerAdapter(List<T> items, RecyclerResolver<T> resolver) {
        this(resolver);
        addAll(items);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return this.resolver.getViewHolderFromViewTypeInternal(viewType, parent);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerViewHolder<T> holder, int position) {
        holder.bind(getItem(position));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return this.resolver.getItemViewType(getItem(position));
    }

    public T getItem(int position) {
        return this.items.get(position);
    }

    public void add(T item) {
        if (this.items.add(item)) {
            notifyItemInserted(this.items.size() - 1);
        }
    }

    public void add(T item, int position) {
        this.items.add(position, item);
        notifyItemInserted(position);
    }

    public void addAll(List<T> items) {
        int initialPosition = this.items.size();
        if (this.items.addAll(items)) {
            notifyItemRangeInserted(initialPosition, items.size());
        }
    }

    public void addAll(List<T> items, int position) {
        if (this.items.addAll(position, items)) {
            notifyItemRangeInserted(position, items.size());
        }
    }

    public void update(int position, T newItem) {
        this.items.set(position, newItem);
        notifyItemChanged(position);
    }

    public void remove(T item) {
        int index = this.items.indexOf(item);
        if (index != -1) {
            this.items.remove(item);
            notifyItemRemoved(index);
        }
    }

    public void remove(int position) {
        this.items.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        int size = this.items.size();
        this.items.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void removeAll(int viewType) {
        List<T> itemsToRemove = new ArrayList<>(this.items.size());
        List<Integer> itemsToRemoveIndexes = new ArrayList<>(this.items.size());
        int size = this.items.size();
        for (int i = 0; i < size; i++) {
            if (getItemViewType(i) == viewType) {
                itemsToRemove.add(getItem(i));
                itemsToRemoveIndexes.add(Integer.valueOf(i));
            }
        }
        if (this.items.removeAll(itemsToRemove)) {
            for (Integer index : itemsToRemoveIndexes) {
                notifyItemRemoved(index.intValue());
            }
        }
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        removeAll();
        addAll(items);
    }
}
