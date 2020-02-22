package com.july.community.controller;

import com.july.community.dto.FileDTO;
import com.july.community.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;


@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/file/upload")
    @ResponseBody
    private FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");

        FileDTO fileDTO = fileService.fileUpload(file);
        return fileDTO;

    }
}
