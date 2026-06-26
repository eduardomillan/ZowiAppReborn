package com.bq.zowi.views.interactive.zowiapps.minigames;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiRunnerMinigamePresenter;
import com.bq.zowi.views.interactive.InteractiveBaseActivity;

/* JADX INFO: loaded from: classes.dex */
public class ZowiRunnerMinigameActivity extends InteractiveBaseActivity<ZowiRunnerMinigamePresenter> implements ZowiRunnerMinigameView, SensorEventListener {
    private Handler handlerTimer;
    private Button leftButton;
    private FloatingActionButton playButton;
    private Button rightButton;
    private Runnable runnableTimer;
    private int runningTime;
    private Sensor sensor;
    private SensorManager sensorManager;
    private TextView sensorValue;
    private Button stopButton;
    private TextView timerText;
    private long lastUpdate = 0;
    private final int TILT_THRESHOLD = 5;
    private final int SENSOR_UPDATE_INTERVAL_MILLIS = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    private int ONE_SECOND = 1000;

    static /* synthetic */ int access$508(ZowiRunnerMinigameActivity x0) {
        int i = x0.runningTime;
        x0.runningTime = i + 1;
        return i;
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zowi_runner_minigame_view);
        this.playButton = (FloatingActionButton) findViewById(R.id.zowi_runner_play_button);
        this.playButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.ZowiRunnerMinigameActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ZowiRunnerMinigameActivity.this.leftButton.setEnabled(true);
                ZowiRunnerMinigameActivity.this.rightButton.setEnabled(true);
                ZowiRunnerMinigameActivity.this.playButton.setVisibility(8);
                ZowiRunnerMinigameActivity.this.stopButton.setVisibility(0);
                ZowiRunnerMinigameActivity.this.timerText.setVisibility(0);
                ZowiRunnerMinigameActivity.this.runningTime = 0;
                ZowiRunnerMinigameActivity.this.handlerTimer = new Handler();
                ZowiRunnerMinigameActivity.this.runnableTimer = new Runnable() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.ZowiRunnerMinigameActivity.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ZowiRunnerMinigameActivity.access$508(ZowiRunnerMinigameActivity.this);
                        ZowiRunnerMinigameActivity.this.timerText.setText(ZowiRunnerMinigameActivity.this.runningTime + " secs");
                        ZowiRunnerMinigameActivity.this.handlerTimer.postDelayed(ZowiRunnerMinigameActivity.this.runnableTimer, ZowiRunnerMinigameActivity.this.ONE_SECOND);
                    }
                };
                ZowiRunnerMinigameActivity.this.runnableTimer.run();
                ((ZowiRunnerMinigamePresenter) ZowiRunnerMinigameActivity.this.getPresenter()).playButtonPressed();
            }
        });
        this.stopButton = (Button) findViewById(R.id.zowi_runner_stop_button);
        this.stopButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.ZowiRunnerMinigameActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ZowiRunnerMinigameActivity.this.leftButton.setEnabled(false);
                ZowiRunnerMinigameActivity.this.rightButton.setEnabled(false);
                ((ZowiRunnerMinigamePresenter) ZowiRunnerMinigameActivity.this.getPresenter()).stopButtonPressed();
                ZowiRunnerMinigameActivity.this.handlerTimer.removeCallbacks(ZowiRunnerMinigameActivity.this.runnableTimer);
            }
        });
        this.timerText = (TextView) findViewById(R.id.zowi_runner_timer_text);
        this.leftButton = (Button) findViewById(R.id.zowi_runner_left_button);
        this.leftButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.ZowiRunnerMinigameActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ZowiRunnerMinigamePresenter) ZowiRunnerMinigameActivity.this.getPresenter()).leftButtonPressed();
            }
        });
        this.rightButton = (Button) findViewById(R.id.zowi_runner_right_button);
        this.rightButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.interactive.zowiapps.minigames.ZowiRunnerMinigameActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ZowiRunnerMinigamePresenter) ZowiRunnerMinigameActivity.this.getPresenter()).rightButtonPressed();
            }
        });
        this.sensorManager = (SensorManager) getSystemService("sensor");
        this.sensor = this.sensorManager.getDefaultSensor(1);
        if (this.sensor == null) {
        }
        this.sensorValue = (TextView) findViewById(R.id.zowi_runner_accelerometer_speed_text);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public ZowiRunnerMinigamePresenter resolvePresenter() {
        ZowiRunnerMinigamePresenter presenter = AndroidDependencyInjector.getInstance().provideZowiRunnerMinigamePresenter();
        presenter.bindViewAndWireframe(this, null);
        return presenter;
    }

    @Override // com.bq.zowi.views.interactive.InteractiveBaseActivity, com.bq.zowi.views.BaseActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_ZOWI_RUNNER));
        this.sensorManager.registerListener(this, this.sensor, 1);
    }

    @Override // com.bq.zowi.views.BaseActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        this.sensorManager.unregisterListener(this);
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.lastUpdate > 200) {
            this.lastUpdate = currentTimeMillis;
            if (sensor.getType() == 1) {
                float y = sensorEvent.values[1];
                this.sensorValue.setText("A: " + y);
                if (y > 5.0f) {
                    ((ZowiRunnerMinigamePresenter) getPresenter()).tilt(Command.Direction.RIGHT);
                } else if (y < -5.0f) {
                    ((ZowiRunnerMinigamePresenter) getPresenter()).tilt(Command.Direction.LEFT);
                } else {
                    ((ZowiRunnerMinigamePresenter) getPresenter()).noTilt();
                }
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
