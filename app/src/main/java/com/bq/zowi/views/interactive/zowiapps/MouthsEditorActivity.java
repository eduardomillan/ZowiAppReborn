package com.bq.zowi.views.interactive.zowiapps;

import android.os.Bundle;
import android.view.View;
import com.bq.zowi.R;
import com.bq.zowi.components.games.MouthGridItemView;
import com.bq.zowi.components.games.MouthGridLayout;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.interactive.zowiapps.MouthsEditorPresenter;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import com.bq.zowi.wireframes.zowiapps.MouthsEditorWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class MouthsEditorActivity extends InteractiveBaseActivity<MouthsEditorPresenter> implements MouthsEditorView {
    private final int GRID_COLUMNS = 6;
    private final int GRID_ROWS = 5;
    private MouthGridLayout gridMouth;
    private View helpLayout;

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_mouths_editor_view", R.layout.activity_mouths_editor_view);
        this.helpLayout = findViewById(R.id.mouths_editor_how_to_play_layout);
        View homeButton = findViewById(R.id.mouths_editor_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.MouthsEditorActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((MouthsEditorPresenter) MouthsEditorActivity.this.getPresenter()).homeButtonPressed();
            }
        });
        View helpButton = findViewById(R.id.mouths_editor_help_button);
        helpButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.MouthsEditorActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((MouthsEditorPresenter) MouthsEditorActivity.this.getPresenter()).helpButtonPressed();
            }
        });
        this.gridMouth = (MouthGridLayout) findViewById(R.id.mouths_editor_grid);
        this.gridMouth.setMouthGridLayoutTouchListener(new MouthGridLayout.MouthGridLayoutTouchListener() { // from class: com.bq.zowi.views.interactive.zowiapps.MouthsEditorActivity.3
            @Override // com.bq.zowi.components.games.MouthGridLayout.MouthGridLayoutTouchListener
            public void sendCurrentMouth() {
                ((MouthsEditorPresenter) MouthsEditorActivity.this.getPresenter()).sendLedMouth(MouthsEditorActivity.this.getBinaryGridRepresentation());
            }
        });
        initializeMouthGrid(6, 5);
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public MouthsEditorPresenter resolvePresenter() {
        MouthsEditorPresenter presenter = AndroidDependencyInjector.getInstance().provideMouthsEditorPresenter();
        MouthsEditorWireframeImpl wireframe = AndroidDependencyInjector.getInstance().provideMouthsEditorWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    private void initializeMouthGrid(int numOfColumns, int numOfRows) {
        this.gridMouth.setColumnCount(numOfColumns);
        this.gridMouth.setRowCount(numOfRows);
        for (int y = 0; y < numOfRows; y++) {
            for (int x = 0; x < numOfColumns; x++) {
                this.gridMouth.addView(new MouthGridItemView(this, x, y));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getBinaryGridRepresentation() {
        String binaryGrid = "";
        for (int i = 0; i < this.gridMouth.getChildCount(); i++) {
            MouthGridItemView item = (MouthGridItemView) this.gridMouth.getChildAt(i);
            binaryGrid = binaryGrid + (item.isLedOn ? "1" : "0");
        }
        return binaryGrid;
    }

    @Override // com.bq.zowi.views.interactive.zowiapps.MouthsEditorView
    public void showHelp() {
        this.helpLayout.setVisibility(0);
    }
}
