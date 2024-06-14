package com.sky.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class AwsUtil {

    private String accessKeyId;
    private String secretKey;
    private String region;
    private String bucketName;

    /**
     * File upload
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {
        // Create S3Client instance
        S3Client s3Client = createS3Client();

        try {
            // Create PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();

            // Upload file
            s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(bytes));
        } catch (SdkServiceException sse) {
            log.error("Caught an SdkServiceException, which means your request made it to AWS S3, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message: {}", sse.getMessage());
        } catch (SdkClientException sce) {
            log.error("Caught an SdkClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS S3, "
                    + "such as not being able to access the network.");
            log.error("Error Message: {}", sce.getMessage());
        } finally {
            if (s3Client != null) {
                s3Client.close();
            }
        }

        // File access path rule: https://bucketName.s3.region.amazonaws.com/objectName
        String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, objectName);
        log.info("File uploaded to: {}", fileUrl);

        return fileUrl;
    }

    private S3Client createS3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}
