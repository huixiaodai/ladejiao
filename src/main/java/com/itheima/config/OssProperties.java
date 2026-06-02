package com.itheima.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component //让这个类交给 Spring 管理
@ConfigurationProperties(prefix = "aliyun.oss") //把 application.yml 里 aliyun.oss 下面的配置自动绑定到这个类的属性上
public class OssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
