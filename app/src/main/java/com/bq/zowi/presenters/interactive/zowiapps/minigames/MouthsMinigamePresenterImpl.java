package com.bq.zowi.presenters.interactive.zowiapps.minigames;

import android.os.CountDownTimer;
import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.GameController;
import com.bq.zowi.api.RankingController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.RankingEntry;
import com.bq.zowi.models.commands.AnimationCommand;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.commands.MouthCommand;
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
import com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMiniGameView;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class MouthsMinigamePresenterImpl extends InteractiveBasePresenterImpl<MouthsMiniGameView, MinigameBaseWireframe> implements MouthsMinigamePresenter {
    private static final String COMMAND_ACK = "A";
    private final int MIN_SCORE_TO_RANK;
    private final int MOUTH_TYPES;
    private final long ONE_SECOND_MILLIS;
    private final int SCORE_TO_OVERCOME_FOR_ACHIEVEMENTS;
    private CheckAchievementAndUnlockItInteractor checkAchievementAndUnlockItInteractor;
    private BTConnectionController connectionController;
    private CountDownTimer countDownTimer;
    private long countdownTimeInMillis;
    private final Achievement.Id gameAchievement;
    private GameController gameController;
    private boolean isPlaying;
    private int level;
    private MouthCommand mouthInGame;
    private int mouthTypeToPlay;
    private ArrayList<MouthCommand> mouthsType0;
    private ArrayList<MouthCommand> mouthsType1;
    private ArrayList<MouthCommand> mouthsType2;
    private ArrayList<MouthCommand> mouthsType3;
    private RankingController rankingController;
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;
    private Scheduler uiScheduler;

    public MouthsMinigamePresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, SendCommandToZowiInteractor sendCommandToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, RankingController rankingController, GameController gameController, CheckAchievementAndUnlockItInteractor checkAchievementAndUnlockItInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, String factoryFirmwarePath, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.isPlaying = false;
        this.SCORE_TO_OVERCOME_FOR_ACHIEVEMENTS = 8;
        this.gameAchievement = Achievement.Id.mouths_editor;
        this.MIN_SCORE_TO_RANK = 2;
        this.MOUTH_TYPES = 4;
        this.ONE_SECOND_MILLIS = 1000L;
        this.countdownTimeInMillis = 10000L;
        this.connectionController = connectionController;
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
        this.rankingController = rankingController;
        this.gameController = gameController;
        this.checkAchievementAndUnlockItInteractor = checkAchievementAndUnlockItInteractor;
        this.uiScheduler = uiScheduler;
        initMouthsTypes();
    }

    @Override
    public void onCreateView() {
        super.onCreateView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
        }
    }

    @Override
    public void gameReady() {
        this.disposables.add(this.gameController.isFirstPlay(GameController.GAME_ID.MOUTHS_GAME_ID, true).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            isFirstTimePlayed -> {
                if (isFirstTimePlayed.booleanValue()) {
                    ((MouthsMiniGameView) MouthsMinigamePresenterImpl.this.getView()).showHelp();
                }
            },
            error -> Grove.d(error.getMessage(), new Object[0])
        ));
    }

    @Override
    public void playButtonPressed() {
        this.isPlaying = true;
        ((MouthsMiniGameView) getView()).showProgress();
        this.level = 1;
        ((MouthsMiniGameView) getView()).updateLevel(this.level);
        this.mouthTypeToPlay = 0;
        selectMouthInGameConsiderigType();
        this.sendCommandToZowiInteractor.sendCommandToZowi(this.mouthInGame).subscribe(new CommandSingleSubscriber());
        configureCountdownTimer(this.level);
        this.countDownTimer.start();
        ((MouthsMiniGameView) getView()).isMouthGridTouchable(true);
    }

    @Override
    public void checkLedMouth(String mouthDrawnByUser) {
        if (this.isPlaying && mouthDrawnByUser.equals(this.mouthInGame.getLedMatrix())) {
            this.isPlaying = false;
            this.countDownTimer.cancel();
            ((MouthsMiniGameView) getView()).updateProgress(0);
            ((MouthsMiniGameView) getView()).isMouthGridTouchable(false);
            ArrayList<Command> commandsToExecute = new ArrayList<>();
            commandsToExecute.add(new AnimationCommand(Command.Action.VICTORY));
            commandsToExecute.add(new StopCommand());
            SequentialCommandSubscriber sequentialCommandSubscriber = new SequentialCommandSubscriber(commandsToExecute);
            this.connectionController.getReceivedMessageObservable().subscribeOn(Schedulers.io()).filter(s -> s.equals(COMMAND_ACK)).subscribe(sequentialCommandSubscriber);
            sequentialCommandSubscriber.initialize();
        }
    }

    @Override
    public void homeButtonPressed() {
        ((MinigameBaseWireframe) getWireframe()).presentHome();
    }

    @Override
    public void helpButtonPressed() {
        ((MouthsMiniGameView) getView()).showHelp();
    }

    @Override
    public void rankingButtonPressed() {
        this.disposables.add(this.rankingController.getRanking(GameController.GAME_ID.MOUTHS_GAME_ID).subscribeOn(Schedulers.io()).map(new Function<ArrayList<RankingEntry>, ArrayList<RankingEntryViewModel>>() {
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
            rankingEntryViewModels -> ((MouthsMiniGameView) MouthsMinigamePresenterImpl.this.getView()).showRanking(rankingEntryViewModels),
            error -> Grove.e(error, "RETRIEVING RANKING ERROR!!!", new Object[0])
        ));
    }

    @Override
    public void achievementContinueButtonClicked() {
        this.disposables.add(this.rankingController.isScoreInTop10(GameController.GAME_ID.MOUTHS_GAME_ID, this.level).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            isScoreInTop10 -> ((MouthsMiniGameView) MouthsMinigamePresenterImpl.this.getView()).showPoinsEarned(MouthsMinigamePresenterImpl.this.level - 1, MouthsMinigamePresenterImpl.this.level > 2 && isScoreInTop10.booleanValue()),
            error -> Grove.e(error, "CHECKING RANKING TOP 10 ERROR!!!", new Object[0])
        ));
    }

    @Override
    public void playerNameEnteredForRanking(String playerName) {
        RankingEntry newRankingEntry = new RankingEntry(this.level - 1, playerName);
        this.disposables.add(this.rankingController.saveRankingEntry(GameController.GAME_ID.MOUTHS_GAME_ID, newRankingEntry).subscribeOn(Schedulers.io()).map(new Function<ArrayList<RankingEntry>, ArrayList<RankingEntryViewModel>>() {
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
            rankingEntryViewModels -> ((MouthsMiniGameView) MouthsMinigamePresenterImpl.this.getView()).showRanking(rankingEntryViewModels),
            error -> Grove.e(error, "SAVING RANKING ENTRY ERROR!!!", new Object[0])
        ));
    }

    @Override
    public void gameOver(int score) {
        this.isPlaying = false;
        ((MouthsMiniGameView) getView()).updateProgress(0);
        ((MouthsMiniGameView) getView()).hideProgress();
        ((MouthsMiniGameView) getView()).resetMouthGrid();
        ((MouthsMiniGameView) getView()).isMouthGridTouchable(false);
        if (score >= 8) {
            this.disposables.add(this.checkAchievementAndUnlockItInteractor.checkAchievementAndUnlockIt(this.gameAchievement).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
                achievement -> {
                    if (achievement == null) {
                        MouthsMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.ANGRY)).subscribe(new CommandSingleSubscriber());
                        MouthsMinigamePresenterImpl.this.achievementContinueButtonClicked();
                    } else {
                        ((MouthsMiniGameView) MouthsMinigamePresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                    }
                },
                error -> { }
            ));
        } else {
            this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.ANGRY)).subscribe(new CommandSingleSubscriber());
            achievementContinueButtonClicked();
        }
    }

    private void initMouthsTypes() {
        this.mouthsType0 = new ArrayList<>();
        this.mouthsType0.add(new MouthCommand(Command.Action.MOUTH_SMILE));
        this.mouthsType0.add(new MouthCommand(Command.Action.MOUTH_SAD));
        this.mouthsType0.add(new MouthCommand(Command.Action.MOUTH_BIG_SURPRISE));
        this.mouthsType0.add(new MouthCommand(Command.Action.MOUTH_ANGRY));
        this.mouthsType0.add(new MouthCommand(Command.Action.MOUTH_SAD_OPEN));
        this.mouthsType1 = new ArrayList<>();
        this.mouthsType1.add(new MouthCommand(Command.Action.MOUTH_HAPPY_OPEN));
        this.mouthsType1.add(new MouthCommand(Command.Action.MOUTH_SAD_CLOSED));
        this.mouthsType1.add(new MouthCommand(Command.Action.MOUTH_SMALL_SURPRISE));
        this.mouthsType1.add(new MouthCommand(Command.Action.MOUTH_TONGUE_OUT));
        this.mouthsType1.add(new MouthCommand(Command.Action.MOUTH_TEETH));
        this.mouthsType1.add(new MouthCommand(Command.Action.MOUTH_X));
        this.mouthsType2 = new ArrayList<>();
        this.mouthsType2.add(new MouthCommand(Command.Action.MOUTH_HAPPY_CLOSED));
        this.mouthsType2.add(new MouthCommand(Command.Action.MOUTH_LINE));
        this.mouthsType2.add(new MouthCommand(Command.Action.MOUTH_BIG_OPEN));
        this.mouthsType2.add(new MouthCommand(Command.Action.MOUTH_CONCENTRATED));
        this.mouthsType2.add(new MouthCommand(Command.Action.MOUTH_THUNDER));
        this.mouthsType2.add(new MouthCommand(Command.Action.MOUTH_DIAGONAL));
        this.mouthsType3 = new ArrayList<>();
        this.mouthsType3.add(new MouthCommand(Command.Action.MOUTH_OK));
        this.mouthsType3.add(new MouthCommand(Command.Action.MOUTH_VAMP1));
        this.mouthsType3.add(new MouthCommand(Command.Action.MOUTH_VAMP2));
        this.mouthsType3.add(new MouthCommand(Command.Action.MOUTH_HEART));
        this.mouthsType3.add(new MouthCommand(Command.Action.MOUTH_INTERROGATION));
        this.mouthsType3.add(new MouthCommand(Command.Action.MOUTH_WALRUS));
    }

    private void selectMouthInGameConsiderigType() {
        switch (this.mouthTypeToPlay) {
            case 0:
                this.mouthInGame = this.mouthsType0.get(randomInt(0, this.mouthsType0.size() - 1));
                break;
            case 1:
                this.mouthInGame = this.mouthsType1.get(randomInt(0, this.mouthsType1.size() - 1));
                break;
            case 2:
                this.mouthInGame = this.mouthsType2.get(randomInt(0, this.mouthsType2.size() - 1));
                break;
            case 3:
                this.mouthInGame = this.mouthsType3.get(randomInt(0, this.mouthsType3.size() - 1));
                break;
        }
    }

    private void configureCountdownTimer(final int level) {
        if (level % 4 == 0) {
            this.countdownTimeInMillis -= 1000;
        }
        this.countDownTimer = new CountDownTimer(this.countdownTimeInMillis, 100L) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (MouthsMinigamePresenterImpl.this.isPlaying) {
                    long progress = ((millisUntilFinished - MouthsMinigamePresenterImpl.this.countdownTimeInMillis) * (-100)) / MouthsMinigamePresenterImpl.this.countdownTimeInMillis;
                    ((MouthsMiniGameView) MouthsMinigamePresenterImpl.this.getView()).updateProgress((int) progress);
                }
            }

            @Override
            public void onFinish() {
                if (MouthsMinigamePresenterImpl.this.isPlaying) {
                    MouthsMinigamePresenterImpl.this.gameOver(level);
                }
            }
        };
    }

    private int randomInt(int min, int max) {
        Random rand = new SecureRandom();
        return rand.nextInt((max - min) + 1) + min;
    }

    public void startNextLevel() {
        this.isPlaying = true;
        ((MouthsMiniGameView) getView()).resetMouthGrid();
        this.level++;
        ((MouthsMiniGameView) getView()).updateLevel(this.level);
        this.mouthTypeToPlay++;
        if (this.mouthTypeToPlay > 3) {
            this.mouthTypeToPlay = 0;
        }
        selectMouthInGameConsiderigType();
        this.sendCommandToZowiInteractor.sendCommandToZowi(this.mouthInGame).subscribe(new CommandSingleSubscriber());
        configureCountdownTimer(this.level);
        this.countDownTimer.start();
        ((MouthsMiniGameView) getView()).isMouthGridTouchable(true);
    }

    private class SequentialCommandSubscriber extends DisposableObserver<String> {
        private ArrayList<Command> timeline;

        public SequentialCommandSubscriber(ArrayList<Command> timeline) {
            this.timeline = timeline;
        }

        public void initialize() {
            Command initialCommand = this.timeline.get(0);
            this.timeline.remove(0);
            MouthsMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(initialCommand).subscribeOn(Schedulers.io()).subscribe();
        }

        @Override
        public void onComplete() {
            dispose();
            MouthsMinigamePresenterImpl.this.startNextLevel();
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
                MouthsMinigamePresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(nextCommand).subscribeOn(Schedulers.io()).subscribe();
                return;
            }
            onComplete();
        }
    }
}
