package com.bq.zowi.wireframes.projects;

import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import com.bq.zowi.views.interactive.home.HomeViewActivity;
import com.bq.zowi.views.interactive.projects.ProjectQuizViewActivity;
import com.bq.zowi.views.interactive.projects.ProjectViewActivity;
import com.bq.zowi.wireframes.interactive.InteractiveWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class ProjectWireframeImpl extends InteractiveWireframeImpl implements ProjectWireframe {
    public ProjectWireframeImpl(FragmentActivity activity) {
        super(activity);
    }

    @Override // com.bq.zowi.wireframes.projects.ProjectWireframe
    public void presentQuiz(String projectId) {
        Intent i = new Intent(this.activity, (Class<?>) ProjectQuizViewActivity.class);
        i.putExtra(ProjectViewActivity.PROJECT_ID_EXTRA, projectId);
        this.activity.startActivity(i);
    }

    @Override // com.bq.zowi.wireframes.projects.ProjectWireframe
    public void presentHome() {
        Intent i = new Intent(this.activity, (Class<?>) HomeViewActivity.class);
        i.setFlags(603979776);
        this.activity.startActivity(i);
        this.activity.finish();
    }
}
