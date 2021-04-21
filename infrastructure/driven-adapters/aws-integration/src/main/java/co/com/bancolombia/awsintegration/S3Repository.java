package co.com.bancolombia.awsintegration;

import co.com.bancolombia.model.business.gateways.FileGateway;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class S3Repository implements FileGateway {

    private static final Logger logger = LoggerFactory.getLogger(S3Repository.class);

    @Autowired
    private final S3Client s3Client;

    @Value("${application.s3_bucket_name}")
    private String bucketName;

    @Override
    public void uploadFile(String key) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put(key, "test");

            PutObjectRequest putOb = PutObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(key)
                    .metadata(metadata)
                    .build();

            s3Client.putObject(putOb,
                    RequestBody.fromBytes(getObjectFile("C:/Clean/Podem_Test/deployment/Dockerfile")));

        } catch (Exception ex) {
            logger.error("ERROR cargando documento", ex);
        }
    }

    private byte[] getObjectFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }
}
