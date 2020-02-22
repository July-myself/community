package com.july.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    NO_LOGIN(3001,"未登录，请先登录再进行操作"),
    UNKNOWN_EXCEPTION(3002,"出现未知异常，联系一下开发人员吧"),
    QUESTION_NOT_FOUND(2001,"该问题不存在，请检查问题id是否正确"),
    UPDATE_FAIL_QUESTION_NUT_FOUND(2002,"更新问题失败。数据库中可能已不存在该问题..."),
    TARGET_PARAM_NOT_FOUND(2003,"未选中任何问题或评论进行回复"),
    TYPE_PARAM_WRONG(2004,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2005,"你回复的评论不存在" ),
    COMMENT_CONTENT_NULL(2006,"回复内容不能为空"),
    MESSAGE_NOT_FOUND(2007,"这条信息不存在"),
    READ_MESSAGE_FAIL(2008,"这条信息不是你的，你无权阅读"),
    IMAGE_TOO_MAX(2009,"图片太大，无法上传"),
    IMAGE_UPLOAD_FAIL(2010,"图片上传失败"),
    FILE_IS_EMPTY(2011,"空文件，上传失败");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
