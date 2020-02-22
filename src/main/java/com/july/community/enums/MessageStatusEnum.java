package com.july.community.enums;

public enum MessageStatusEnum {
    UNREAD(0),
    READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    MessageStatusEnum(int status) {
        this.status = status;
    }
}
