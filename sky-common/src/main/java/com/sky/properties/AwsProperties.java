package com.sky.properties;

import com.aliyun.oss.model.CompleteMultipartUploadResult;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.aws")
@Data
public class AwsProperties {

    private String accessKeyId;
    private String secretKey;
    private String region;
    private S3 s3 = new S3();

    @Data
    public static class S3 {
        private String bucketName;
    }
}
