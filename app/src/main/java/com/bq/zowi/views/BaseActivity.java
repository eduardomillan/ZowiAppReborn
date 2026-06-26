package com.bq.zowi.views;

import android.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.adobe.mobile.Config;
import com.bq.analytics.core.AnalyticsController;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.BasePresenter;
import com.comscore.analytics.comScore;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseActivity<T extends BasePresenter<?, ?>> extends AppCompatActivity {
    private AnalyticsController analyticsController;
    private T presenter;

    protected abstract T resolvePresenter();

    @Override // android.support.v7.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.presenter = (T) resolvePresenter();
        this.analyticsController = AndroidDependencyInjector.getInstance().provideAnalyticsController();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.presenter.onCreateView();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Config.collectLifecycleData(this);
        comScore.onEnterForeground();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        Config.pauseCollectingLifecycleData();
        comScore.onExitForeground();
    }

    @Override // android.support.v7.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
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

    @Override // android.support.v7.app.AppCompatActivity
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
