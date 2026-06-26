package com.h6ah4i.android.widget.advrecyclerview.utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerViewAdapterUtils {
    private RecyclerViewAdapterUtils() {
    }

    public static RecyclerView getParentRecyclerView(@Nullable View view) {
        if (view == null) {
            return null;
        }
        Object parent = view.getParent();
        if (parent instanceof RecyclerView) {
            return (RecyclerView) parent;
        }
        if (parent instanceof View) {
            return getParentRecyclerView((View) parent);
        }
        return null;
    }

    public static View getParentViewHolderItemView(@Nullable View view) {
        if (view == null) {
            return null;
        }
        Object parent = view.getParent();
        if (!(parent instanceof RecyclerView)) {
            if (parent instanceof View) {
                return getParentViewHolderItemView((View) parent);
            }
            return null;
        }
        return view;
    }

    public static RecyclerView.ViewHolder getViewHolder(@Nullable View view) {
        RecyclerView rv = getParentRecyclerView(view);
        View rvChild = getParentViewHolderItemView(view);
        if (rv == null || rvChild == null) {
            return null;
        }
        return rv.getChildViewHolder(rvChild);
    }
}
