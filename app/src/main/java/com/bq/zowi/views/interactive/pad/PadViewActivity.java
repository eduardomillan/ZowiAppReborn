package com.bq.zowi.views.interactive.pad;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialog;
import com.bq.zowi.components.makerboxdialogs.MakerBoxDialogScrollable;
import com.bq.zowi.components.recyclerview.RecyclerAdapter;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.models.commands.GridCommand;
import com.bq.zowi.presenters.interactive.pad.PadPresenter;
import com.bq.zowi.utils.ResourceResolver;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;
import com.bq.zowi.views.interactive.timeline.CommandsGridViewHolderResolver;
import com.bq.zowi.views.interactive.timeline.CommandsTileViewHolder;
import com.bq.zowi.wireframes.pad.PadWireframe;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PadViewActivity extends InteractiveBaseActivity<PadPresenter> implements PadView, CommandsTileViewHolder.CommandItemListener {
    public static final int DOWN_BUTTON = 2;
    public static final int HIGH_SPEED_POSITION = 2;
    public static final int LEFT_BUTTON = 3;
    public static final int LOW_SPEED = 2000;
    public static final int LOW_SPEED_POSITION = 0;
    public static final int MEDIUM_SPEED = 1000;
    public static final int MEDIUM_SPEED_POSITION = 1;
    public static final int RIGHT_BUTTON = 1;
    public static final int UPPER_LEFT_BUTTON = 4;
    public static final int UPPER_RIGHT_BUTTON = 5;
    public static final int UP_BUTTON = 0;
    private MakerBoxDialogScrollable actionsDialog;
    private Button bendButton;
    private RecyclerView commandsRecyclerView;
    private RecyclerAdapter<GridCommand> commmandsAdapter;
    private Button crusaitoButton;
    private int currentSpeedPosition;
    private Button downButton;
    private Button flappingButton;
    private GridLayoutManager gridLayoutManager;
    private MakerBoxDialog howToPlayLayout;
    private TextView howToPlayText;
    private Button jitterButton;
    private Button leftButton;
    private Button rightButton;
    private Button shakelegButton;
    private Button swingButton;
    private Button upButton;
    private Button updownButton;
    private Button upperLeftButton;
    private Button upperRighButton;
    public static final int HIGH_SPEED = 700;
    private static final int[] speedArray = {2000, 1000, HIGH_SPEED};
    private final List<Integer> actionButtonsPressedList = new ArrayList();
    private final List<Integer> movementButtonsPressedList = new ArrayList();
    private int movementButtonPressedId = -1;
    private final View.OnTouchListener actionButtonOnTouchListener = new View.OnTouchListener() { // from class: com.bq.zowi.views.interactive.pad.PadViewActivity.1
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent event) {
            if (view.isClickable()) {
                if (event.getAction() == 0) {
                    PadViewActivity.this.addToButtonsPressedList(PadViewActivity.this.actionButtonsPressedList, view.getId());
                    if (PadViewActivity.this.arePressedAnyOfThisButtons(PadViewActivity.this.actionButtonsPressedList, PadViewActivity.this.bendButton.getId(), PadViewActivity.this.shakelegButton.getId(), PadViewActivity.this.crusaitoButton.getId())) {
                        PadViewActivity.this.blockUpAndDownButton();
                    }
                    if (PadViewActivity.this.arePressedAnyOfThisButtons(PadViewActivity.this.actionButtonsPressedList, PadViewActivity.this.flappingButton.getId())) {
                        PadViewActivity.this.blockLeftAndRightButton();
                    }
                    if (PadViewActivity.this.arePressedAnyOfThisButtons(PadViewActivity.this.actionButtonsPressedList, PadViewActivity.this.updownButton.getId())) {
                        ((PadPresenter) PadViewActivity.this.getPresenter()).updownPressed();
                    } else if (PadViewActivity.this.arePressedAnyOfThisButtons(PadViewActivity.this.actionButtonsPressedList, PadViewActivity.this.jitterButton.getId())) {
                        ((PadPresenter) PadViewActivity.this.getPresenter()).jitterPressed();
                    } else if (PadViewActivity.this.arePressedAnyOfThisButtons(PadViewActivity.this.actionButtonsPressedList, PadViewActivity.this.swingButton.getId())) {
                        ((PadPresenter) PadViewActivity.this.getPresenter()).swingPressed();
                    }
                } else if (event.getAction() == 1) {
                    if (PadViewActivity.this.arePressedAnyOfThisButtons(PadViewActivity.this.actionButtonsPressedList, PadViewActivity.this.updownButton.getId(), PadViewActivity.this.jitterButton.getId(), PadViewActivity.this.swingButton.getId())) {
                        PadViewActivity.this.clearButtonsPressedList(PadViewActivity.this.actionButtonsPressedList);
                    } else {
                        PadViewActivity.this.removeFromButtonsPressedList(PadViewActivity.this.actionButtonsPressedList, view.getId());
                    }
                    ((PadPresenter) PadViewActivity.this.getPresenter()).actionButtonReleased();
                    PadViewActivity.this.unblockUpDownButton(PadViewActivity.this.actionButtonsPressedList.isEmpty());
                    PadViewActivity.this.unblockLeftRightButton(PadViewActivity.this.actionButtonsPressedList.isEmpty());
                }
            }
            return false;
        }
    };
    private View.OnTouchListener movementButtonOnTouchListener = new View.OnTouchListener() { // from class: com.bq.zowi.views.interactive.pad.PadViewActivity.2
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == 0) {
                PadViewActivity.this.addToButtonsPressedList(PadViewActivity.this.movementButtonsPressedList, view.getId());
                PadViewActivity.this.pressedMovementButton(PadViewActivity.this.getMovementButtonPressedId(view, event));
            }
            if (event.getAction() == 1) {
                if (PadViewActivity.this.arePressedAnyOfThisButtons(PadViewActivity.this.movementButtonsPressedList, PadViewActivity.this.upperLeftButton.getId(), PadViewActivity.this.upperRighButton.getId())) {
                    PadViewActivity.this.clearButtonsPressedList(PadViewActivity.this.movementButtonsPressedList);
                } else {
                    PadViewActivity.this.removeFromButtonsPressedList(PadViewActivity.this.movementButtonsPressedList, view.getId());
                }
                PadViewActivity.this.unblockUpDownButton(false);
                PadViewActivity.this.unblockLeftRightButton(false);
                ((PadPresenter) PadViewActivity.this.getPresenter()).actionButtonReleased();
            }
            return false;
        }
    };

    static /* synthetic */ int access$2808(PadViewActivity x0) {
        int i = x0.currentSpeedPosition;
        x0.currentSpeedPosition = i + 1;
        return i;
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_pad_view", R.layout.activity_pad_view);
        this.howToPlayLayout = (MakerBoxDialog) findViewById(R.id.gamepad_how_to_play_layout);
        this.howToPlayText = (TextView) findViewById(R.id.gamepad_how_to_play_text);
        Button homeButton = (Button) findViewById(R.id.gamepad_home);
        homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.pad.PadViewActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((PadPresenter) PadViewActivity.this.getPresenter()).homeButtonPressed();
            }
        });
        Button howToPlayButton = (Button) findViewById(R.id.gamepad_howtoplay);
        howToPlayButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.pad.PadViewActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ((PadPresenter) PadViewActivity.this.getPresenter()).helpButtonPressed();
            }
        });
        this.currentSpeedPosition = 1;
        final Button changeSpeedButton = (Button) findViewById(R.id.gamepad_change_speed);
        changeSpeedButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.pad.PadViewActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PadViewActivity.access$2808(PadViewActivity.this);
                if (PadViewActivity.this.currentSpeedPosition == 3) {
                    PadViewActivity.this.currentSpeedPosition = 0;
                }
                ((PadPresenter) PadViewActivity.this.getPresenter()).configureDuration(PadViewActivity.speedArray[PadViewActivity.this.currentSpeedPosition] + "");
                switch (PadViewActivity.this.currentSpeedPosition) {
                    case 0:
                        changeSpeedButton.setBackgroundResource(R.drawable.bottom_pad_zowi_change_speed_slow_button_selector);
                        break;
                    case 1:
                        changeSpeedButton.setBackgroundResource(R.drawable.bottom_pad_zowi_change_speed_medium_button_selector);
                        break;
                    case 2:
                        changeSpeedButton.setBackgroundResource(R.drawable.bottom_pad_zowi_change_speed_fast_button_selector);
                        break;
                }
            }
        });
        this.upButton = (Button) findViewById(R.id.gamepad_arrow_up);
        this.upButton.setOnTouchListener(this.movementButtonOnTouchListener);
        this.downButton = (Button) findViewById(R.id.gamepad_arrow_down);
        this.downButton.setOnTouchListener(this.movementButtonOnTouchListener);
        this.leftButton = (Button) findViewById(R.id.gamepad_arrow_left);
        this.leftButton.setOnTouchListener(this.movementButtonOnTouchListener);
        this.rightButton = (Button) findViewById(R.id.gamepad_arrow_right);
        this.rightButton.setOnTouchListener(this.movementButtonOnTouchListener);
        this.upperLeftButton = (Button) findViewById(R.id.gamepad_turn_left);
        this.upperLeftButton.setOnTouchListener(this.movementButtonOnTouchListener);
        this.upperRighButton = (Button) findViewById(R.id.gamepad_turn_right);
        this.upperRighButton.setOnTouchListener(this.movementButtonOnTouchListener);
        this.bendButton = (Button) findViewById(R.id.gamepad_bend_button);
        this.bendButton.setOnTouchListener(this.actionButtonOnTouchListener);
        this.shakelegButton = (Button) findViewById(R.id.gamepad_shake_leg_button);
        this.shakelegButton.setOnTouchListener(this.actionButtonOnTouchListener);
        this.updownButton = (Button) findViewById(R.id.gamepad_updown_button);
        this.updownButton.setOnTouchListener(this.actionButtonOnTouchListener);
        this.jitterButton = (Button) findViewById(R.id.gamepad_jitter_button);
        this.jitterButton.setOnTouchListener(this.actionButtonOnTouchListener);
        this.swingButton = (Button) findViewById(R.id.gamepad_swing_button);
        this.swingButton.setOnTouchListener(this.actionButtonOnTouchListener);
        this.flappingButton = (Button) findViewById(R.id.gamepad_flapping_button);
        this.flappingButton.setOnTouchListener(this.actionButtonOnTouchListener);
        this.crusaitoButton = (Button) findViewById(R.id.gamepad_crusaito_button);
        this.crusaitoButton.setOnTouchListener(this.actionButtonOnTouchListener);
        this.actionsDialog = (MakerBoxDialogScrollable) findViewById(R.id.gamepad_actions_dialog);
        this.commandsRecyclerView = (RecyclerView) findViewById(R.id.gamepad_actions_grid_commands_recycler_view);
        this.gridLayoutManager = new GridLayoutManager(this, ResourceResolver.getIntegerByResourceId("makerbox_dialog_scrollable_columns", this));
        this.commandsRecyclerView.setLayoutManager(this.gridLayoutManager);
        this.commandsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.commmandsAdapter = new RecyclerAdapter<>(new CommandsGridViewHolderResolver(this));
        this.commandsRecyclerView.setAdapter(this.commmandsAdapter);
        Button mouthsButton = (Button) findViewById(R.id.gamepad_mouths_button);
        mouthsButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.pad.PadViewActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((PadPresenter) PadViewActivity.this.getPresenter()).mouthsPressed();
            }
        });
        Button animationsButton = (Button) findViewById(R.id.gamepad_animations_button);
        animationsButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.pad.PadViewActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ((PadPresenter) PadViewActivity.this.getPresenter()).animationsPressed();
            }
        });
        this.zowiDependantViews = new View[]{this.upButton, this.downButton, this.leftButton, this.rightButton, this.upperLeftButton, this.upperRighButton, this.bendButton, this.shakelegButton, this.updownButton, this.jitterButton, this.swingButton, this.flappingButton, this.crusaitoButton};
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ((PadPresenter) getPresenter()).gameReady();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public PadPresenter resolvePresenter() {
        PadPresenter presenter = AndroidDependencyInjector.getInstance().providePadPresenter();
        PadWireframe wireframe = AndroidDependencyInjector.getInstance().providePadWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pressedMovementButton(int leftButtonPressedId) {
        switch (leftButtonPressedId) {
            case 0:
                this.upButton.setBackgroundResource(R.drawable.pressed_pad_walk_forward);
                if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.flappingButton.getId())) {
                    ((PadPresenter) getPresenter()).flappingForward();
                } else {
                    ((PadPresenter) getPresenter()).upArrowPressed();
                }
                break;
            case 1:
                this.rightButton.setBackgroundResource(R.drawable.pressed_pad_moonwalker_right);
                if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.crusaitoButton.getId())) {
                    ((PadPresenter) getPresenter()).crusaitoRight();
                } else if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.bendButton.getId())) {
                    ((PadPresenter) getPresenter()).bendRight();
                } else if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.shakelegButton.getId())) {
                    ((PadPresenter) getPresenter()).shakeLegRight();
                } else {
                    ((PadPresenter) getPresenter()).moonwalkerRight();
                }
                break;
            case 2:
                this.downButton.setBackgroundResource(R.drawable.pressed_pad_walk_backward);
                if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.flappingButton.getId())) {
                    ((PadPresenter) getPresenter()).flappingBackward();
                } else {
                    ((PadPresenter) getPresenter()).downArrowPressed();
                }
                break;
            case 3:
                this.leftButton.setBackgroundResource(R.drawable.pressed_pad_moonwalker_left);
                if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.crusaitoButton.getId())) {
                    ((PadPresenter) getPresenter()).crusaitoLeft();
                } else if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.bendButton.getId())) {
                    ((PadPresenter) getPresenter()).bendLeft();
                } else if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.shakelegButton.getId())) {
                    ((PadPresenter) getPresenter()).shakeLegLeft();
                } else {
                    ((PadPresenter) getPresenter()).moonwalkerLeft();
                }
                break;
            case 4:
                blockUpAndDownButton();
                blockLeftAndRightButton();
                ((PadPresenter) getPresenter()).leftArrowPressed();
                break;
            case 5:
                blockUpAndDownButton();
                blockLeftAndRightButton();
                ((PadPresenter) getPresenter()).rightArrowPressed();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMovementButtonPressedId(View viewClicked, MotionEvent event) {
        if (viewClicked.getId() == this.rightButton.getId() && isInsideView(viewClicked, this.rightButton, event, R.drawable.pad_moonwalker_right)) {
            this.movementButtonPressedId = 1;
        } else if (viewClicked.getId() == this.upButton.getId() && isInsideView(viewClicked, this.upButton, event, R.drawable.pad_walk_forward)) {
            this.movementButtonPressedId = 0;
        } else if (viewClicked.getId() == this.downButton.getId() && isInsideView(viewClicked, this.downButton, event, R.drawable.pad_walk_backward)) {
            this.movementButtonPressedId = 2;
        } else if (viewClicked.getId() == this.leftButton.getId() && isInsideView(viewClicked, this.leftButton, event, R.drawable.pad_moonwalker_left)) {
            this.movementButtonPressedId = 3;
        } else if (viewClicked.getId() == this.upperLeftButton.getId()) {
            this.movementButtonPressedId = 4;
        } else if (viewClicked.getId() == this.upperRighButton.getId()) {
            this.movementButtonPressedId = 5;
        } else {
            if (viewClicked.getId() == this.rightButton.getId()) {
                if (event.getY() <= this.rightButton.getHeight() / 2) {
                    if (isInsideView(viewClicked, this.upButton, event, R.drawable.pad_walk_forward)) {
                        this.movementButtonPressedId = 0;
                    }
                } else if (isInsideView(viewClicked, this.downButton, event, R.drawable.pad_walk_backward)) {
                    this.movementButtonPressedId = 2;
                }
            }
            if (viewClicked.getId() == this.leftButton.getId()) {
                if (event.getY() <= this.leftButton.getHeight() / 2) {
                    if (isInsideView(viewClicked, this.upButton, event, R.drawable.pad_walk_forward)) {
                        this.movementButtonPressedId = 0;
                    }
                } else if (isInsideView(viewClicked, this.downButton, event, R.drawable.pad_walk_backward)) {
                    this.movementButtonPressedId = 2;
                }
            }
        }
        return this.movementButtonPressedId;
    }

    private boolean isInsideView(View oldView, View newView, MotionEvent event, int drawable) {
        float viewX = (oldView.getLeft() + event.getX()) - newView.getLeft();
        float viewY = (oldView.getTop() + event.getY()) - newView.getTop();
        return isBitmapClicked(viewX, viewY, drawable);
    }

    private boolean isBitmapClicked(float localX, float localY, int drawable) {
        int x = (int) localX;
        int y = (int) localY;
        Bitmap theBitmap = BitmapFactory.decodeResource(getResources(), drawable);
        return x >= 0 && y >= 0 && x < theBitmap.getWidth() && y < theBitmap.getHeight() && theBitmap.getPixel(x, y) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void blockUpAndDownButton() {
        if (!this.actionButtonsPressedList.isEmpty()) {
            clearButtonsPressedList(this.movementButtonsPressedList);
        }
        changeUpDownButton(false, R.drawable.blocked_pad_walk_forward, R.drawable.blocked_pad_walk_backward);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void blockLeftAndRightButton() {
        if (!this.actionButtonsPressedList.isEmpty()) {
            clearButtonsPressedList(this.movementButtonsPressedList);
        }
        changeLeftRightButton(false, R.drawable.blocked_pad_moonwalker_left, R.drawable.blocked_pad_moonwalker_right);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unblockUpDownButton(boolean forced) {
        if (forced) {
            changeUpDownButton(true, R.drawable.pad_walk_forward, R.drawable.pad_walk_backward);
        } else if (!arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.bendButton.getId(), this.shakelegButton.getId(), this.crusaitoButton.getId()) && !arePressedAnyOfThisButtons(this.movementButtonsPressedList, this.upperLeftButton.getId(), this.upperRighButton.getId())) {
            changeUpDownButton(true, R.drawable.pad_walk_forward, R.drawable.pad_walk_backward);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unblockLeftRightButton(boolean forced) {
        if (forced) {
            changeLeftRightButton(true, R.drawable.pad_moonwalker_left, R.drawable.pad_moonwalker_right);
        } else if (!arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.flappingButton.getId()) && !arePressedAnyOfThisButtons(this.movementButtonsPressedList, this.upperLeftButton.getId(), this.upperRighButton.getId())) {
            changeLeftRightButton(true, R.drawable.pad_moonwalker_left, R.drawable.pad_moonwalker_right);
        }
    }

    private void changeUpDownButton(boolean enabled, int crostree_front_button, int crostree_back_button) {
        this.upButton.setEnabled(enabled);
        this.downButton.setEnabled(enabled);
        if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.bendButton.getId(), this.shakelegButton.getId(), this.crusaitoButton.getId())) {
            this.upButton.setBackgroundResource(crostree_front_button);
            this.downButton.setBackgroundResource(crostree_back_button);
            return;
        }
        if (arePressedAnyOfThisButtons(this.movementButtonsPressedList, this.upperLeftButton.getId(), this.upperRighButton.getId())) {
            this.upButton.setBackgroundResource(crostree_front_button);
            this.downButton.setBackgroundResource(crostree_back_button);
            return;
        }
        if (this.movementButtonsPressedList.isEmpty()) {
            this.upButton.setBackgroundResource(crostree_front_button);
            this.downButton.setBackgroundResource(crostree_back_button);
        } else {
            if (this.movementButtonsPressedList.size() == 1) {
                if (this.movementButtonsPressedList.contains(Integer.valueOf(this.upButton.getId()))) {
                    this.downButton.setBackgroundResource(crostree_back_button);
                    return;
                } else {
                    if (this.movementButtonsPressedList.contains(Integer.valueOf(this.downButton.getId()))) {
                        this.upButton.setBackgroundResource(crostree_front_button);
                        return;
                    }
                    return;
                }
            }
            if (this.movementButtonsPressedList.size() > 1) {
            }
        }
    }

    private void changeLeftRightButton(boolean enabled, int crostree_left_button, int crostree_right_button) {
        this.leftButton.setEnabled(enabled);
        this.rightButton.setEnabled(enabled);
        if (arePressedAnyOfThisButtons(this.actionButtonsPressedList, this.flappingButton.getId())) {
            this.leftButton.setBackgroundResource(crostree_left_button);
            this.rightButton.setBackgroundResource(crostree_right_button);
            return;
        }
        if (arePressedAnyOfThisButtons(this.movementButtonsPressedList, this.upperLeftButton.getId(), this.upperRighButton.getId())) {
            this.leftButton.setBackgroundResource(crostree_left_button);
            this.rightButton.setBackgroundResource(crostree_right_button);
            return;
        }
        if (this.movementButtonsPressedList.isEmpty()) {
            this.leftButton.setBackgroundResource(crostree_left_button);
            this.rightButton.setBackgroundResource(crostree_right_button);
        } else {
            if (this.movementButtonsPressedList.size() == 1) {
                if (this.movementButtonsPressedList.contains(Integer.valueOf(this.leftButton.getId()))) {
                    this.rightButton.setBackgroundResource(crostree_right_button);
                    return;
                } else {
                    if (this.movementButtonsPressedList.contains(Integer.valueOf(this.rightButton.getId()))) {
                        this.leftButton.setBackgroundResource(crostree_left_button);
                        return;
                    }
                    return;
                }
            }
            if (this.movementButtonsPressedList.size() > 1) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean arePressedAnyOfThisButtons(List<Integer> buttonsPressedList, int... buttonsId) {
        for (int buttonId : buttonsId) {
            if (buttonsPressedList.contains(Integer.valueOf(buttonId))) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addToButtonsPressedList(List<Integer> buttonsPressedList, int buttonId) {
        if (!buttonsPressedList.contains(Integer.valueOf(buttonId))) {
            buttonsPressedList.add(Integer.valueOf(buttonId));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFromButtonsPressedList(List<Integer> buttonsPressedList, int buttonId) {
        if (buttonsPressedList.contains(Integer.valueOf(buttonId))) {
            buttonsPressedList.remove(buttonsPressedList.indexOf(Integer.valueOf(buttonId)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearButtonsPressedList(List<Integer> buttonsPressedList) {
        buttonsPressedList.clear();
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.interactive.InteractiveBaseView
    public void showZowiName(String zowiName) {
        super.showZowiName(zowiName);
        this.howToPlayText.setText(getResources().getString(R.string.gamepad_how_to_play_text, zowiName));
    }

    @Override // com.bq.zowi.views.interactive.pad.PadView
    public void showHelp() {
        this.howToPlayLayout.setVisibility(0);
    }

    @Override // com.bq.zowi.views.interactive.pad.PadView
    public void showActionsGrid(List<GridCommand> commandList) {
        this.actionsDialog.setVisibility(0);
        this.commmandsAdapter.setItems(commandList);
        float rowHeight = getResources().getDimension(R.dimen.achievement_row_item_height);
        int columns = ResourceResolver.getIntegerByResourceId("makerbox_dialog_scrollable_columns", this);
        int numberOfRows = commandList.size() / columns;
        if (commandList.size() % columns != 0) {
            numberOfRows++;
        }
        this.commandsRecyclerView.getLayoutParams().height = (int) (numberOfRows * rowHeight);
    }

    @Override // com.bq.zowi.views.interactive.pad.PadView
    public void setUnlockStatusCrusaitoButton(boolean unlocked) {
        setClickableButton(this.crusaitoButton, unlocked, R.drawable.right_pad_zowi_crusaito_button_selector);
    }

    @Override // com.bq.zowi.views.interactive.pad.PadView
    public void setUnlockStatusFlappingButton(boolean unlocked) {
        setClickableButton(this.flappingButton, unlocked, R.drawable.right_pad_zowi_flapping_button_selector);
    }

    @Override // com.bq.zowi.views.interactive.pad.PadView
    public void setUnlockStatusShakeLegButton(boolean unlocked) {
        setClickableButton(this.shakelegButton, unlocked, R.drawable.right_pad_zowi_shake_leg_button_selector);
    }

    @Override // com.bq.zowi.views.interactive.pad.PadView
    public void setUnlockStatusJitterButton(boolean unlocked) {
        setClickableButton(this.jitterButton, unlocked, R.drawable.right_pad_zowi_jitter_button_selector);
    }

    @Override // com.bq.zowi.views.interactive.pad.PadView
    public void setUnlockStatusSwingButton(boolean unlocked) {
        setClickableButton(this.swingButton, unlocked, R.drawable.right_pad_zowi_swing_button_selector);
    }

    private void setClickableButton(Button button, boolean isClickable, int drawableId) {
        button.setClickable(isClickable);
        if (isClickable) {
            if (Build.VERSION.SDK_INT < 16) {
                button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), drawableId));
            } else {
                button.setBackground(ContextCompat.getDrawable(getApplicationContext(), drawableId));
            }
        }
    }

    @Override // com.bq.zowi.views.interactive.timeline.CommandsTileViewHolder.CommandItemListener
    public void onCommandSelected(GridCommand gridCommand) {
        if (gridCommand.isUnlocked()) {
            ((PadPresenter) getPresenter()).actionPressed(gridCommand.getCommand());
        }
    }
}
