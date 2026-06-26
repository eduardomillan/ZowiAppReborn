package com.bq.robotic.protocolSTK500v1;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Hex {
    private ArrayList<ArrayList<Byte>> binList = new ArrayList<>();
    private ArrayList<Byte> dataList = new ArrayList<>();
    private Logger logger;
    private boolean state;
    private byte[] subHex;

    public Hex(byte[] bin, Logger log) {
        this.state = false;
        this.logger = log;
        this.subHex = bin;
        this.state = splitHex();
        this.logger.logcat("Hex file status: " + this.state, "v");
    }

    public int getDataSize() {
        return this.dataList.size();
    }

    public boolean getChecksumStatus() {
        return this.state;
    }

    public byte[] getHexLine(int startByte, int numberOfBytes) {
        try {
            this.logger.logcat("Hex.getHexLine: startByte: " + startByte + ", numberOfBytes: " + numberOfBytes, "d");
            return formatHexLine(startByte, numberOfBytes);
        } catch (IndexOutOfBoundsException e) {
            this.logger.logcat("Hex.getHexLine: startByte is out of bounds! Value was: " + startByte + ", max value: " + this.dataList.size(), "w");
            return new byte[0];
        }
    }

    private byte[] formatHexLine(int startByte, int numberOfBytes) throws IndexOutOfBoundsException {
        try {
            this.dataList.get(startByte);
            int dataLength = numberOfBytes;
            if (startByte + numberOfBytes > this.dataList.size()) {
                dataLength = this.dataList.size() - startByte;
                this.logger.logcat("Hex.formatHexLine: Could not read " + numberOfBytes + " bytes, changed to " + dataLength, "i");
            }
            byte[] tempArray = new byte[dataLength];
            for (int i = 0; i < dataLength; i++) {
                tempArray[i] = this.dataList.get(startByte + i).byteValue();
            }
            return tempArray;
        } catch (Exception e) {
            throw new IndexOutOfBoundsException("Index " + startByte + " is out of bounds!");
        }
    }

    private boolean splitHex() {
        int b = 0;
        while (b < this.subHex.length) {
            b = splitHex(b);
            if (b < 0) {
                return false;
            }
        }
        return true;
    }

    private int splitHex(int startOnDataByte) {
        if (this.subHex.length + startOnDataByte < 6) {
            this.logger.logcat("splitHex(): The minimum size of a line is 6, this line was " + this.subHex.length, "w");
            return -1;
        }
        int dataLength = this.subHex[startOnDataByte + 1];
        if (this.subHex[startOnDataByte] != 58) {
            this.logger.logcat("splitHex(): Line not starting with ':' !", "w");
            return -1;
        }
        if (this.subHex[startOnDataByte + 4] == 1 && dataLength > 0) {
            this.logger.logcat("splitHex(): Contains data, but are told to stop!", "w");
            return -1;
        }
        if (this.subHex[startOnDataByte + 4] == 1 && this.subHex.length > startOnDataByte + dataLength + 6) {
            this.logger.logcat("splitHex(): Contains more lines with data, but are told to stop!", "w");
            return -1;
        }
        if (this.subHex[startOnDataByte + 4] == 0 && this.subHex[startOnDataByte + 1] == 0) {
            this.logger.logcat("splitHex(): Told to send data, but contains no data!", "w");
            return -1;
        }
        this.binList.add(new ArrayList<>());
        byte[] tempBytes = new byte[this.subHex[startOnDataByte + 1] + 6];
        tempBytes[0] = this.subHex[startOnDataByte + 1];
        tempBytes[1] = this.subHex[startOnDataByte + 2];
        tempBytes[2] = this.subHex[startOnDataByte + 3];
        tempBytes[3] = this.subHex[startOnDataByte + 4];
        for (int i = 0; i < dataLength; i++) {
            tempBytes[i] = this.subHex[i + startOnDataByte + 5];
        }
        tempBytes[tempBytes.length - 1] = this.subHex[startOnDataByte + 5 + dataLength];
        if (checkData(tempBytes, startOnDataByte)) {
            for (int i2 = 0; i2 < dataLength; i2++) {
                try {
                    this.dataList.add(Byte.valueOf(this.subHex[startOnDataByte + i2 + 5]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    return -1;
                }
            }
            return startOnDataByte + dataLength + 6;
        }
        this.logger.logcat("splitHex(): Checksum failed!", "w");
        return -1;
    }

    private boolean checkData(byte[] data, int startByte) {
        int byteValue = 0;
        for (int i = 0; i < data.length - 2; i++) {
            byteValue += this.subHex[startByte + i + 1];
        }
        byte check = (byte) (256 - byteValue);
        return ((byte) (check & 255)) == data[(data.length + (-1)) & 255];
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] hexChars = new char[bytes.length * 5];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 255;
            hexChars[j * 5] = '[';
            hexChars[(j * 5) + 1] = hexArray[v >>> 4];
            hexChars[(j * 5) + 2] = hexArray[v & 15];
            hexChars[(j * 5) + 3] = ']';
            hexChars[(j * 5) + 4] = ' ';
        }
        return new String(hexChars);
    }

    public static String oneByteToHex(byte b) {
        byte[] tempB = {b};
        return new String(bytesToHex(tempB));
    }
}
