package co.com.bancolombia.messagereceiver.business;

import co.com.bancolombia.usecase.business.BusinessUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BusinessMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(BusinessMessageListener.class);

    private final SqsClient sqsClient;
    private final BusinessUseCase businessUseCase;


    @Value("${application.receive_queue_name}")
    private String queueName;


    @Scheduled(cron = "0 0/1 * 1/1 * *")
    public void receiveMessages() {

        try {
            GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                    .queueName(queueName)
                    .build();

            String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();

            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest
                    .builder()
                    .queueUrl(queueUrl)
                    .build();

            if (sqsClient.receiveMessage(receiveMessageRequest).hasMessages()) {
                List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
                for (Message m : messages) {
                    businessUseCase.doSomethingWithMessage(m.body());
                    logger.info("Receiving message: " + m.body());
                }
            }
        } catch (Exception ex) {
            logger.error("Error receiving message", ex);

        }

    }
}
