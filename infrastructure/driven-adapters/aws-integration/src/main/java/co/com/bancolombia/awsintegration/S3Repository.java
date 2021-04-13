package co.com.bancolombia.awsintegration;

import co.com.bancolombia.model.business.gateways.FileGateway;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
@RequiredArgsConstructor
public class S3Repository implements FileGateway {

    private static final Logger logger = LoggerFactory.getLogger(S3Repository.class);

    @Autowired
    private final AmazonS3Client s3Client;

    @Value("${application.s3_bucket_name}")
    private String bucketName;

    @Override
    public void uploadFile(String key) {
        try {

        } catch (Exception ex) {

        }
        s3Client.putObject(
                bucketName,
                key,
                new File("")
        );


    }
}
