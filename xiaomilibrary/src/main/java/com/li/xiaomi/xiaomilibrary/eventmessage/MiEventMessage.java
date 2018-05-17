package com.li.xiaomi.xiaomilibrary.eventmessage;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/9
 * 内容：
 * 最后修改：
 */

public class MiEventMessage {
    public static final int GET_PHOTO_FILE_URL = 0x511;
    public static final int ExitApp = 0x512;//退出App
    public static final int CheckApp = 0x513;//是都正在更新

    int messageType;
    String messageString;
    int messageInt;
    boolean messageFlag;

    public MiEventMessage(int messageType) {
        this.messageType = messageType;
    }

    public MiEventMessage(int messageType, String messageString) {
        this.messageType = messageType;
        this.messageString = messageString;
    }

    public MiEventMessage(int messageType, int messageInt) {
        this.messageType = messageType;
        this.messageInt = messageInt;
    }

    public MiEventMessage(int messageType, boolean messageFlag) {
        this.messageType = messageType;
        this.messageFlag = messageFlag;
    }


    public boolean isMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(boolean messageFlag) {
        this.messageFlag = messageFlag;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    public int getMessageInt() {
        return messageInt;
    }

    public void setMessageInt(int messageInt) {
        this.messageInt = messageInt;
    }
}
