package com.bq.robotic.protocolSTK500v1;

import com.comscore.streaming.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class STK500v1 {
    private Hex hexParser;
    private InputStream input;
    private Logger logger;
    private OutputStream output;
    private boolean partialRecovery;
    private IReader reader;
    private Thread readerThread;
    private ArrayList<Long> statistics;
    private int timeoutRecoveries;
    private int syncStack = 0;
    private int uploadFileTries = 0;
    private volatile double progress = 0.0d;
    private boolean timeoutOccurred = false;
    private boolean recoverySuccessful = false;
    private boolean readWrittenPage = false;
    private volatile ProtocolState state = ProtocolState.INITIALIZING;

    public enum ProtocolState {
        INITIALIZING,
        READY,
        CONNECTING,
        WRITING,
        READING,
        FINISHED,
        ERROR_PARSE_HEX,
        ERROR_CONNECT,
        ERROR_WRITE,
        ERROR_READ
    }

    public STK500v1(OutputStream output, InputStream input, Logger log, byte[] binary) {
        this.hexParser = new Hex(binary, log);
        this.output = output;
        this.input = input;
        this.logger = log;
        this.logger.logcat("STKv1 constructor: Initializing protocol code", "v");
        this.statistics = new ArrayList<>();
    }

    private void initializeWrapper() {
        this.reader = new Reader(this.input, this.logger);
        this.readerThread = new Thread((Runnable) this.reader);
        this.readerThread.start();
        this.reader.start();
        while (this.reader.getState() != EReaderState.WAITING) {
            try {
                Thread.sleep(2L);
            } catch (InterruptedException e) {
            }
        }
        waitForReaderStateActivated();
        this.logger.logcat("STKv1 constructor: ReadWrapper should be started now", "v");
        this.state = ProtocolState.READY;
    }

    public ProtocolState getProtocolState() {
        return this.state;
    }

    private void recover() {
        this.logger.logcat("Recover: Attempting timeout recovery", "i");
        this.timeoutOccurred = true;
        this.recoverySuccessful = false;
        for (int i = 0; i < 5; i++) {
            this.partialRecovery = false;
            if (spamSync()) {
                this.partialRecovery = true;
                while (this.reader.getState() != EReaderState.WAITING) {
                }
                waitForReaderStateActivated();
                this.reader.forget();
                try {
                    synchronized (this) {
                        wait(5L);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getSynchronization()) {
                    this.recoverySuccessful = true;
                    this.timeoutRecoveries++;
                    this.logger.logcat("Recover: recovery successful - recovered from " + this.timeoutRecoveries + " so far.", "i");
                    return;
                }
            } else {
                this.logger.logcat("recover: Unable to regain comms", "i");
                restartReader();
                return;
            }
        }
    }

    private void restartReader() {
        this.logger.logcat("restartReader: restarting reader", "d");
        boolean stopScheduled = false;
        boolean startScheduled = false;
        while (this.reader.getState() != EReaderState.STOPPED) {
            if (!stopScheduled) {
                stopScheduled = this.reader.stop();
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        waitForReaderStateActivated();
        while (this.reader.getState() != EReaderState.WAITING) {
            if (!startScheduled) {
                startScheduled = this.reader.start();
            }
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        waitForReaderStateActivated();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:5|(2:7|(3:9|32|10)(2:11|(3:31|13|14)))(1:(1:16))|28|17|18|26|19|33|10|3) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean spamSync() {
        /*
            r9 = this;
            r5 = 0
            r6 = 2
            byte[] r0 = new byte[r6]
            r0 = {x00bc: FILL_ARRAY_DATA , data: [48, 32} // fill-array
            com.bq.robotic.protocolSTK500v1.Logger r6 = r9.logger
            java.lang.String r7 = "spamSync: sending commands"
            java.lang.String r8 = "d"
            r6.logcat(r7, r8)
            r4 = 0
            r2 = 0
        L12:
            r6 = 500(0x1f4, float:7.0E-43)
            if (r2 >= r6) goto Lb2
            com.bq.robotic.protocolSTK500v1.IReader r6 = r9.reader
            com.bq.robotic.protocolSTK500v1.EReaderState r6 = r6.getState()
            com.bq.robotic.protocolSTK500v1.EReaderState r7 = com.bq.robotic.protocolSTK500v1.EReaderState.TIMEOUT_OCCURRED
            if (r6 != r7) goto L62
            r6 = 10
            boolean r6 = r9.waitForReaderStateActivated(r6)
            if (r6 != 0) goto L34
            com.bq.robotic.protocolSTK500v1.Logger r6 = r9.logger
            java.lang.String r7 = "spamSync: gave up waiting for state activation"
            java.lang.String r8 = "d"
            r6.logcat(r7, r8)
        L31:
            int r2 = r2 + 1
            goto L12
        L34:
            com.bq.robotic.protocolSTK500v1.IReader r6 = r9.reader
            int r3 = r6.getResult()
            com.bq.robotic.protocolSTK500v1.Logger r6 = r9.logger
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "spamSync: reader.getresult returns: "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "i"
            r6.logcat(r7, r8)
            r6 = -3
            if (r3 != r6) goto L85
            com.bq.robotic.protocolSTK500v1.Logger r5 = r9.logger
            java.lang.String r6 = "SpamSync: Returning true"
            java.lang.String r7 = "i"
            r5.logcat(r6, r7)
            r5 = 1
        L61:
            return r5
        L62:
            if (r4 != 0) goto L85
            r4 = 1
            com.bq.robotic.protocolSTK500v1.Logger r6 = r9.logger
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "spamSync: reader not in TIMEOUT_OCCURRED, but in "
            java.lang.StringBuilder r7 = r7.append(r8)
            com.bq.robotic.protocolSTK500v1.IReader r8 = r9.reader
            com.bq.robotic.protocolSTK500v1.EReaderState r8 = r8.getState()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "i"
            r6.logcat(r7, r8)
        L85:
            java.io.OutputStream r6 = r9.output     // Catch: java.io.IOException -> L92
            r6.write(r0)     // Catch: java.io.IOException -> L92
            r6 = 5
            java.lang.Thread.sleep(r6)     // Catch: java.lang.InterruptedException -> L90
            goto L31
        L90:
            r6 = move-exception
            goto L31
        L92:
            r1 = move-exception
            com.bq.robotic.protocolSTK500v1.Logger r6 = r9.logger
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Unable to send sync: "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r1.getMessage()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "i"
            r6.logcat(r7, r8)
            goto L61
        Lb2:
            com.bq.robotic.protocolSTK500v1.Logger r6 = r9.logger
            java.lang.String r7 = "SpamSync: unable to recover. Returning false"
            java.lang.String r8 = "i"
            r6.logcat(r7, r8)
            goto L61
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bq.robotic.protocolSTK500v1.STK500v1.spamSync():boolean");
    }

    private void setProgress(double p) {
        if (((int) p) > 100) {
            this.logger.logcat("setProgress: Value too high, values was: " + p, "w");
            this.progress = 100.0d;
        } else if (((int) p) < 0) {
            this.logger.logcat("setProgress: Value too low, values was: " + p, "w");
            this.progress = 0.0d;
        } else {
            this.progress = p;
        }
    }

    public int getProgress() {
        return (int) this.progress;
    }

    private void writingStats() {
        long min = Long.MAX_VALUE;
        long max = 0;
        int size = this.statistics.size();
        long sum = 0;
        for (int i = 0; i < this.statistics.size(); i++) {
            long temp = this.statistics.get(i).longValue();
            if (temp > max) {
                max = temp;
            }
            if (temp < min) {
                min = temp;
            }
            sum += temp;
        }
        long average = size != 0 ? sum / ((long) size) : 0L;
        this.logger.logcat("writingStats: MAX: " + max, "i");
        this.logger.logcat("writingStats: MIN: " + min, "i");
        this.logger.logcat("writingStats: Average of " + size + ": " + average, "i");
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a7, code lost:
    
        r12.state = com.bq.robotic.protocolSTK500v1.STK500v1.ProtocolState.ERROR_CONNECT;
        shutdownReaderCompletely();
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:?, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean programUsingOptiboot(boolean r13, int r14) {
        /*
            Method dump skipped, instruction units count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bq.robotic.protocolSTK500v1.STK500v1.programUsingOptiboot(boolean, int):boolean");
    }

    private void shutdownReaderCompletely() {
        long time = System.currentTimeMillis();
        boolean stopScheduled = false;
        while (this.reader.getState() != EReaderState.STOPPED) {
            if (System.currentTimeMillis() > time + Constants.HEARTBEAT_STAGE_ONE_INTERVAL) {
                this.readerThread.stop();
                return;
            } else if (!stopScheduled) {
                stopScheduled = this.reader.stop();
            }
        }
        waitForReaderStateActivated(Constants.HEARTBEAT_STAGE_ONE_INTERVAL / 2);
        ((Reader) this.reader).requestCompleteStop();
    }

    private boolean resetAndSync() {
        for (int i = 0; i < 3; i++) {
            if (this.reader.getState() != EReaderState.WAITING) {
                restartReader();
            }
            waitForReaderStateActivated();
            this.logger.logcat("programUsingOptiboot: Attempting synchronization", "i");
            boolean connect = getSynchronization();
            if (connect) {
                return true;
            }
        }
        this.state = ProtocolState.ERROR_CONNECT;
        this.logger.logcat("programUsingOptiboot: Unable to reset and sync!", "i");
        this.reader.stop();
        return false;
    }

    private boolean softReset() {
        byte[] write = {-1, 0, 1, -1, 0, 0};
        this.logger.logcat("softReset: Sending bytes to restart arduino: " + Hex.bytesToHex(write), "d");
        for (byte b : write) {
            try {
                this.output.write(b);
            } catch (IOException e) {
                this.logger.logcat("softReset: Could not write to arduino.", "w");
                return false;
            }
        }
        this.logger.logcat("softReset: Restarting arduino...", "w");
        return true;
    }

    private String checkIfStarterKitPresent() {
        byte readByte;
        this.logger.logcat("checkIfStarterKitPresent: Detect programmer", "v");
        try {
            byte[] out = {ConstantsStk500v1.STK_GET_SIGN_ON, ConstantsStk500v1.CRC_EOP};
            this.output.write(out);
            this.logger.logcat("checkIfStarterKitPresent: Sending bytes to get starter kit: " + Hex.bytesToHex(out), "d");
            try {
                char[] response = new char[7];
                int responseIndex = 0;
                int readResult = 0;
                while (true) {
                    if (readResult < 0) {
                        break;
                    }
                    readResult = read(TimeoutValues.CONNECT);
                    if (readResult == -2) {
                        this.logger.logcat("checkIfStarterKitPresent: Couldn't start reading", "i");
                        break;
                    }
                    readByte = (byte) readResult;
                    this.logger.logcat("checkIfStarterKitPresent: Read byte: " + ((int) readByte), "v");
                    if (responseIndex == 0 && readByte == 20) {
                        responseIndex = 7;
                    } else {
                        if (responseIndex == 7 && readByte == 16) {
                            return "Arduino";
                        }
                        if (responseIndex < 0 || responseIndex >= 7) {
                            break;
                        }
                        response[responseIndex] = (char) readByte;
                        responseIndex++;
                    }
                }
                if (responseIndex == 0 && readByte == 21) {
                    this.logger.logcat("checkIfStarterKitPresent: Not in sync", "w");
                } else {
                    this.logger.logcat("checkIfStarterKitPresent: Not terminated by STK_OK!", "v");
                }
            } catch (IOException e) {
                this.logger.logcat("checkIfStarterKitPreset: Unable to read!", "e");
                return "";
            } catch (TimeoutException e2) {
                this.logger.logcat("checkIfStarterKitPresent: Timeout in checkIfStarterkitPresent!", "w");
                recover();
            }
            return "";
        } catch (IOException e3) {
            this.logger.logcat("checkIfStarterKitPresent: Communication problem: Can't send request for programmer version", "i");
            return "-1";
        }
    }

    private boolean getSynchronization() {
        byte[] getSyncCommand = {ConstantsStk500v1.STK_GET_SYNC, ConstantsStk500v1.CRC_EOP};
        try {
            this.output.write(getSyncCommand);
            if (checkInput(false, ConstantsStk500v1.STK_GET_SYNC, TimeoutValues.CONNECT)) {
                this.logger.logcat("getSynchronization: Sync achieved! Returning true", "v");
                this.syncStack = 0;
                return true;
            }
            if (this.timeoutOccurred && this.partialRecovery && !this.recoverySuccessful) {
                this.logger.logcat("GetSynchronization: Only partial timeout recovery, give up.", "i");
                return false;
            }
            if (this.timeoutOccurred && this.recoverySuccessful) {
                this.timeoutOccurred = false;
                this.logger.logcat("GetSynchronization: Recovered from timeout! Returning true.", "i");
                return true;
            }
            this.logger.logcat("getSynchronization: Could not get synchronization. Returning false.", "d");
            return false;
        } catch (IOException e) {
            this.logger.logcat("getSynchronization: Unable to write output in getSynchronization", "i");
            e.printStackTrace();
            return false;
        }
    }

    private boolean enterProgramMode() {
        byte[] command = {ConstantsStk500v1.STK_ENTER_PROGMODE, ConstantsStk500v1.CRC_EOP};
        this.logger.logcat("enterProgramMode: Sending bytes to enter programming mode: " + Hex.bytesToHex(command), "d");
        try {
            this.output.write(command);
            boolean ok = checkInput(true, ConstantsStk500v1.STK_ENTER_PROGMODE, TimeoutValues.CONNECT);
            if (!ok) {
                this.logger.logcat("enterProgramMode: Unable to enter programming mode", "w");
                return ok;
            }
            return ok;
        } catch (IOException e) {
            this.logger.logcat("enterProgramMode: Communication problem on sendingrequest to enter programming mode", "i");
            return false;
        }
    }

    private boolean leaveProgramMode() {
        byte[] command = {ConstantsStk500v1.STK_LEAVE_PROGMODE, ConstantsStk500v1.CRC_EOP};
        try {
            this.output.write(command);
        } catch (IOException e) {
            this.logger.logcat("leaveProgramMode: Communication problem on leavingprogramming mode", "i");
        }
        boolean ok = checkInput();
        if (!ok) {
            this.logger.logcat("leaveProgramMode: Unable to leave programming mode", "w");
        }
        return ok;
    }

    private boolean chipErase() {
        byte[] command = {ConstantsStk500v1.STK_CHIP_ERASE, ConstantsStk500v1.CRC_EOP};
        this.logger.logcat("chipErase: Sending bytes to erase chip: " + Hex.bytesToHex(command), "d");
        try {
            this.output.write(command);
            this.logger.logcat("chipErase: Chip erased!", "d");
            boolean ok = checkInput();
            if (!ok) {
                this.logger.logcat("chipErase: No sync. EOP not recieved for chip erase.", "v");
                return ok;
            }
            return ok;
        } catch (IOException e) {
            this.logger.logcat("chipErase: Communication problem on chip erase.", "v");
            return false;
        }
    }

    private boolean chipEraseUniversal() {
        byte[] command = {ConstantsStk500v1.STK_UNIVERSAL, -84, -128, 0, 0, ConstantsStk500v1.CRC_EOP};
        this.logger.logcat("chipEraseUniversal: Sending bytes to erase chip: " + Hex.bytesToHex(command), "d");
        try {
            this.output.write(command);
            byte[] in = new byte[3];
            this.logger.logcat("chipEraseUniversal: Waiting for " + in.length + " bytes.", "d");
            for (int i = 0; i < 3; i++) {
                try {
                    int numberOfBytes = read(TimeoutValues.READ);
                    in[i] = (byte) numberOfBytes;
                    switch (i) {
                        case 0:
                            if (numberOfBytes != 20) {
                                this.logger.logcat("chipEraseUniversal: STK_INSYNC failed on first byte, " + Hex.oneByteToHex(in[i]), "w");
                                return false;
                            }
                            break;
                            break;
                        case 1:
                            break;
                        case 2:
                            if (numberOfBytes == 16) {
                                this.logger.logcat("readPage: STK_OK, " + Hex.oneByteToHex(in[i]), "w");
                            }
                            return true;
                        default:
                            return false;
                    }
                } catch (IOException e) {
                    this.logger.logcat("readPage: Problem reading! " + e.getMessage(), "e");
                    return false;
                } catch (TimeoutException e2) {
                    this.logger.logcat("readPage: Unable to read", "w");
                    return false;
                }
            }
            this.logger.logcat("readPage: Something went wrong...", "w");
            return false;
        } catch (IOException e3) {
            this.logger.logcat("chipEraseUniversal: Communication problem on chip erase.", "v");
            return false;
        }
    }

    private boolean checkForAddressAutoincrement() {
        byte[] command = {ConstantsStk500v1.STK_CHECK_AUTOINC, ConstantsStk500v1.CRC_EOP};
        try {
            this.output.write(command);
            return checkInput();
        } catch (IOException e) {
            this.logger.logcat("checkForAddressAutoincrement: Unable to write output in checkForAddressAutoincrement", "i");
            e.printStackTrace();
            return false;
        }
    }

    private boolean loadAddress(int address) {
        byte[] tempAddr = packTwoBytes(address / 2);
        byte[] loadAddr = {ConstantsStk500v1.STK_LOAD_ADDRESS, tempAddr[1], tempAddr[0], ConstantsStk500v1.CRC_EOP};
        this.logger.logcat("loadAddress: Sending bytes to load address: " + Hex.bytesToHex(loadAddr), "d");
        this.logger.logcat("loadAddress: Memory address to load: " + address + " (" + (address / 2) + ")", "d");
        try {
            this.output.write(loadAddr);
            if (checkInput()) {
                this.logger.logcat("loadAddress: address loaded", "i");
                return true;
            }
            this.logger.logcat("loadAddress: failed to load address.", "w");
            return false;
        } catch (IOException e) {
            this.logger.logcat("loadAddress: Unable to write output in loadAddress", "w");
            e.printStackTrace();
            return false;
        }
    }

    private byte[] packTwoBytes(int integer) {
        byte[] bytes = {(byte) ((integer >> 8) & 255), (byte) (integer & 255)};
        return bytes;
    }

    private boolean programDataMemory(byte data) {
        byte[] programCommand = {ConstantsStk500v1.STK_PROG_DATA, data, ConstantsStk500v1.CRC_EOP};
        try {
            this.output.write(programCommand);
            return checkInput();
        } catch (IOException e) {
            this.logger.logcat("programDataMemory: Could not write output in programDataMemory", "i");
            e.printStackTrace();
            return false;
        }
    }

    private boolean programPage(boolean writeFlash, byte[] data) {
        boolean result = false;
        byte[] programPage = new byte[data.length + 5];
        programPage[0] = ConstantsStk500v1.STK_PROG_PAGE;
        programPage[1] = (byte) ((data.length >> 8) & 255);
        programPage[2] = (byte) (data.length & 255);
        if (writeFlash) {
            programPage[3] = 70;
            for (int i = 0; i < data.length; i++) {
                programPage[i + 4] = data[i];
            }
            programPage[data.length + 4] = ConstantsStk500v1.CRC_EOP;
            this.logger.logcat("programPage: Length of data to program: " + data.length, "v");
            this.logger.logcat("programPage: Writing bytes: " + Hex.bytesToHex(programPage), "d");
            this.logger.logcat("programPage: Data array: " + Hex.bytesToHex(data), "v");
            this.logger.logcat("programPage: programPage array, length: " + programPage.length, "v");
            try {
                this.output.write(programPage);
                long currentTime = System.currentTimeMillis();
                result = checkInput(false, ConstantsStk500v1.STK_PROG_PAGE, TimeoutValues.WRITE);
                if (result) {
                    this.statistics.add(Long.valueOf(System.currentTimeMillis() - currentTime));
                }
            } catch (IOException e) {
                this.logger.logcat("programPage: Could not write output in programDataMemory", "i");
                e.printStackTrace();
            }
            return result;
        }
        throw new IllegalArgumentException("Does not support writing to EEPROM.");
    }

    private byte[] readPage(int address, boolean writeFlash) {
        byte[] addr = packTwoBytes(address);
        return readPage(addr[0], addr[1], writeFlash);
    }

    private byte[] readPage(byte bytes_high, byte bytes_low, boolean writeFlash) {
        byte[] readCommand = new byte[5];
        readCommand[0] = ConstantsStk500v1.STK_READ_PAGE;
        readCommand[1] = bytes_high;
        readCommand[2] = bytes_low;
        if (writeFlash) {
            readCommand[3] = 70;
            readCommand[4] = ConstantsStk500v1.CRC_EOP;
            this.logger.logcat("readPage: Sending bytes: " + Hex.bytesToHex(readCommand), "d");
            try {
                this.output.write(readCommand);
            } catch (IOException e) {
                this.logger.logcat("readPage: Could not write output read command in readPage", "w");
                e.printStackTrace();
            }
            byte[] in = new byte[unPackTwoBytes(bytes_high, bytes_low)];
            this.logger.logcat("readPage: Waiting for " + in.length + " bytes.", "d");
            for (int i = 0; i < in.length + 2; i++) {
                try {
                    int numberOfBytes = read(TimeoutValues.READ);
                    if (i == 0) {
                        if (numberOfBytes != 20) {
                            this.logger.logcat("readPage: STK_INSYNC failed on first byte, " + Hex.oneByteToHex((byte) numberOfBytes), "w");
                            return null;
                        }
                        this.logger.logcat("readPage: STK_INSYNC, " + Hex.oneByteToHex((byte) numberOfBytes), "d");
                    } else {
                        if (i == in.length + 1) {
                            if (numberOfBytes != 16) {
                                this.logger.logcat("readPage: STK_OK failed on last byte, " + i + ", value " + Hex.oneByteToHex((byte) numberOfBytes), "w");
                                return null;
                            }
                            this.logger.logcat("readPage: Read OK.", "d");
                            return in;
                        }
                        in[i - 1] = (byte) numberOfBytes;
                    }
                } catch (IOException e2) {
                    this.logger.logcat("readPage: Unable to read! " + e2.getMessage(), "w");
                    return null;
                } catch (TimeoutException e3) {
                    this.logger.logcat("readPage: Unable to read! " + e3.getMessage(), "w");
                    return null;
                }
            }
            this.logger.logcat("readPage: Something went wrong...", "w");
            return null;
        }
        throw new IllegalArgumentException("Does not support reading from EEPROM.");
    }

    private byte[] readDataMemory() {
        byte[] in = new byte[3];
        byte[] readCommand = {ConstantsStk500v1.STK_READ_DATA, ConstantsStk500v1.CRC_EOP};
        try {
            this.output.write(readCommand);
        } catch (IOException e) {
            this.logger.logcat("readDataMemory: Could not write output read command in readDataMemory", "i");
            e.printStackTrace();
        }
        int numberOfBytes = 0;
        try {
            numberOfBytes = this.input.read(in);
        } catch (IOException e2) {
            this.logger.logcat("readDataMemory: Could not read input", "i");
            e2.printStackTrace();
        }
        if (numberOfBytes != 3 || in[0] != 20 || in[2] != 16) {
            if (numberOfBytes != 1 || in[0] != 21) {
                return null;
            }
            return in;
        }
        return in;
    }

    private byte[] readFlashMemory() {
        byte[] in = new byte[4];
        byte[] readCommand = {ConstantsStk500v1.STK_READ_FLASH, ConstantsStk500v1.CRC_EOP};
        try {
            this.output.write(readCommand);
        } catch (IOException e) {
            this.logger.logcat("readFlashMemory: Could not write output read command in readFlashMemory", "i");
            e.printStackTrace();
        }
        int numberOfBytes = 0;
        try {
            numberOfBytes = this.input.read(in);
        } catch (IOException e2) {
            this.logger.logcat("readFlashMemory: Could not read input in readFlashMemory", "i");
            e2.printStackTrace();
        }
        if (numberOfBytes != 4 || in[0] != 20 || in[3] != 16) {
            if (numberOfBytes != 1 || in[0] != 21) {
                return null;
            }
            return in;
        }
        return in;
    }

    private boolean checkInput() {
        return checkInput(false, (byte) 0, TimeoutValues.DEFAULT);
    }

    private boolean checkInput(boolean checkCommand, byte command, TimeoutValues timeout) {
        this.logger.logcat("checkInput called with command: " + Hex.oneByteToHex(command), "w");
        this.logger.logcat("checkInput: checkCommand = " + checkCommand, "i");
        try {
            int intInput = read(timeout);
            if (intInput == -1) {
                this.logger.logcat("checkInput: End of stream encountered", "w");
                return false;
            }
            if (intInput == 20) {
                this.logger.logcat("checkInput: received INSYNC", "i");
                int intInput2 = read(timeout);
                this.logger.logcat("checkInput: intInput = " + intInput2, "i");
                if (intInput2 == -1) {
                    this.logger.logcat("checkInput: End of stream encountered", "w");
                    return false;
                }
                byte byteInput = (byte) intInput2;
                if (checkCommand) {
                    switch (command) {
                        case 80:
                            if (byteInput == 19) {
                                this.logger.logcat("checkInput: Error entering programming mode: Programmer not found", "w");
                                throw new RuntimeException("STK_NODEVICE returned");
                            }
                            if (byteInput == 16) {
                                return true;
                            }
                            this.logger.logcat("checkInput: Reponse was STK_INSYNC but not STK_NODEVICE or STK_OK", "i");
                            return false;
                        default:
                            throw new IllegalArgumentException("Unhandled argument:" + ((int) command));
                    }
                }
                if (byteInput == 16) {
                    this.logger.logcat("checkInput: received OK. Returning true", "i");
                    return true;
                }
                this.logger.logcat("checkInput: Reponse was STK_INSYNC but not STK_OK", "v");
                return false;
            }
            if (this.syncStack > 2) {
                this.logger.logcat("checkInput: Avoid stack overflow, not in sync!", "v");
                return false;
            }
            this.logger.logcat("checkInput: Response was not STK_INSYNC, attempting synchronization.", "w");
            this.syncStack++;
            return false;
        } catch (IOException e) {
            this.logger.logcat("checkInput: Can't read! " + e.getMessage(), "w");
            return false;
        } catch (TimeoutException e2) {
            this.logger.logcat("checkInput: Timeout!", "w");
            if (this.timeoutOccurred) {
                return false;
            }
            this.logger.logcat("checkInput: Trying to recover", "w");
            recover();
            return false;
        }
    }

    private boolean programFlashMemory(byte flash_low, byte flash_high) {
        byte[] uploadFile = {ConstantsStk500v1.STK_PROG_FLASH, flash_low, flash_high, ConstantsStk500v1.CRC_EOP};
        try {
            this.logger.logcat("programFlashMemory: sending bytes to write word: " + Hex.bytesToHex(uploadFile), "d");
            this.output.write(uploadFile);
            if (checkInput()) {
                this.logger.logcat("programFlashMemory: word written", "v");
                return true;
            }
            this.logger.logcat("programFlashMemory: failed to write word", "w");
            return false;
        } catch (IOException e) {
            this.logger.logcat("programFlashMemory: Unable to write output in programFlashMemory", "i");
            e.printStackTrace();
            return false;
        }
    }

    private boolean hardwareReset() {
        this.logger.logcat("hardwareReset: Trying to reset arduino...", "d");
        this.uploadFileTries++;
        return true;
    }

    private boolean writeAndReadFile(boolean checkWrittenData, int bytesToLoad) {
        setProgress(0.0d);
        this.uploadFileTries = 0;
        if (checkWrittenData) {
            this.readWrittenPage = true;
        } else {
            this.readWrittenPage = false;
        }
        boolean success = uploadFile(bytesToLoad, true);
        if (success && checkWrittenData && uploadFile(bytesToLoad, false)) {
            return true;
        }
        return success;
    }

    private boolean uploadFile(int bytesToLoad, boolean write) {
        this.state = ProtocolState.WRITING;
        this.logger.logcat("progress: " + getProgress() + " %", "d");
        this.logger.logcat("uploadFile: Data bytes to write: " + bytesToLoad, "d");
        int hexPosition = 0;
        while (hexPosition < this.hexParser.getDataSize()) {
            if (this.uploadFileTries > 10) {
                return false;
            }
            byte[] tempArray = this.hexParser.getHexLine(hexPosition, bytesToLoad);
            this.logger.logcat("uploadFile: " + hexPosition + ", " + bytesToLoad, "v");
            this.logger.logcat("uploadFile: " + Hex.bytesToHex(tempArray), "v");
            if (tempArray.length == 0) {
                return true;
            }
            int j = 1;
            while (true) {
                if (j >= 5) {
                    break;
                }
                if (loadAddress(hexPosition)) {
                    this.logger.logcat("uploadFile: loadAddress OK after " + j + " attempts.", "v");
                    break;
                }
                if (!hardwareReset()) {
                    if (this.timeoutOccurred && !this.recoverySuccessful) {
                        return false;
                    }
                    if (this.timeoutOccurred) {
                        this.timeoutOccurred = false;
                        this.uploadFileTries++;
                    }
                }
                j++;
            }
            boolean success = true;
            if (write) {
                this.logger.logcat("uploadFile: Trying to write data.", "d");
                if (programPage(true, tempArray)) {
                    hexPosition += tempArray.length;
                    double tempProgress = ((double) hexPosition) / ((double) this.hexParser.getDataSize());
                    if (this.readWrittenPage) {
                        setProgress(50.0d * tempProgress);
                    } else {
                        setProgress(100.0d * tempProgress);
                    }
                    this.logger.logcat("progress: " + getProgress() + " % " + hexPosition + " / " + this.hexParser.getDataSize(), "d");
                } else {
                    success = false;
                }
            } else {
                this.logger.logcat("uploadFile: Trying to read written data.", "d");
                if (readPage(bytesToLoad, false) == tempArray) {
                    hexPosition += tempArray.length;
                    this.logger.logcat("hexPosition: " + hexPosition + ", hexParser.getDataSize(): " + this.hexParser.getDataSize(), "d");
                    setProgress((((double) hexPosition) / ((double) this.hexParser.getDataSize())) + 50.0d);
                    this.logger.logcat("progress: " + getProgress() + " % ", "d");
                } else {
                    success = false;
                }
            }
            if (!success) {
                if (this.timeoutOccurred && !this.recoverySuccessful) {
                    if (!hardwareReset()) {
                        return false;
                    }
                } else if (this.timeoutOccurred) {
                    this.timeoutOccurred = false;
                    this.uploadFileTries++;
                }
            }
        }
        this.logger.logcat("uploadFile: End of file. Upload finished with success.", "d");
        return true;
    }

    private static int unPackTwoBytes(byte high, byte low) {
        int out = (decodeByte(high) << 8) | decodeByte(low);
        return out;
    }

    private static int decodeByte(byte unsignedByte) {
        return unsignedByte & 255;
    }

    private int read(TimeoutValues timeout) throws TimeoutException, IOException {
        return read(null, timeout);
    }

    private int read(byte[] buffer, TimeoutValues timeout) throws TimeoutException, IOException {
        long time = System.currentTimeMillis();
        this.logger.logcat("read: waiting for reader waiting state", "i");
        while (this.reader.getState() != EReaderState.WAITING) {
            if (System.currentTimeMillis() - time > 50) {
                this.logger.logcat("read: Giving up waiting for reader", "d");
                return -2;
            }
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        waitForReaderStateActivated(10L);
        return this.reader.read(timeout);
    }

    private boolean waitForReaderStateActivated(long timeout) {
        this.logger.logcat("waitForReaderStateActivated: waiting for state to activate", "d");
        long time = System.currentTimeMillis();
        while (!this.reader.wasCurrentStateActivated()) {
            if (timeout > 0 && System.currentTimeMillis() - time > timeout) {
                return false;
            }
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean waitForReaderStateActivated() {
        return waitForReaderStateActivated(-1L);
    }
}
