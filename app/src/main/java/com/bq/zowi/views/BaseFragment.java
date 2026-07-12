package com.bq.zowi.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.View;
import com.bq.zowi.presenters.BasePresenter;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseFragment<T extends BasePresenter<?, ?>> extends Fragment {
    private T presenter;

    protected abstract T resolvePresenter();

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.presenter = (T) resolvePresenter();
        this.presenter.onCreateView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().onDestroyView();
        getPresenter().unBindViewAndWireframe();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    protected T getPresenter() {
        if (this.presenter.getView() != this) {
            throw new IllegalArgumentException("Not Binded!");
        }
        return this.presenter;
    }
}
