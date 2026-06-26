package com.bq.zowi.presenters.interactive.zowiapps.minigames;

import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.GameController;
import com.bq.zowi.controllers.RankingController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.RankingEntry;
import com.bq.zowi.models.commands.AnimationCommand;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.commands.ForwardBackwardCommand;
import com.bq.zowi.models.commands.LeftRightCommand;
import com.bq.zowi.models.commands.StaticCommand;
import com.bq.zowi.models.commands.StopCommand;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.models.viewmodels.RankingEntryViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameView;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframe;
import java.util.ArrayList;
import java.util.Random;
import rx.Scheduler;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class ZowiSaysMinigamePresenterImpl extends InteractiveBasePresenterImpl<ZowiSaysMinigameView, MinigameBaseWireframe> implements ZowiSaysMinigamePresenter {
    private static final String COMMAND_ACK = "A";
    private final int MIN_SCORE_TO_RANK;
    private final int SCORE_TO_OVERCOME_FOR_ACHIEVEMENTS;
    ArrayList<Command> availableCommandList;
    private CheckAchievementAndUnlockItInteractor checkAchievementAndUnlockItInteractor;
    private long configuredDuration;
    private BTConnectionController connectionController;
    private final Achievement.Id gameAchievement;
    private GameController gameController;
    private boolean isPlaying;
    private RankingController rankingController;
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;
    private SessionController sessionController;
    private Scheduler uiScheduler;
    private ArrayList<Command> userCommandSequence;
    private ArrayList<Command> zowiCommandSequence;

    public ZowiSaysMinigamePresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, SendCommandToZowiInteractor sendCommandToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, RankingController rankingController, GameController gameController, CheckAchievementAndUnlockItInteractor checkAchievementAndUnlockItInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, String factoryFirmwarePath, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.isPlaying = false;
        this.configuredDuration = 1000L;
        this.SCORE_TO_OVERCOME_FOR_ACHIEVEMENTS = 12;
        this.gameAchievement = Achievement.Id.in_love;
        this.MIN_SCORE_TO_RANK = 3;
        this.connectionController = connectionController;
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
        this.sessionController = sessionController;
        this.rankingController = rankingController;
        this.gameController = gameController;
        this.checkAchievementAndUnlockItInteractor = checkAchievementAndUnlockItInteractor;
        this.uiScheduler = uiScheduler;
        this.userCommandSequence = new ArrayList<>();
        this.zowiCommandSequence = new ArrayList<>();
    }

    @Override // com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl, com.bq.zowi.presenters.BasePresenterImpl, com.bq.zowi.presenters.BasePresenter
    public void onCreateView() {
        super.onCreateView();
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter
    public void gameReady() {
        this.subscriptions.add(this.gameController.isFirstPlay(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, true).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Boolean>() { // from class: com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenterImpl.1
            @Override // rx.SingleSubscriber
            public void onSuccess(Boolean isFirstTimePlayed) {
                if (isFirstTimePlayed.booleanValue()) {
                    ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showHelp();
                }
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.d(error.getMessage(), new Object[0]);
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter
    public void homeButtonPressed() {
        ((MinigameBaseWireframe) getWireframe()).presentHome();
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter
    public void helpButtonPressed() {
        ((ZowiSaysMinigameView) getView()).showHelp();
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter
    public void rankingButtonPressed() {
        this.subscriptions.add(this.rankingController.getRanking(GameController.GAME_ID.ZOWI_SAYS_GAME_ID).subscribeOn(Schedulers.io()).map(new Func1<ArrayList<RankingEntry>, ArrayList<RankingEntryViewModel>>() { // from class: com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenterImpl.3
            @Override // rx.functions.Func1
            public ArrayList<RankingEntryViewModel> call(ArrayList<RankingEntry> rankingEntries) {
                ArrayList<RankingEntryViewModel> viewModelsRanking = new ArrayList<>();
                int position = 1;
                for (RankingEntry rankingEntry : rankingEntries) {
                    RankingEntryViewModel rankingEntryViewModel = RankingEntryViewModel.rankingEntryViewModelFromRankingEntry(rankingEntry);
                    rankingEntryViewModel.setPosition(position);
                    viewModelsRanking.add(rankingEntryViewModel);
                    position++;
                }
                return viewModelsRanking;
            }
        }).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<ArrayList<RankingEntryViewModel>>() { // from class: com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenterImpl.2
            @Override // rx.SingleSubscriber
            public void onSuccess(ArrayList<RankingEntryViewModel> rankingEntryViewModels) {
                ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showRanking(rankingEntryViewModels);
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.e(error, "RETRIEVING RANKING ERROR!!!", new Object[0]);
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter
    public void playButtonPressed() {
        this.isPlaying = true;
        ((ZowiSaysMinigameView) getView()).showProgress();
        this.availableCommandList = new ArrayList<>();
        this.availableCommandList.add(new ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, this.configuredDuration));
        this.availableCommandList.add(new LeftRightCommand(Command.Action.BEND, Command.Direction.RIGHT, this.configuredDuration));
        this.availableCommandList.add(new StaticCommand(Command.Action.JUMP, this.configuredDuration));
        this.availableCommandList.add(new LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.RIGHT, this.configuredDuration));
        this.userCommandSequence.clear();
        this.zowiCommandSequence.clear();
        executeZowiCommandSequence();
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenter
    public void topLeftActionPressed() {
        if (this.isPlaying) {
            this.userCommandSequence.add(new ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, this.configuredDuration));
            checkCurrentUserSequece();
        }
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenter
    public void topRightActionPressed() {
        if (this.isPlaying) {
            this.userCommandSequence.add(new LeftRightCommand(Command.Action.BEND, Command.Direction.RIGHT, this.configuredDuration));
            checkCurrentUserSequece();
        }
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenter
    public void bottomLeftActionPressed() {
        if (this.isPlaying) {
            this.userCommandSequence.add(new StaticCommand(Command.Action.JUMP, this.configuredDuration));
            checkCurrentUserSequece();
        }
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenter
    public void bottomRightActionPressed() {
        if (this.isPlaying) {
            this.userCommandSequence.add(new LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.RIGHT, this.configuredDuration));
            checkCurrentUserSequece();
        }
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter
    public void achievementContinueButtonClicked() {
        this.subscriptions.add(this.rankingController.isScoreInTop10(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, this.zowiCommandSequence.size()).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Boolean>() { // from class: com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenterImpl.4
            @Override // rx.SingleSubscriber
            public void onSuccess(Boolean isScoreInTop10) {
                ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showPoinsEarned(ZowiSaysMinigamePresenterImpl.this.zowiCommandSequence.size() - 1, ZowiSaysMinigamePresenterImpl.this.zowiCommandSequence.size() > 3 && isScoreInTop10.booleanValue());
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.e(error, "CHECKING RANKING TOP 10 ERROR!!!", new Object[0]);
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter
    public void playerNameEnteredForRanking(String playerName) {
        RankingEntry newRankingEntry = new RankingEntry(this.zowiCommandSequence.size() - 1, playerName);
        this.subscriptions.add(this.rankingController.saveRankingEntry(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, newRankingEntry).subscribeOn(Schedulers.io()).map(new Func1<ArrayList<RankingEntry>, ArrayList<RankingEntryViewModel>>() { // from class: com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenterImpl.6
            @Override // rx.functions.Func1
            public ArrayList<RankingEntryViewModel> call(ArrayList<RankingEntry> rankingEntries) {
                ArrayList<RankingEntryViewModel> viewModelsRanking = new ArrayList<>();
                int position = 1;
                long latestRankingEntryTimeStamp = -1;
                RankingEntryViewModel latestRankingEntryViewModel = null;
                for (RankingEntry rankingEntry : rankingEntries) {
                    RankingEntryViewModel rankingEntryViewModel = RankingEntryViewModel.rankingEntryViewModelFromRankingEntry(rankingEntry);
                    int position2 = position + 1;
                    rankingEntryViewModel.setPosition(position);
                    viewModelsRanking.add(rankingEntryViewModel);
                    if (latestRankingEntryTimeStamp == -1 || rankingEntry.timestamp > latestRankingEntryTimeStamp) {
                        latestRankingEntryTimeStamp = rankingEntry.timestamp;
                        latestRankingEntryViewModel = rankingEntryViewModel;
                    }
                    position = position2;
                }
                if (latestRankingEntryViewModel != null) {
                    latestRankingEntryViewModel.setIsLatestEntry(true);
                }
                return viewModelsRanking;
            }
        }).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<ArrayList<RankingEntryViewModel>>() { // from class: com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenterImpl.5
            @Override // rx.SingleSubscriber
            public void onSuccess(ArrayList<RankingEntryViewModel> rankingEntryViewModels) {
                ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showRanking(rankingEntryViewModels);
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.e(error, "SAVING RANKING ENTRY ERROR!!!", new Object[0]);
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter
    public void gameOver(int score) {
        ((ZowiSaysMinigameView) getView()).hideProgress();
        if (score >= 12) {
            this.subscriptions.add(this.checkAchievementAndUnlockItInteractor.checkAchievementAndUnlockIt(this.gameAchievement).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Achievement>() { // from class: com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenterImpl.7
                @Override // rx.SingleSubscriber
                public void onSuccess(Achievement achievement) {
                    if (achievement == null) {
                        ZowiSaysMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.ANGRY)).subscribe(new CommandSingleSubscriber());
                        ZowiSaysMinigamePresenterImpl.this.achievementContinueButtonClicked();
                    } else {
                        ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                    }
                }

                @Override // rx.SingleSubscriber
                public void onError(Throwable error) {
                }
            }));
        } else {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.ANGRY)).subscribe(new CommandSingleSubscriber());
            achievementContinueButtonClicked();
        }
    }

    private void checkCurrentUserSequece() {
        int userSequenceSize = this.userCommandSequence.size();
        int zowiSequenceSize = this.zowiCommandSequence.size();
        if (userSequenceSize > zowiSequenceSize) {
            gameOver(zowiSequenceSize);
            this.isPlaying = false;
        } else {
            if (!this.userCommandSequence.get(userSequenceSize - 1).getCommandValue().equals(this.zowiCommandSequence.get(userSequenceSize - 1).getCommandValue())) {
                gameOver(zowiSequenceSize);
                this.isPlaying = false;
                return;
            }
            int progress = (userSequenceSize * 100) / zowiSequenceSize;
            ((ZowiSaysMinigameView) getView()).setProgressValue(progress, userSequenceSize, zowiSequenceSize);
            if (userSequenceSize == zowiSequenceSize) {
                executeZowiCommandSequence();
                this.userCommandSequence.clear();
            }
        }
    }

    private void executeZowiCommandSequence() {
        ((ZowiSaysMinigameView) getView()).blockUserControls();
        addRandomCommandToZowiSequence();
        ArrayList<Command> commandsToExecute = new ArrayList<>();
        for (Command command : this.zowiCommandSequence) {
            commandsToExecute.add(command);
            commandsToExecute.add(new StopCommand());
        }
        TimelineCommandSubscriber timelineCommandSubscriber = new TimelineCommandSubscriber(commandsToExecute);
        this.connectionController.getReceivedMessageObservable().subscribeOn(Schedulers.io()).filter(new Func1<String, Boolean>() { // from class: com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenterImpl.8
            @Override // rx.functions.Func1
            public Boolean call(String s) {
                return Boolean.valueOf(s.equals(ZowiSaysMinigamePresenterImpl.COMMAND_ACK));
            }
        }).subscribe((Subscriber<? super String>) timelineCommandSubscriber);
        timelineCommandSubscriber.initialize();
    }

    private void addRandomCommandToZowiSequence() {
        Random random = new Random();
        int index = random.nextInt(this.availableCommandList.size());
        this.zowiCommandSequence.add(this.availableCommandList.get(index));
    }

    private class TimelineCommandSubscriber extends Subscriber<String> {
        private ArrayList<Command> timeline;

        public TimelineCommandSubscriber(ArrayList<Command> timeline) {
            this.timeline = timeline;
        }

        public void initialize() {
            Command initialCommand = this.timeline.get(0);
            this.timeline.remove(0);
            ZowiSaysMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(initialCommand).subscribeOn(Schedulers.io()).subscribe();
        }

        @Override // rx.Observer
        public void onCompleted() {
            unsubscribe();
            ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showUserControls();
            ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).setProgressValue(0, 0, ZowiSaysMinigamePresenterImpl.this.zowiCommandSequence.size());
        }

        @Override // rx.Observer
        public void onError(Throwable error) {
            Grove.d("Send COMMAND to Zowi ERROR! " + error.toString(), new Object[0]);
            error.printStackTrace();
        }

        @Override // rx.Observer
        public void onNext(String s) {
            if (this.timeline.size() > 0) {
                Command nextCommand = this.timeline.get(0);
                this.timeline.remove(0);
                ZowiSaysMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(nextCommand).subscribeOn(Schedulers.io()).subscribe();
                return;
            }
            onCompleted();
        }
    }
}
