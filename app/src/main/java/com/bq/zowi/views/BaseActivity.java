package com.bq.zowi.views;

import android.R;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import com.adobe.mobile.Config;
import com.bq.analytics.core.AnalyticsController;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.BasePresenter;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseActivity<T extends BasePresenter<?, ?>> extends AppCompatActivity {
    private AnalyticsController analyticsController;
    private T presenter;

    protected abstract T resolvePresenter();

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.presenter = (T) resolvePresenter();
        this.analyticsController = AndroidDependencyInjector.getInstance().provideAnalyticsController();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.presenter.onCreateView();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Config.collectLifecycleData(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        Config.pauseCollectingLifecycleData();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroyView();
        getPresenter().unBindViewAndWireframe();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public T getPresenter() {
        if (this.presenter.getView() != this) {
            throw new IllegalArgumentException("Not Binded!");
        }
        return this.presenter;
    }

    protected AnalyticsController getAnalyticsController() {
        return this.analyticsController;
    }

    protected final void setResolvedContentView(String layoutName, int fallbackLayoutId) {
        int layoutId = getResources().getIdentifier(layoutName, "layout", getPackageName());
        setContentView(layoutId != 0 ? layoutId : fallbackLayoutId);
    }

    protected final int resolveViewId(String viewIdName, int fallbackViewId) {
        int viewId = getResources().getIdentifier(viewIdName, "id", getPackageName());
        return viewId != 0 ? viewId : fallbackViewId;
    }

    protected final View findResolvedView(String viewIdName, int fallbackViewId) {
        return findViewById(resolveViewId(viewIdName, fallbackViewId));
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public void supportNavigateUpTo(Intent upIntent) {
        onBackPressed();
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
