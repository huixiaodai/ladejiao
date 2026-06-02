package com.itheima.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.itheima.config.OssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class AliOssUtil {
    @Autowired
    private OssProperties ossProperties;

    public String upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String suffix = ""; //后缀
        if (originalFilename != null && originalFilename.contains(".")){
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String objectName = UUID.randomUUID().toString().replace("-", "") + suffix;
        //“从上传文件里取出内容流，把它交给上传方法；上传完以后，顺手把这个流自动关掉。”
        try (InputStream inputStream = file.getInputStream()){ //1. 从 MultipartFile 里拿到文件内容流(把前端传来的文件，变成一个可以读取内容的输入流。)
            return upload(objectName,inputStream);
        }
    }

    public String upload(String objectName, InputStream inputStream) throws Exception {
        OSS ossClient = null; //先准备一个阿里云 OSS 客户端变量，等下真正创建连接时给它赋值。
        try {
            //登录并连上阿里云 OSS 服务。”
            ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
            //把 inputStream 这份文件内容，上传到 bucketName 这个桶里，并保存成 objectName 这个名字。
            ossClient.putObject(ossProperties.getBucketName(), objectName, inputStream);
            //变成：oss-cn-beijing.aliyuncs.com
            String endpoint = ossProperties.getEndpoint().replace("https://", "")
                    .replace("http://", "");
            //https://javaai-clever.oss-cn-beijing.aliyuncs.com/abc123.jpg
            return "https://" + ossProperties.getBucketName() + "." + endpoint + "/" + objectName;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}
