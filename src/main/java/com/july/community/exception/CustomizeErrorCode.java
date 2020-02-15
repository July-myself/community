package com.july.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("该问题不存在，请检查问题id是否正确");

    private String message;
    CustomizeErrorCode(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
