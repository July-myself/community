package com.july.community.service;

import com.july.community.dto.FileDTO;
import com.july.community.dto.ResultDTO;
import com.july.community.exception.CustomizeErrorCode;
import com.july.community.exception.CustomizeException;
import com.july.community.provider.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.undo.CannotUndoException;

@Service
public class FileService {
    @Autowired
    private OssUtil ossUtil;

    public FileDTO fileUpload(MultipartFile file) {
        if (file == null){
            throw new CustomizeException(CustomizeErrorCode.FILE_IS_EMPTY);
        }
        //上传
        String url=ossUtil.checkImage(file);
        //获取url
        FileDTO fileDTO = new FileDTO();
        fileDTO.setUrl(url);
        fileDTO.setSuccess(1);
        return fileDTO;

    }
}
