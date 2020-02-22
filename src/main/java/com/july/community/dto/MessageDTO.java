package com.july.community.dto;

import com.july.community.model.User;
import lombok.Data;

@Data
public class MessageDTO {
    private Long id;
    private Long timeCreate;
    private Integer status;
    private Long notifiedId;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
