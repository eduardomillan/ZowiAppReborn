package com.bq.zowi.presenters.interactive.zowiapps.minigames;

import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.GameController;
import com.bq.zowi.api.RankingController;
import com.bq.zowi.api.SessionController;
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
import com.bq.zowi.usecases.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.usecases.SendCommandToZowiInteractor;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameView;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Random;

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

    @Override
    public void onCreateView() {
        super.onCreateView();
    }

    @Override
    public void gameReady() {
        this.disposables.add(this.gameController.isFirstPlay(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, true).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            isFirstTimePlayed -> {
                if (isFirstTimePlayed.booleanValue()) {
                    ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showHelp();
                }
            },
            error -> Grove.d(error.getMessage(), new Object[0])
        ));
    }

    @Override
    public void homeButtonPressed() {
        ((MinigameBaseWireframe) getWireframe()).presentHome();
    }

    @Override
    public void helpButtonPressed() {
        ((ZowiSaysMinigameView) getView()).showHelp();
    }

    @Override
    public void rankingButtonPressed() {
        this.disposables.add(this.rankingController.getRanking(GameController.GAME_ID.ZOWI_SAYS_GAME_ID).subscribeOn(Schedulers.io()).map(new Function<ArrayList<RankingEntry>, ArrayList<RankingEntryViewModel>>() {
            @Override
            public ArrayList<RankingEntryViewModel> apply(ArrayList<RankingEntry> rankingEntries) {
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
        }).observeOn(this.uiScheduler).subscribe(
            rankingEntryViewModels -> ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showRanking(rankingEntryViewModels),
            error -> Grove.e(error, "RETRIEVING RANKING ERROR!!!", new Object[0])
        ));
    }

    @Override
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

    @Override
    public void topLeftActionPressed() {
        if (this.isPlaying) {
            this.userCommandSequence.add(new ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, this.configuredDuration));
            checkCurrentUserSequece();
        }
    }

    @Override
    public void topRightActionPressed() {
        if (this.isPlaying) {
            this.userCommandSequence.add(new LeftRightCommand(Command.Action.BEND, Command.Direction.RIGHT, this.configuredDuration));
            checkCurrentUserSequece();
        }
    }

    @Override
    public void bottomLeftActionPressed() {
        if (this.isPlaying) {
            this.userCommandSequence.add(new StaticCommand(Command.Action.JUMP, this.configuredDuration));
            checkCurrentUserSequece();
        }
    }

    @Override
    public void bottomRightActionPressed() {
        if (this.isPlaying) {
            this.userCommandSequence.add(new LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.RIGHT, this.configuredDuration));
            checkCurrentUserSequece();
        }
    }

    @Override
    public void achievementContinueButtonClicked() {
        this.disposables.add(this.rankingController.isScoreInTop10(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, this.zowiCommandSequence.size()).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            isScoreInTop10 -> ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showPoinsEarned(ZowiSaysMinigamePresenterImpl.this.zowiCommandSequence.size() - 1, ZowiSaysMinigamePresenterImpl.this.zowiCommandSequence.size() > 3 && isScoreInTop10.booleanValue()),
            error -> Grove.e(error, "CHECKING RANKING TOP 10 ERROR!!!", new Object[0])
        ));
    }

    @Override
    public void playerNameEnteredForRanking(String playerName) {
        RankingEntry newRankingEntry = new RankingEntry(this.zowiCommandSequence.size() - 1, playerName);
        this.disposables.add(this.rankingController.saveRankingEntry(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, newRankingEntry).subscribeOn(Schedulers.io()).map(new Function<ArrayList<RankingEntry>, ArrayList<RankingEntryViewModel>>() {
            @Override
            public ArrayList<RankingEntryViewModel> apply(ArrayList<RankingEntry> rankingEntries) {
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
        }).observeOn(this.uiScheduler).subscribe(
            rankingEntryViewModels -> ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showRanking(rankingEntryViewModels),
            error -> Grove.e(error, "SAVING RANKING ENTRY ERROR!!!", new Object[0])
        ));
    }

    @Override
    public void gameOver(int score) {
        ((ZowiSaysMinigameView) getView()).hideProgress();
        if (score >= 12) {
            this.disposables.add(this.checkAchievementAndUnlockItInteractor.checkAchievementAndUnlockIt(this.gameAchievement).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
                achievement -> {
                    if (achievement == null) {
                        ZowiSaysMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.ANGRY)).subscribe(new CommandSingleSubscriber());
                        ZowiSaysMinigamePresenterImpl.this.achievementContinueButtonClicked();
                    } else {
                        ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                    }
                },
                error -> { }
            ));
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
        this.connectionController.getReceivedMessageObservable().subscribeOn(Schedulers.io()).filter(s -> s.equals(COMMAND_ACK)).subscribe(timelineCommandSubscriber);
        timelineCommandSubscriber.initialize();
    }

    private void addRandomCommandToZowiSequence() {
        Random random = new Random();
        int index = random.nextInt(this.availableCommandList.size());
        this.zowiCommandSequence.add(this.availableCommandList.get(index));
    }

    private class TimelineCommandSubscriber extends DisposableObserver<String> {
        private ArrayList<Command> timeline;

        public TimelineCommandSubscriber(ArrayList<Command> timeline) {
            this.timeline = timeline;
        }

        public void initialize() {
            Command initialCommand = this.timeline.get(0);
            this.timeline.remove(0);
            ZowiSaysMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(initialCommand).subscribeOn(Schedulers.io()).subscribe();
        }

        @Override
        public void onComplete() {
            dispose();
            ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).showUserControls();
            ((ZowiSaysMinigameView) ZowiSaysMinigamePresenterImpl.this.getView()).setProgressValue(0, 0, ZowiSaysMinigamePresenterImpl.this.zowiCommandSequence.size());
        }

        @Override
        public void onError(Throwable error) {
            Grove.d("Send COMMAND to Zowi ERROR! " + error.toString(), new Object[0]);
            error.printStackTrace();
        }

        @Override
        public void onNext(String s) {
            if (this.timeline.size() > 0) {
                Command nextCommand = this.timeline.get(0);
                this.timeline.remove(0);
                ZowiSaysMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(nextCommand).subscribeOn(Schedulers.io()).subscribe();
                return;
            }
            onComplete();
        }
    }
}
