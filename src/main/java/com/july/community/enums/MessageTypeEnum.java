package com.july.community.enums;

public enum MessageTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    MessageTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type){
        for (MessageTypeEnum messageTypeEnum :MessageTypeEnum.values()){
            if (messageTypeEnum.getType() == type){
                return messageTypeEnum.getName();
            }
        }
        return "";
    }
}
