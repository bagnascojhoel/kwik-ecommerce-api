package br.com.kwikecommerce.api.application.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("aws")
public class AwsProperties {

    private S3Property s3;
    private String accessKey;
    private String secretKey;

    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class S3Property {

        private String bucket;
        private String region;

    }

}
