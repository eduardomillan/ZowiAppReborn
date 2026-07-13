package com.bq.zowi.views;

import android.R;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.bq.zowi.BuildConfig;
import com.bq.zowi.presenters.BasePresenter;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseActivity<T extends BasePresenter<?, ?>> extends AppCompatActivity {
    private static final String VERSION_BADGE_TAG = "app_version_badge";
    private T presenter;

    protected abstract T resolvePresenter();

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.presenter = (T) resolvePresenter();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.presenter.onCreateView();
        setTitle(getString(com.bq.zowi.R.string.app_name) + " v" + BuildConfig.VERSION_NAME);
        ensureVersionBadgeIsVisible();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
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

    private void ensureVersionBadgeIsVisible() {
        View root = getWindow().getDecorView();
        if (root.findViewWithTag(VERSION_BADGE_TAG) != null) {
            return;
        }
        TextView versionBadge = new TextView(this);
        versionBadge.setTag(VERSION_BADGE_TAG);
        String versionText = "Build " + BuildConfig.VERSION_CODE;
        versionBadge.setText(versionText);
        versionBadge.setContentDescription(versionText);
        versionBadge.setTextColor(-1);
        versionBadge.setTextSize(2, 12.0f);
        versionBadge.setShadowLayer(4.0f, 0.0f, 0.0f, -16777216);
        versionBadge.setPadding(dpToPx(8), dpToPx(4), dpToPx(8), dpToPx(4));
        versionBadge.setClickable(false);
        versionBadge.setFocusable(false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2, Gravity.TOP | Gravity.END);
        params.setMargins(dpToPx(8), dpToPx(24), dpToPx(8), dpToPx(8));
        addContentView(versionBadge, params);
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics()));
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
