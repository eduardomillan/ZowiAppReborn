package com.bq.zowi.presenters.interactive.timeline;

import com.bq.zowi.api.AchievementsController;
import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.GameController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.commands.AnimationCommand;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.commands.ForwardBackwardCommand;
import com.bq.zowi.models.commands.GridCommand;
import com.bq.zowi.models.commands.LeftRightCommand;
import com.bq.zowi.models.commands.MouthCommand;
import com.bq.zowi.models.commands.MovementCommand;
import com.bq.zowi.models.commands.StaticCommand;
import com.bq.zowi.models.commands.StopCommand;
import com.bq.zowi.models.commands.TimelineCommand;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.usecases.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.usecases.SendCommandToZowiInteractor;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.timeline.TimelineView;
import com.bq.zowi.wireframes.timeline.TimelineWireframe;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TimelinePresenterImpl extends InteractiveBasePresenterImpl<TimelineView, TimelineWireframe> implements TimelinePresenter {
    private static final String COMMAND_ACK = "A";
    private static final Achievement.Id FIRST_TIME_ACHIEVEMENT = Achievement.Id.shake_leg;
    private static final Achievement.Id TIMELINE_ACHIEVEMENT = Achievement.Id.anxious;
    private static final int TIMELINE_ACHIEVEMENT_MIN_COMMANDS = 15;
    private AchievementsController achievementsController;
    private final CheckAchievementAndUnlockItInteractor achievementsInteractor;
    private final BTConnectionController connectionController;
    private final GameController gameController;
    private final SendCommandToZowiInteractor sendCommandToZowiInteractor;
    private Disposable subscriberDisposable;
    private TimelineCommandSubscriber subscriber;
    private final Scheduler uiScheduler;

    public TimelinePresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, GameController gameController, SendCommandToZowiInteractor sendCommandToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, String factoryFirmwarePath, CheckAchievementAndUnlockItInteractor achievementsInteractor, AchievementsController achievementsController, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.connectionController = connectionController;
        this.gameController = gameController;
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
        this.achievementsInteractor = achievementsInteractor;
        this.achievementsController = achievementsController;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void gameReady() {
        this.disposables.add(Single.zip(this.gameController.isFirstPlay(GameController.GAME_ID.GAMEPAD_GAME_ID, false), this.gameController.isFirstPlay(GameController.GAME_ID.TIMELINE_GAME_ID, true), new BiFunction<Boolean, Boolean, HashMap<GameController.GAME_ID, Boolean>>() {
            @Override
            public HashMap<GameController.GAME_ID, Boolean> apply(Boolean isGamePadFirstTimePlayed, Boolean isTimelineFirstTimePlayed) {
                HashMap<GameController.GAME_ID, Boolean> gamesStatus = new HashMap<>();
                gamesStatus.put(GameController.GAME_ID.GAMEPAD_GAME_ID, isGamePadFirstTimePlayed);
                gamesStatus.put(GameController.GAME_ID.TIMELINE_GAME_ID, isTimelineFirstTimePlayed);
                return gamesStatus;
            }
        }).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).flatMap(new Function<HashMap<GameController.GAME_ID, Boolean>, Single<Achievement>>() {
            @Override
            public Single<Achievement> apply(HashMap<GameController.GAME_ID, Boolean> gamesStatus) {
                if (gamesStatus.get(GameController.GAME_ID.TIMELINE_GAME_ID).booleanValue()) {
                    ((TimelineView) TimelinePresenterImpl.this.getView()).showHelp();
                    if (!gamesStatus.get(GameController.GAME_ID.GAMEPAD_GAME_ID).booleanValue()) {
                        return TimelinePresenterImpl.this.achievementsInteractor.checkAchievementAndUnlockIt(TimelinePresenterImpl.FIRST_TIME_ACHIEVEMENT);
                    }
                }
                return Single.just(null);
            }
        }).subscribe(
            achievement -> {
                if (achievement != null) {
                    ((TimelineView) TimelinePresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                }
            },
            error -> Grove.d(error.getMessage(), new Object[0])
        ));
    }

    @Override
    public void playTimelineButtonPressed(List<TimelineCommand> timelineCommandList) {
        this.subscriber = new TimelineCommandSubscriber(timelineCommandList);
        this.connectionController.getReceivedMessageObservable().subscribeOn(Schedulers.io()).filter(s -> s.equals(COMMAND_ACK)).subscribe(this.subscriber);
        this.subscriberDisposable = this.subscriber;
        this.disposables.add(this.subscriberDisposable);
        this.subscriber.initialize();
    }

    @Override
    public void stopTimelineButtonPressed() {
        if (this.subscriber != null && !this.subscriberDisposable.isDisposed()) {
            this.subscriberDisposable.dispose();
        }
        this.sendCommandToZowiInteractor.sendCommandToZowi(new StopCommand()).subscribeOn(Schedulers.io()).subscribe();
        ((TimelineView) getView()).showTimelineStoppedPlaying();
    }

    @Override
    public void saveTimeline(List<TimelineCommand> timelineCommandList) {
        this.disposables.add(this.gameController.saveProgress(GameController.GAME_ID.TIMELINE_GAME_ID, timelineCommandList).subscribeOn(Schedulers.io()).subscribe());
    }

    @Override
    public void loadAndResumeTimeline() {
        this.disposables.add(this.gameController.<List<TimelineCommand>>loadProgress(GameController.GAME_ID.TIMELINE_GAME_ID, new TypeToken<List<TimelineCommand>>() {
        }.getType()).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            loadedTimelineCommandList -> {
                if (loadedTimelineCommandList != null) {
                    ((TimelineView) TimelinePresenterImpl.this.getView()).addTimelineCommandsToTimeline(loadedTimelineCommandList);
                }
            },
            error -> Grove.e(error, "LOADING SAVED TIMELINE ERROR!!!", new Object[0])
        ));
    }

    @Override
    public void addNewMovementCommandButtonClicked() {
        final List<GridCommand> movementCommands = Arrays.asList(new GridCommand(new ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new LeftRightCommand(Command.Action.TURN, Command.Direction.LEFT, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new StaticCommand(Command.Action.UPDOWN, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.LEFT, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new StaticCommand(Command.Action.SWING, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new LeftRightCommand(Command.Action.CRUSAITO, Command.Direction.LEFT, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new ForwardBackwardCommand(Command.Action.FLAPPING, Command.Direction.FORWARD, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new StaticCommand(Command.Action.TIP_TOE, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new LeftRightCommand(Command.Action.BEND, Command.Direction.LEFT, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new LeftRightCommand(Command.Action.SHAKE_LEG, Command.Direction.LEFT, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new StaticCommand(Command.Action.JITTER, MovementCommand.ALLOWED_DURATIONS[1].longValue())), new GridCommand(new StaticCommand(Command.Action.ASCENDING_TURN, MovementCommand.ALLOWED_DURATIONS[1].longValue())));
        this.disposables.add(this.achievementsController.getAchievementsList().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            achievementsList -> {
                for (Achievement achievement : achievementsList) {
                    for (GridCommand gridCommand : movementCommands) {
                        if (achievement.id.equals(gridCommand.getCommandId())) {
                            gridCommand.setUnlocked(achievement.unlocked);
                        }
                    }
                }
                ((TimelineView) TimelinePresenterImpl.this.getView()).showCommandsSelector(movementCommands);
            },
            error -> { }
        ));
    }

    @Override
    public void addNewAnimationCommandButtonClicked() {
        final List<GridCommand> animationCommands = Arrays.asList(new GridCommand(new AnimationCommand(Command.Action.HAPPY)), new GridCommand(new AnimationCommand(Command.Action.SUPER_HAPPY)), new GridCommand(new AnimationCommand(Command.Action.SAD)), new GridCommand(new AnimationCommand(Command.Action.SLEEPY)), new GridCommand(new AnimationCommand(Command.Action.FART)), new GridCommand(new AnimationCommand(Command.Action.CONFUSED)), new GridCommand(new AnimationCommand(Command.Action.IN_LOVE)), new GridCommand(new AnimationCommand(Command.Action.ANGRY)), new GridCommand(new AnimationCommand(Command.Action.ANXIOUS)), new GridCommand(new AnimationCommand(Command.Action.MAGIC)), new GridCommand(new AnimationCommand(Command.Action.WAVE)));
        this.disposables.add(this.achievementsController.getAchievementsList().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            achievementsList -> {
                for (Achievement achievement : achievementsList) {
                    for (GridCommand gridCommand : animationCommands) {
                        if (achievement.id.equals(gridCommand.getCommandId())) {
                            gridCommand.setUnlocked(achievement.unlocked);
                        }
                    }
                }
                ((TimelineView) TimelinePresenterImpl.this.getView()).showCommandsSelector(animationCommands);
            },
            error -> { }
        ));
    }

    @Override
    public void addNewMouthCommandButtonClicked() {
        List<GridCommand> mouthCommands = Arrays.asList(new GridCommand(new MouthCommand(Command.Action.MOUTH_SMILE)), new GridCommand(new MouthCommand(Command.Action.MOUTH_SAD)), new GridCommand(new MouthCommand(Command.Action.MOUTH_CONFUSED)), new GridCommand(new MouthCommand(Command.Action.MOUTH_BIG_SURPRISE)), new GridCommand(new MouthCommand(Command.Action.MOUTH_SMALL_SURPRISE)), new GridCommand(new MouthCommand(Command.Action.MOUTH_HAPPY_OPEN)), new GridCommand(new MouthCommand(Command.Action.MOUTH_SAD_OPEN)), new GridCommand(new MouthCommand(Command.Action.MOUTH_SAD_CLOSED)), new GridCommand(new MouthCommand(Command.Action.MOUTH_HEART)), new GridCommand(new MouthCommand(Command.Action.MOUTH_THUNDER)), new GridCommand(new MouthCommand(Command.Action.MOUTH_X)), new GridCommand(new MouthCommand(Command.Action.MOUTH_INTERROGATION)), new GridCommand(new MouthCommand(Command.Action.MOUTH_TONGUE_OUT)), new GridCommand(new MouthCommand(Command.Action.MOUTH_DIAGONAL)), new GridCommand(new MouthCommand(Command.Action.MOUTH_ANGRY)), new GridCommand(new MouthCommand(Command.Action.MOUTH_CULITO)), new GridCommand(new MouthCommand(Command.Action.MOUTH_OK)), new GridCommand(new MouthCommand(Command.Action.MOUTH_LINE)), new GridCommand(new MouthCommand(Command.Action.MOUTH_VAMP1)), new GridCommand(new MouthCommand(Command.Action.MOUTH_VAMP2)));
        ((TimelineView) getView()).showCommandsSelector(mouthCommands);
    }

    @Override
    public void timelineCommandSelected(GridCommand gridCommand) {
        ((TimelineView) getView()).hideCommandsSelector();
        Command command = gridCommand.getCommand();
        ((TimelineView) getView()).addTimelineCommandToTimeline(new TimelineCommand(command, 1));
    }

    @Override
    public void helpButtonPressed() {
        ((TimelineView) getView()).showHelp();
    }

    @Override
    public void homeButtonPressed() {
        ((TimelineWireframe) getWireframe()).presentHome();
    }

    private class TimelineCommandSubscriber extends DisposableObserver<String> {
        TimelineCommand lastPlayedCommand;
        final List<TimelineCommand> timeline;
        final int timelineCommandsCount;

        TimelineCommandSubscriber(List<TimelineCommand> timelineCommands) {
            this.timelineCommandsCount = timelineCommands.size();
            this.timeline = TimelinePresenterImpl.flatTimelineCommands(timelineCommands);
        }

        void initialize() {
            TimelineCommand initialCommand = this.timeline.remove(0);
            this.lastPlayedCommand = initialCommand;
            TimelinePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(initialCommand.getCommand()).subscribeOn(Schedulers.io()).subscribe();
        }

        @Override
        public void onComplete() {
            dispose();
            if (this.timelineCommandsCount >= 15) {
                TimelinePresenterImpl.this.achievementsInteractor.checkAchievementAndUnlockIt(TimelinePresenterImpl.TIMELINE_ACHIEVEMENT).subscribeOn(Schedulers.io()).observeOn(TimelinePresenterImpl.this.uiScheduler).subscribe(new Consumer<Achievement>() {
                    @Override
                    public void accept(Achievement achievement) {
                        if (achievement != null) {
                            ((TimelineView) TimelinePresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                        }
                    }
                });
            }
            ((TimelineView) TimelinePresenterImpl.this.getView()).showTimelineStoppedPlaying();
        }

        @Override
        public void onError(Throwable error) {
            try {
                ((TimelineView) TimelinePresenterImpl.this.getView()).showTimelineStoppedPlaying();
            } catch (Exception e) {
                Grove.d("Send COMMAND to Zowi ERROR. View could not be updated! " + error.toString(), new Object[0]);
            }
            Grove.d("Send COMMAND to Zowi ERROR! " + error.toString(), new Object[0]);
            error.printStackTrace();
        }

        @Override
        public void onNext(String s) {
            ((TimelineView) TimelinePresenterImpl.this.getView()).showCommandIsBeingPlayed(this.lastPlayedCommand);
            if (this.timeline.size() > 0) {
                TimelineCommand nextCommand = this.timeline.remove(0);
                this.lastPlayedCommand = nextCommand;
                TimelinePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(nextCommand.getCommand()).subscribeOn(Schedulers.io()).subscribe();
                return;
            }
            onComplete();
        }
    }

    public static List<TimelineCommand> flatTimelineCommands(List<TimelineCommand> timelineCommands) {
        List<TimelineCommand> flatTimeline = new ArrayList<>(timelineCommands.size());
        for (TimelineCommand timelineCommand : timelineCommands) {
            for (int i = 0; i < timelineCommand.getRepetitions(); i++) {
                flatTimeline.add(timelineCommand);
            }
        }
        flatTimeline.add(new TimelineCommand(new StopCommand(), 1));
        return flatTimeline;
    }
}
