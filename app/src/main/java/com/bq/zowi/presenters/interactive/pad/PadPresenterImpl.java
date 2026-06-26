package com.bq.zowi.presenters.interactive.pad;

import com.bq.analytics.core.AnalyticsController;
import com.bq.analytics.hit.Event;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.controllers.AchievementsController;
import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.GameController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.commands.AnimationCommand;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.commands.ForwardBackwardCommand;
import com.bq.zowi.models.commands.GridCommand;
import com.bq.zowi.models.commands.LeftRightCommand;
import com.bq.zowi.models.commands.MouthCommand;
import com.bq.zowi.models.commands.StaticCommand;
import com.bq.zowi.models.commands.StopCommand;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.pad.PadView;
import com.bq.zowi.wireframes.pad.PadWireframe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class PadPresenterImpl extends InteractiveBasePresenterImpl<PadView, PadWireframe> implements PadPresenter {
    private static final String COMMAND_FINISHED_ACK = "F";
    private static final Achievement.Id FIRST_TIME_ACHIEVEMENT = Achievement.Id.shake_leg;
    private AchievementsController achievementsController;
    private CheckAchievementAndUnlockItInteractor achievementsInteractor;
    private AnalyticsController analyticsController;
    private long configuredDuration;
    private GameController gameController;
    private final Achievement.Id[] gamepadAchievements;
    private boolean isCommandSendingBlocked;
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;
    private Scheduler uiScheduler;

    public PadPresenterImpl(SessionController sessionController, GameController gameController, CheckAchievementAndUnlockItInteractor achievementsInteractor, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, SendCommandToZowiInteractor sendCommandToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, AchievementsController achievementsController, String factoryFirmwarePath, Scheduler uiScheduler, AnalyticsController analyticsController) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.configuredDuration = 1000L;
        this.isCommandSendingBlocked = false;
        this.gamepadAchievements = new Achievement.Id[]{Achievement.Id.crusaito, Achievement.Id.flapping, Achievement.Id.shake_leg, Achievement.Id.jitter, Achievement.Id.swing, Achievement.Id.fart, Achievement.Id.confused, Achievement.Id.in_love, Achievement.Id.angry, Achievement.Id.anxious, Achievement.Id.magic, Achievement.Id.wave};
        this.gameController = gameController;
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
        this.achievementsInteractor = achievementsInteractor;
        this.achievementsController = achievementsController;
        this.uiScheduler = uiScheduler;
        this.analyticsController = analyticsController;
    }

    @Override // com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl, com.bq.zowi.presenters.BasePresenterImpl, com.bq.zowi.presenters.BasePresenter
    public void onCreateView() {
        super.onCreateView();
        configureEnabledActions();
        startListeningCommandFinishedAck();
    }

    @Override // com.bq.zowi.presenters.BasePresenterImpl, com.bq.zowi.presenters.BasePresenter
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void gameReady() {
        this.subscriptions.add(Single.zip(this.gameController.isFirstPlay(GameController.GAME_ID.GAMEPAD_GAME_ID, true), this.gameController.isFirstPlay(GameController.GAME_ID.TIMELINE_GAME_ID, false), new Func2<Boolean, Boolean, HashMap<GameController.GAME_ID, Boolean>>() { // from class: com.bq.zowi.presenters.interactive.pad.PadPresenterImpl.3
            @Override // rx.functions.Func2
            public HashMap<GameController.GAME_ID, Boolean> call(Boolean isGamePadFirstTimePlayed, Boolean isTimelineFirstTimePlayed) {
                HashMap<GameController.GAME_ID, Boolean> gamesStatus = new HashMap<>();
                gamesStatus.put(GameController.GAME_ID.GAMEPAD_GAME_ID, isGamePadFirstTimePlayed);
                gamesStatus.put(GameController.GAME_ID.TIMELINE_GAME_ID, isTimelineFirstTimePlayed);
                return gamesStatus;
            }
        }).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).flatMap(new Func1<HashMap<GameController.GAME_ID, Boolean>, Single<Achievement>>() { // from class: com.bq.zowi.presenters.interactive.pad.PadPresenterImpl.2
            @Override // rx.functions.Func1
            public Single<Achievement> call(HashMap<GameController.GAME_ID, Boolean> gamesStatus) {
                if (gamesStatus.get(GameController.GAME_ID.GAMEPAD_GAME_ID).booleanValue()) {
                    ((PadView) PadPresenterImpl.this.getView()).showHelp();
                    if (!gamesStatus.get(GameController.GAME_ID.TIMELINE_GAME_ID).booleanValue()) {
                        return PadPresenterImpl.this.achievementsInteractor.checkAchievementAndUnlockIt(PadPresenterImpl.FIRST_TIME_ACHIEVEMENT);
                    }
                }
                return Single.just(null);
            }
        }).subscribe(new SingleSubscriber<Achievement>() { // from class: com.bq.zowi.presenters.interactive.pad.PadPresenterImpl.1
            @Override // rx.SingleSubscriber
            public void onSuccess(Achievement achievement) {
                if (achievement != null) {
                    PadPresenterImpl.this.configureEnabledActions();
                    ((PadView) PadPresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                }
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.d(error.getMessage(), new Object[0]);
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void homeButtonPressed() {
        ((PadWireframe) getWireframe()).presentHome();
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void helpButtonPressed() {
        ((PadView) getView()).showHelp();
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void configureDuration(String duration) {
        this.configuredDuration = Long.valueOf(duration).longValue();
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void upArrowPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.WALK.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_FORWARD_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void downArrowPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new ForwardBackwardCommand(Command.Action.WALK, Command.Direction.BACKWARD, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.WALK.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_BACKWARD_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void rightArrowPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.TURN, Command.Direction.RIGHT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.TURN.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_RIGHT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void leftArrowPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.TURN, Command.Direction.LEFT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.TURN.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_LEFT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void crusaitoLeft() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.CRUSAITO, Command.Direction.LEFT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.CRUSAITO.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_LEFT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void crusaitoRight() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.CRUSAITO, Command.Direction.RIGHT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.CRUSAITO.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_RIGHT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void moonwalkerLeft() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.LEFT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.MOONWALKER.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_LEFT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void moonwalkerRight() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.RIGHT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.MOONWALKER.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_RIGHT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void flappingForward() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new ForwardBackwardCommand(Command.Action.FLAPPING, Command.Direction.FORWARD, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.FLAPPING.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_FORWARD_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void flappingBackward() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new ForwardBackwardCommand(Command.Action.FLAPPING, Command.Direction.BACKWARD, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.FLAPPING.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_BACKWARD_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void bendLeft() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.BEND, Command.Direction.LEFT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.BEND.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_LEFT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void bendRight() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.BEND, Command.Direction.RIGHT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.BEND.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_RIGHT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void shakeLegLeft() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.SHAKE_LEG, Command.Direction.LEFT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.SHAKE_LEG.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_LEFT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void shakeLegRight() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new LeftRightCommand(Command.Action.SHAKE_LEG, Command.Direction.RIGHT, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.SHAKE_LEG.getId() + AnalyticsUtils.EVENT_GAMEPAD_MOVE_RIGHT_SUFFIX, 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void updownPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new StaticCommand(Command.Action.UPDOWN, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.UPDOWN.getId(), 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void jumpPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new StaticCommand(Command.Action.JUMP, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.JUMP.getId(), 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void tipToeSwingPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new StaticCommand(Command.Action.TIP_TOE, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.TIP_TOE.getId(), 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void jitterPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new StaticCommand(Command.Action.JITTER, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.JITTER.getId(), 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void ascendingTurnPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new StaticCommand(Command.Action.ASCENDING_TURN, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.ASCENDING_TURN.getId(), 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void swingPressed() {
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new StaticCommand(Command.Action.SWING, this.configuredDuration)).subscribe(new CommandSingleSubscriber());
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, "move", Command.Action.SWING.getId(), 0L));
        }
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void actionButtonReleased() {
        this.sendCommandToZowiInteractor.sendCommandToZowi(new StopCommand()).subscribe(new CommandSingleSubscriber());
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void animationsPressed() {
        final List<GridCommand> animationCommands = Arrays.asList(new GridCommand(new AnimationCommand(Command.Action.HAPPY)), new GridCommand(new AnimationCommand(Command.Action.SUPER_HAPPY)), new GridCommand(new AnimationCommand(Command.Action.SAD)), new GridCommand(new AnimationCommand(Command.Action.SLEEPY)), new GridCommand(new AnimationCommand(Command.Action.FART)), new GridCommand(new AnimationCommand(Command.Action.CONFUSED)), new GridCommand(new AnimationCommand(Command.Action.IN_LOVE)), new GridCommand(new AnimationCommand(Command.Action.ANGRY)), new GridCommand(new AnimationCommand(Command.Action.ANXIOUS)), new GridCommand(new AnimationCommand(Command.Action.MAGIC)), new GridCommand(new AnimationCommand(Command.Action.WAVE)));
        this.subscriptions.add(this.achievementsController.getAchievementsList().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<ArrayList<Achievement>>() { // from class: com.bq.zowi.presenters.interactive.pad.PadPresenterImpl.4
            @Override // rx.SingleSubscriber
            public void onSuccess(ArrayList<Achievement> achievementsList) {
                for (Achievement achievement : achievementsList) {
                    for (GridCommand gridCommand : animationCommands) {
                        if (achievement.id.equals(gridCommand.getCommandId())) {
                            gridCommand.setUnlocked(achievement.unlocked);
                        }
                    }
                }
                ((PadView) PadPresenterImpl.this.getView()).showActionsGrid(animationCommands);
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void mouthsPressed() {
        List<GridCommand> mouthCommands = Arrays.asList(new GridCommand(new MouthCommand(Command.Action.MOUTH_SMILE)), new GridCommand(new MouthCommand(Command.Action.MOUTH_SAD)), new GridCommand(new MouthCommand(Command.Action.MOUTH_CONFUSED)), new GridCommand(new MouthCommand(Command.Action.MOUTH_BIG_SURPRISE)), new GridCommand(new MouthCommand(Command.Action.MOUTH_SMALL_SURPRISE)), new GridCommand(new MouthCommand(Command.Action.MOUTH_HAPPY_OPEN)), new GridCommand(new MouthCommand(Command.Action.MOUTH_SAD_OPEN)), new GridCommand(new MouthCommand(Command.Action.MOUTH_SAD_CLOSED)), new GridCommand(new MouthCommand(Command.Action.MOUTH_HEART)), new GridCommand(new MouthCommand(Command.Action.MOUTH_THUNDER)), new GridCommand(new MouthCommand(Command.Action.MOUTH_X)), new GridCommand(new MouthCommand(Command.Action.MOUTH_INTERROGATION)), new GridCommand(new MouthCommand(Command.Action.MOUTH_TONGUE_OUT)), new GridCommand(new MouthCommand(Command.Action.MOUTH_DIAGONAL)), new GridCommand(new MouthCommand(Command.Action.MOUTH_ANGRY)), new GridCommand(new MouthCommand(Command.Action.MOUTH_CULITO)), new GridCommand(new MouthCommand(Command.Action.MOUTH_OK)), new GridCommand(new MouthCommand(Command.Action.MOUTH_LINE)), new GridCommand(new MouthCommand(Command.Action.MOUTH_VAMP1)), new GridCommand(new MouthCommand(Command.Action.MOUTH_VAMP2)));
        ((PadView) getView()).showActionsGrid(mouthCommands);
    }

    @Override // com.bq.zowi.presenters.interactive.pad.PadPresenter
    public void actionPressed(Command command) {
        String commandType;
        if (!isCommandSendingBlocked()) {
            this.sendCommandToZowiInteractor.sendCommandToZowi(command).subscribe(new CommandSingleSubscriber());
            if (command instanceof MouthCommand) {
                commandType = "mouth";
            } else {
                commandType = command instanceof AnimationCommand ? "animation" : "move";
            }
            this.analyticsController.send(new Event(AnalyticsUtils.EVENT_GAMEPAD, commandType, command.getId(), 0L));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configureEnabledActions() {
        this.subscriptions.add(this.achievementsController.getAchievementsList().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<ArrayList<Achievement>>() { // from class: com.bq.zowi.presenters.interactive.pad.PadPresenterImpl.5
            @Override // rx.SingleSubscriber
            public void onSuccess(ArrayList<Achievement> achievementsList) {
                for (int i = 0; i < PadPresenterImpl.this.gamepadAchievements.length; i++) {
                    for (Achievement achievement : achievementsList) {
                        if (achievement.id.equals(PadPresenterImpl.this.gamepadAchievements[i].toString())) {
                            switch (AnonymousClass8.$SwitchMap$com$bq$zowi$models$Achievement$Id[PadPresenterImpl.this.gamepadAchievements[i].ordinal()]) {
                                case 1:
                                    ((PadView) PadPresenterImpl.this.getView()).setUnlockStatusCrusaitoButton(achievement.unlocked);
                                    break;
                                case 2:
                                    ((PadView) PadPresenterImpl.this.getView()).setUnlockStatusFlappingButton(achievement.unlocked);
                                    break;
                                case 3:
                                    ((PadView) PadPresenterImpl.this.getView()).setUnlockStatusShakeLegButton(achievement.unlocked);
                                    break;
                                case 4:
                                    ((PadView) PadPresenterImpl.this.getView()).setUnlockStatusJitterButton(achievement.unlocked);
                                    break;
                                case 5:
                                    ((PadView) PadPresenterImpl.this.getView()).setUnlockStatusSwingButton(achievement.unlocked);
                                    break;
                            }
                        }
                    }
                }
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.e(error, "CONFIGURE ENABLED ACTIONS ERROR!!!", new Object[0]);
            }
        }));
    }

    /* JADX INFO: renamed from: com.bq.zowi.presenters.interactive.pad.PadPresenterImpl$8, reason: invalid class name */
    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$bq$zowi$models$Achievement$Id = new int[Achievement.Id.values().length];

        static {
            try {
                $SwitchMap$com$bq$zowi$models$Achievement$Id[Achievement.Id.crusaito.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$bq$zowi$models$Achievement$Id[Achievement.Id.flapping.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$bq$zowi$models$Achievement$Id[Achievement.Id.shake_leg.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$bq$zowi$models$Achievement$Id[Achievement.Id.jitter.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$bq$zowi$models$Achievement$Id[Achievement.Id.swing.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private boolean isCommandSendingBlocked() {
        if (this.isCommandSendingBlocked) {
            return true;
        }
        this.isCommandSendingBlocked = true;
        return false;
    }

    private void startListeningCommandFinishedAck() {
        this.subscriptions.add(this.connectionController.getReceivedMessageObservable().subscribeOn(Schedulers.io()).filter(new Func1<String, Boolean>() { // from class: com.bq.zowi.presenters.interactive.pad.PadPresenterImpl.7
            @Override // rx.functions.Func1
            public Boolean call(String s) {
                return Boolean.valueOf(s.equals(PadPresenterImpl.COMMAND_FINISHED_ACK));
            }
        }).subscribe((Subscriber<? super String>) new Subscriber<String>() { // from class: com.bq.zowi.presenters.interactive.pad.PadPresenterImpl.6
            @Override // rx.Observer
            public void onCompleted() {
            }

            @Override // rx.Observer
            public void onError(Throwable e) {
            }

            @Override // rx.Observer
            public void onNext(String s) {
                PadPresenterImpl.this.isCommandSendingBlocked = false;
            }
        }));
    }
}
