package com.bq.robotic.protocolSTK500v1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class Reader implements Runnable, IReader {
    private BufferedInputStream bis;
    private volatile IReaderState currentState;
    private volatile boolean doCompleteStop;
    private Queue<IReaderState> eventQueue;
    private InputStream in;
    private volatile Exception lastException;
    private Logger logger;
    private int result;
    private EnumMap<EReaderState, IReaderState> states;

    public Reader(InputStream input, Logger logger) {
        IReaderState state;
        logger.logcat("Reader constructor: Initializing...", "i");
        if (input == null || logger == null) {
            throw new IllegalArgumentException("Reader.constructor: null as argument(s)");
        }
        this.eventQueue = new LinkedList();
        this.in = input;
        this.bis = new BufferedInputStream(this.in, 1024);
        this.logger = logger;
        this.states = new EnumMap<>(EReaderState.class);
        EReaderState[] arr$ = EReaderState.values();
        for (EReaderState eState : arr$) {
            switch (eState) {
                case STOPPED:
                    state = new StoppedState(this, eState);
                    break;
                case STARTING:
                    state = new StartingState(this, eState);
                    break;
                case WAITING:
                    state = new WaitingState(this, eState);
                    break;
                case READING:
                    state = new ReadingState(this, eState);
                    break;
                case RESULT_READY:
                    state = new ResultReadyState(this, eState);
                    break;
                case TIMEOUT_OCCURRED:
                    state = new TimeoutOccurredState(this, eState);
                    break;
                case FAIL:
                    state = new FailureState(this, eState);
                    break;
                case STOPPING:
                    state = new StoppingState(this, eState);
                    break;
                default:
                    throw new IllegalStateException("Reader constructor: Unknown state:" + eState);
            }
            this.states.put(eState, state);
        }
        this.currentState = this.states.get(EReaderState.STOPPED);
        logger.logcat("Reader constructor: Done", "i");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchState(EReaderState newState) {
        addToEventQueue(this.states.get(newState));
    }

    private synchronized void addToEventQueue(IReaderState newState) {
        if (this.eventQueue.size() > 500) {
            this.logger.logcat("addToEventQueue: Queue already full, has 500 states", "w");
        }
        this.logger.logcat("addToEventQueue: adding newState " + newState.getEnum() + " to queue", "d");
        this.eventQueue.add(newState);
        this.logger.logcat("addToEventQueue: states in queue after adding: " + this.eventQueue.size(), "d");
        synchronized (newState) {
            this.logger.logcat("addToEventQueue: notifying all", "d");
            notifyAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized IReaderState pollEventQueue() {
        IReaderState state;
        state = this.eventQueue.poll();
        if (state != null) {
            this.logger.logcat("pollEventQueue: polling event " + state.getEnum() + " from queue", "d");
        }
        return state;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void resetQueue() {
        this.eventQueue = new LinkedList();
    }

    @Override // com.bq.robotic.protocolSTK500v1.IReader
    public boolean wasCurrentStateActivated() {
        return this.currentState.hasStateBeenActivated();
    }

    @Override // com.bq.robotic.protocolSTK500v1.IReader
    public void forget() {
        ((IReader) this.currentState).forget();
    }

    @Override // com.bq.robotic.protocolSTK500v1.IReader
    public EReaderState getState() {
        return this.currentState.getEnum();
    }

    @Override // com.bq.robotic.protocolSTK500v1.IReader
    public int getResult() {
        return ((IReader) this.currentState).getResult();
    }

    @Override // com.bq.robotic.protocolSTK500v1.IReader
    public int read(TimeoutValues timeout) throws TimeoutException, IOException {
        return ((IReader) this.currentState).read(timeout);
    }

    @Override // com.bq.robotic.protocolSTK500v1.IReader
    public boolean stop() {
        return ((IReader) this.currentState).stop();
    }

    @Override // com.bq.robotic.protocolSTK500v1.IReader
    public boolean start() {
        return ((IReader) this.currentState).start();
    }

    public void requestCompleteStop() {
        if (this.currentState.getEnum() == EReaderState.STOPPED) {
            this.logger.logcat("requestCompleteStop: setting doCompleteStop to true", "d");
            this.doCompleteStop = true;
        }
        this.logger.logcat("requestCompleteStop: can only shut down completely while stopped. Current state: " + this.currentState.getEnum(), "d");
    }

    @Override // java.lang.Runnable
    public void run() {
        while (!this.doCompleteStop) {
            this.currentState.execute();
        }
        this.logger.logcat("Reader.run: Fully stopped (needs new Thread to restart)", "i");
    }

    public void setCurrentState(IReaderState currentState) {
        this.currentState = currentState;
    }

    abstract class BaseState implements IReaderState, IReader {
        private EReaderState eState;
        protected Reader reader;
        protected volatile boolean active = true;
        protected volatile boolean activated = false;
        protected boolean abort = false;

        public BaseState(Reader reader, EReaderState eState) {
            this.eState = eState;
            this.reader = reader;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean wasCurrentStateActivated() {
            return hasStateBeenActivated();
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public boolean hasStateBeenActivated() {
            return Reader.this.currentState == this && this.activated;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public void execute() {
            if (this.activated || this.abort) {
                IReaderState nextState = Reader.this.pollEventQueue();
                if (nextState != null) {
                    Reader.this.currentState = nextState;
                    ((BaseState) nextState).abort = false;
                    this.abort = true;
                    this.activated = false;
                    return;
                }
            } else {
                activate();
            }
            if (!this.active) {
                synchronized (this) {
                    try {
                        Reader.this.logger.logcat(getEnum() + "(Base).execute: waiting...", "v");
                        wait(1000L);
                        this.active = true;
                    } catch (InterruptedException e) {
                        Reader.this.logger.logcat(getEnum() + "(Base).execute: woken up!", "v");
                        this.active = true;
                    }
                }
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public boolean isReadingAllowed() {
            return false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public EReaderState getState() {
            return getEnum();
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public int read(TimeoutValues timeout) throws TimeoutException, IOException {
            return -2;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public int getResult() {
            return -2;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public EReaderState getEnum() {
            return this.eState;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public void forget() {
            throw new IllegalStateException(String.format("%s.forget: Only call when timed out or waiting", this.eState));
        }
    }

    class StoppedState extends BaseState {
        public StoppedState(Reader reader, EReaderState eState) {
            super(reader, eState);
            this.active = false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReaderState
        public void execute() {
            super.execute();
            if (!this.abort) {
                this.active = false;
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public void activate() {
            Reader.this.logger.logcat("StoppedState.activate: The reader has stopped", "i");
            Reader.this.bis = null;
            this.activated = true;
            this.abort = false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean stop() {
            Reader.this.logger.logcat("StoppedState.stop: Already stopped", "i");
            return true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean start() {
            Reader.this.logger.logcat(getEnum() + " start: Starting...", "d");
            Reader.this.switchState(EReaderState.STARTING);
            return true;
        }
    }

    class StartingState extends BaseState {
        public StartingState(Reader reader, EReaderState eState) {
            super(reader, eState);
            if (!this.abort) {
                this.active = true;
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public void activate() {
            Reader.this.logger.logcat("StartingState.activate: Starting...", "i");
            this.activated = true;
            this.active = true;
            this.abort = false;
            Reader.this.bis = new BufferedInputStream(Reader.this.in);
            Reader.this.switchState(EReaderState.WAITING);
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReaderState
        public void execute() {
            super.execute();
            if (this.abort) {
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean stop() {
            Reader.this.logger.logcat("StartingState: Wait until it's running before attempting to stop", "e");
            return false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean start() {
            Reader.this.logger.logcat("StartingState.start: Already starting...", "i");
            return true;
        }
    }

    class WaitingState extends BaseState {
        public WaitingState(Reader reader, EReaderState eState) {
            super(reader, eState);
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public void activate() {
            Reader.this.logger.logcat("WaitingState.activate: Ready to work", "d");
            Reader.this.lastException = null;
            this.active = true;
            this.activated = true;
            this.abort = false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean stop() {
            Reader.this.switchState(EReaderState.STOPPING);
            return true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean start() {
            Reader.this.logger.logcat("WaitingState.start: Already running...", "i");
            return true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReader
        public void forget() {
            try {
                int toSkip = Reader.this.bis.available();
                Reader.this.logger.logcat("WaitingState.forget: Attempts to skip " + toSkip + " bytes...", "d");
                long skipped = Reader.this.bis.skip(toSkip);
                Reader.this.logger.logcat("WaitingState.forget: Skipped " + skipped + " bytes", "d");
            } catch (IOException e) {
                Reader.this.logger.logcat("WaitingState.forget: " + e.getMessage(), "i");
                Reader.this.lastException = e;
                Reader.this.switchState(EReaderState.FAIL);
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReaderState
        public boolean isReadingAllowed() {
            return true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReader
        public int read(TimeoutValues timeout) throws TimeoutException, IOException {
            Reader.this.logger.logcat(getEnum() + " read: entered read method in Reader.java", "i");
            Reader.this.switchState(EReaderState.READING);
            while (true) {
                EReaderState s = Reader.this.currentState.getEnum();
                IReader state = (IReader) Reader.this.currentState;
                switch (s) {
                    case STOPPED:
                    case STOPPING:
                        Reader.this.logger.logcat("Reader.read: Terminated by request while reading!", "w");
                        return -2;
                    case STARTING:
                    default:
                        throw new IllegalArgumentException("Unexpected state " + s);
                    case WAITING:
                    case READING:
                        break;
                    case RESULT_READY:
                        int res = state.getResult();
                        Reader.this.logger.logcat(getEnum() + ".read: result: " + Hex.oneByteToHex((byte) res), "i");
                        return res;
                    case TIMEOUT_OCCURRED:
                        throw new TimeoutException("Reader.read: Reading timed out!");
                    case FAIL:
                        int res2 = state.getResult();
                        if (res2 != -1) {
                            if (Reader.this.lastException != null && (Reader.this.lastException instanceof IOException)) {
                                throw ((IOException) Reader.this.lastException);
                            }
                        } else {
                            return res2;
                        }
                }
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    class ReadingState extends BaseState {
        private long readInitiated;

        public ReadingState(Reader reader, EReaderState eState) {
            super(reader, eState);
            this.readInitiated = -1L;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public void activate() {
            Reader.this.logger.logcat("ReadingState.activate: Reading started...", "d");
            synchronized (this.reader) {
                Reader.this.result = -2;
            }
            this.readInitiated = System.currentTimeMillis();
            this.active = true;
            this.activated = true;
            this.abort = false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReaderState
        public void execute() {
            super.execute();
            if (!this.abort) {
                try {
                    long now = System.currentTimeMillis();
                    if (now - this.readInitiated > TimeoutValues.DEFAULT.getTimeout()) {
                        Reader.this.switchState(EReaderState.TIMEOUT_OCCURRED);
                        return;
                    }
                    int bytesInBuffer = Reader.this.bis.available();
                    if (bytesInBuffer > 0) {
                        Reader.this.logger.logcat(getEnum() + ".execute: bytes in buffer: " + bytesInBuffer, "d");
                        int b = Reader.this.bis.read();
                        if (b == -1) {
                            Reader.this.logger.logcat("ReadingState.execute: EndOfStream", "w");
                            synchronized (this.reader) {
                                Reader.this.result = b;
                            }
                            Reader.this.switchState(EReaderState.FAIL);
                            return;
                        }
                        synchronized (this.reader) {
                            Reader.this.result = b;
                        }
                        Reader.this.switchState(EReaderState.RESULT_READY);
                    }
                } catch (IOException e) {
                    Reader.this.logger.logcat("ReadingState.execute: " + e.getMessage(), "e");
                    Reader.this.lastException = e;
                    Reader.this.switchState(EReaderState.FAIL);
                }
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean stop() {
            Reader.this.logger.logcat("ReadingState.stop: Stopping, this might take some time", "i");
            Reader.this.switchState(EReaderState.STOPPING);
            return true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean start() {
            Reader.this.logger.logcat("ReadingState.start: Already running...", "i");
            return true;
        }
    }

    class ResultReadyState extends BaseState {
        private boolean resultFetched;

        public ResultReadyState(Reader reader, EReaderState eState) {
            super(reader, eState);
            this.resultFetched = false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReaderState
        public void execute() {
            super.execute();
            if (!this.abort && this.resultFetched) {
                Reader.this.logger.logcat("ResultReadyState: execute: should switch state to WAITING.", "i");
                Reader.this.switchState(EReaderState.WAITING);
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public void activate() {
            this.resultFetched = false;
            Reader.this.logger.logcat("ResultReadyState.activate: result arrived!", "d");
            this.activated = true;
            this.abort = false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReader
        public int getResult() {
            int res = Reader.this.result;
            Reader.this.logger.logcat(getEnum() + " getResult: " + Hex.oneByteToHex((byte) res), "d");
            synchronized (this.reader) {
                Reader.this.result = -2;
            }
            Reader.this.switchState(EReaderState.WAITING);
            synchronized (this) {
                this.resultFetched = true;
            }
            this.active = true;
            return res;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean stop() {
            Reader.this.switchState(EReaderState.STOPPING);
            return true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean start() {
            Reader.this.logger.logcat("" + getEnum() + ".start: Already running...", "i");
            return true;
        }
    }

    class TimeoutOccurredState extends BaseState {
        private volatile boolean forgetInProgress;
        private volatile boolean readInProgress;
        private volatile boolean receivedSomething;
        private int toSkip;

        public TimeoutOccurredState(Reader reader, EReaderState eState) {
            super(reader, eState);
            this.toSkip = -1;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReaderState
        public void execute() {
            super.execute();
            if (!this.abort && !this.forgetInProgress && !this.readInProgress && !this.receivedSomething) {
                try {
                    int skippableBytes = Reader.this.bis.available();
                    if (skippableBytes != this.toSkip) {
                        Reader.this.logger.logcat(getEnum() + ".execute: " + skippableBytes + " possible to skip.", "i");
                        this.toSkip = skippableBytes;
                    }
                    if (skippableBytes > 0) {
                        this.receivedSomething = true;
                    }
                } catch (IOException e) {
                    Reader.this.lastException = e;
                    Reader.this.switchState(EReaderState.FAIL);
                }
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReaderState
        public boolean isReadingAllowed() {
            return false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReader
        public int read(TimeoutValues timeout) throws TimeoutException, IOException {
            if (!isReadingAllowed()) {
                throw new IllegalStateException("Reading not allowed while reading or forgetting!");
            }
            return super.read(timeout);
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReader
        public int getResult() {
            if (!this.receivedSomething) {
                return -2;
            }
            Reader.this.switchState(EReaderState.WAITING);
            return -3;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReader
        public void forget() {
            if (this.readInProgress || this.forgetInProgress) {
                throw new IllegalStateException("Can't start forget process while already reading or forgetting");
            }
            this.forgetInProgress = true;
            try {
                int toSkip = Reader.this.bis.available();
                Reader.this.logger.logcat("TimeoutOccurred.forget: Attempts to skip " + toSkip + " bytes...", "d");
                long skipped = Reader.this.bis.skip(toSkip);
                Reader.this.logger.logcat("TimeoutOccurred.forget: Skipped " + skipped + " bytes", "d");
            } catch (IOException e) {
                Reader.this.logger.logcat("TimeoutOccurred.forget: " + e.getMessage(), "i");
                Reader.this.lastException = e;
                Reader.this.switchState(EReaderState.FAIL);
            } finally {
                this.forgetInProgress = false;
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public void activate() {
            this.readInProgress = false;
            this.forgetInProgress = false;
            this.receivedSomething = false;
            this.abort = false;
            this.toSkip = -1;
            try {
                int avail = Reader.this.bis.available();
                if (avail > 0) {
                    Reader.this.logger.logcat(getEnum() + ".activate: " + avail + "unread bytes already in the buffer!", "w");
                    forget();
                }
            } catch (IOException e) {
                Reader.this.lastException = e;
                Reader.this.switchState(EReaderState.FAIL);
            }
            this.active = true;
            this.activated = true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean stop() {
            Reader.this.logger.logcat("TimeoutOccurredState.stop: Stopping... Might take a while if blocking operations are in progress", "i");
            Reader.this.switchState(EReaderState.STOPPING);
            return true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean start() {
            Reader.this.logger.logcat("TimeoutOccurredState.start: Already running", "i");
            return true;
        }
    }

    class FailureState extends BaseState {
        public FailureState(Reader reader, EReaderState eState) {
            super(reader, eState);
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public void activate() {
            Reader.this.logger.logcat(getEnum() + ".activate: Reader failed!", "e");
            this.activated = true;
            this.abort = false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReaderState
        public void execute() {
            super.execute();
            if (!this.abort) {
                this.active = false;
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReader
        public int getResult() {
            if (Reader.this.lastException != null) {
                if (Reader.this.lastException instanceof IOException) {
                    throw new RuntimeException(Reader.this.lastException);
                }
                throw new RuntimeException("Unexpected exception of type " + Reader.this.lastException.getClass(), Reader.this.lastException);
            }
            throw new RuntimeException("An Unknown problem occured!");
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean stop() {
            Reader.this.switchState(EReaderState.STOPPING);
            Reader.this.logger.logcat(getEnum() + ".stop: Stopping...", "i");
            return true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean start() {
            Reader.this.logger.logcat(getEnum() + ".start: Already running, though currently in a failure state.", "i");
            return true;
        }
    }

    class StoppingState extends BaseState {
        public StoppingState(Reader reader, EReaderState eState) {
            super(reader, eState);
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReaderState
        public void activate() {
            Reader.this.resetQueue();
            Reader.this.logger.logcat("StoppingState.activate: Shutdown in progress...", "i");
            this.active = true;
            ((BaseState) Reader.this.states.get(EReaderState.STOPPED)).abort = false;
            this.activated = true;
            this.abort = false;
        }

        @Override // com.bq.robotic.protocolSTK500v1.Reader.BaseState, com.bq.robotic.protocolSTK500v1.IReaderState
        public void execute() {
            super.execute();
            if (!this.abort) {
                Reader.this.switchState(EReaderState.STOPPED);
            }
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean stop() {
            Reader.this.logger.logcat("StoppingState.stop: Already stopping...", "i");
            return true;
        }

        @Override // com.bq.robotic.protocolSTK500v1.IReader
        public boolean start() {
            Reader.this.logger.logcat(getEnum() + ".start: Can't start during shutdown!", "e");
            return false;
        }
    }
}
