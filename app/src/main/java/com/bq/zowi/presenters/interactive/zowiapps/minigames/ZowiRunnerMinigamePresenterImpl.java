package com.bq.zowi.presenters.interactive.zowiapps.minigames;

import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.commands.ForwardRunnerCommand;
import com.bq.zowi.models.commands.LeftRightRunnerCommand;
import com.bq.zowi.models.commands.StopCommand;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.usecases.SendCommandToZowiInteractor;
import com.bq.zowi.views.interactive.zowiapps.minigames.ZowiRunnerMinigameView;
import com.bq.zowi.wireframes.interactive.InteractiveWireframe;
import io.reactivex.Scheduler;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ZowiRunnerMinigamePresenterImpl extends InteractiveBasePresenterImpl<ZowiRunnerMinigameView, InteractiveWireframe> implements ZowiRunnerMinigamePresenter {
    private final long DEFAULT_DURATION;
    private final double LINEAR_FUNCTION_SLOPE;
    private final long MAX_INTERVAL_UNTIL_STOP;
    private final long MIN_DURATION;
    private BTConnectionController connectionController;
    private long currentMovementDuration;
    private boolean isStopped;
    private boolean isTilt;
    private long leftButtonPressedTimestamp;
    private long rightButtonPressedTimestamp;
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;
    private Timer timer;

    public ZowiRunnerMinigamePresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, SendCommandToZowiInteractor sendCommandToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, String factoryFirmwarePath, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.leftButtonPressedTimestamp = 0L;
        this.rightButtonPressedTimestamp = 0L;
        this.currentMovementDuration = 0L;
        this.LINEAR_FUNCTION_SLOPE = 1.6d;
        this.DEFAULT_DURATION = 1500L;
        this.MIN_DURATION = 500L;
        this.MAX_INTERVAL_UNTIL_STOP = 2000L;
        this.isTilt = false;
        this.isStopped = false;
        this.connectionController = connectionController;
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
    }

    @Override
    public void playButtonPressed() {
        this.timer = new Timer(true);
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if ((currentTime - ZowiRunnerMinigamePresenterImpl.this.leftButtonPressedTimestamp > 2000 || currentTime - ZowiRunnerMinigamePresenterImpl.this.rightButtonPressedTimestamp > 2000) && !ZowiRunnerMinigamePresenterImpl.this.isStopped) {
                    ZowiRunnerMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new StopCommand()).subscribe(new CommandSingleSubscriber());
                    ZowiRunnerMinigamePresenterImpl.this.isStopped = true;
                }
            }
        }, 2000L, 2000L);
    }

    @Override
    public void stopButtonPressed() {
        this.sendCommandToZowiInteractor.sendCommandToZowi(new StopCommand()).subscribe(new CommandSingleSubscriber());
        this.isStopped = true;
        this.timer.cancel();
    }

    @Override
    public void leftButtonPressed() {
        this.leftButtonPressedTimestamp = Calendar.getInstance().getTimeInMillis();
        updateDuration();
    }

    @Override
    public void rightButtonPressed() {
        this.rightButtonPressedTimestamp = Calendar.getInstance().getTimeInMillis();
        updateDuration();
    }

    @Override
    public void tilt(Command.Direction direction) {
        if (this.currentMovementDuration != 0 && !this.isStopped) {
            this.isTilt = true;
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightRunnerCommand(Command.Action.TURN, direction, this.currentMovementDuration)).subscribe(new CommandSingleSubscriber());
            this.isStopped = false;
        }
    }

    @Override
    public void noTilt() {
        this.isTilt = false;
    }

    private void updateDuration() {
        if (this.leftButtonPressedTimestamp != 0 && this.rightButtonPressedTimestamp != 0) {
            long interval = Math.abs(this.leftButtonPressedTimestamp - this.rightButtonPressedTimestamp);
            this.currentMovementDuration = Math.round(500.0d + (interval * 1.6d));
            if (this.currentMovementDuration > 1500) {
                this.currentMovementDuration = 1500L;
            }
            sendWalkForwardCommand();
        }
    }

    private void sendWalkForwardCommand() {
        if (!this.isTilt) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new ForwardRunnerCommand(Command.Action.WALK, Command.Direction.FORWARD, this.currentMovementDuration)).subscribe(new CommandSingleSubscriber());
            this.isStopped = false;
        }
    }
}
