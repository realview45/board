package com.beyond.basic.board.common.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Config {
    @Value("${aws.credentials.access-key}")//순서 1이어야만 함
    private String accessKey;
    @Value("${aws.credentials.secret-key}")//순서 1이어야만 함
    private String secretKey;
    @Value("${aws.region}")//순서 1이어야만 함
    private String region;
    @Bean//s3에 접근하기위한 빈객체
    public S3Client client(){
        AwsBasicCredentials basicCredentials = AwsBasicCredentials.create(accessKey,secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(basicCredentials))
                .build();
    }
}
