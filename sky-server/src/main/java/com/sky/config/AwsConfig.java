package com.sky.config;

import com.sky.properties.AwsProperties;
import com.sky.utils.AwsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AwsConfig {

    @Bean
    @ConditionalOnMissingBean
    public AwsUtil awsUtil(AwsProperties awsProperties) {
        log.info("开始创建Aws文件上传工具类对象: {}", awsProperties);
        return new AwsUtil(awsProperties.getAccessKeyId(),
                awsProperties.getSecretKey(),
                awsProperties.getRegion(),
                awsProperties.getS3().getBucketName());
    }
}
