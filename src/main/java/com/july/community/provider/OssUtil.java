package com.july.community.provider;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.july.community.dto.ResultDTO;
import com.july.community.exception.CustomizeErrorCode;
import com.july.community.exception.CustomizeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 阿里云oss服务器工具类
 */
@Component
public class OssUtil {

    @Value("${aliyun.endpoint}")
    private String endpoint;
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.bucketName}")
    private String bucketName;

    //文件存储目录
    @Value("${aliyun.filehost}")
    private String filedir;

    //上传图片
    public String uploadImg20ss(MultipartFile file){
        if (file.getSize() > 1024 * 1024 * 20){
            throw new CustomizeException(CustomizeErrorCode.IMAGE_TOO_MAX);
        }
        String originalFilename = file.getOriginalFilename();
        String subString = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name=random.nextInt(10000)+System.currentTimeMillis()+subString;
        try{
            InputStream inputStream = file.getInputStream();
            this.uploadFile20SS(inputStream,name);
            return  name;
        }catch (Exception e){
            throw new CustomizeException(CustomizeErrorCode.IMAGE_UPLOAD_FAIL);
        }
    }

    //上传图片获取fileUrl
    private String uploadFile20SS(InputStream instream,String fileName){
        String ret="";
        try {
            //创建上传object的Metadata
            ObjectMetadata objectMetadata=new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma","no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename="+fileName);
            //上传文件
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putRequest = ossClient.putObject(bucketName,filedir+fileName,instream,objectMetadata);
            ret=putRequest.getETag();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                if (instream != null){
                    instream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return ret;
    }

    //
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    //获取图片路径
    public String getImgUrl(String fileUrl){
        if (!StringUtils.isEmpty(fileUrl)){
            String[] split =fileUrl.split("/");
            String url=this.getUrl(this.filedir+split[split.length-1]);
            return url;
        }
        return null;
    }

    //获取url链接
    public String getUrl(String key){
        //设置Url过期时间为10年
        Date expiration =new Date(new Date().getTime() + 36001 * 1000 * 24 * 365 * 10);
        //生成URL
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        URL url=ossClient.generatePresignedUrl(bucketName,key,expiration);
        if (url != null){
            return url.toString();
        }
        return null;
    }

    //多图上传
    public String checkList(List<MultipartFile> fileList){
        String fileUrl="";
        String str = "";
        String photoUrl = "";
        for (int i =0;i<fileList.size();i++){
            fileUrl = uploadImg20ss(fileList.get(i));
            str = getImgUrl(fileUrl);
            if (i==0){
                photoUrl = str;
            }else {
                photoUrl+=","+str;
            }

        }
        return photoUrl.trim();
    }

    //单个图片上传
    public String checkImage(MultipartFile file){
        String fileUrl = uploadImg20ss(file);
        String str = getImgUrl(fileUrl);
        return str.trim();
    }



}
