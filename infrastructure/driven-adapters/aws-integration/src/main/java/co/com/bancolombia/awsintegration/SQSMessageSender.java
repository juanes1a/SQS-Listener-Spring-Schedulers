package co.com.bancolombia.awsintegration;

import co.com.bancolombia.model.business.gateways.MessageGateway;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
@RequiredArgsConstructor
public class SQSMessageSender implements MessageGateway {

    private static final Logger logger = LoggerFactory.getLogger(SQSMessageSender.class);

    private final SqsClient sqsClient;

    @Value("${application.send_queue_name}")
    private String senderQueueName;

    @Override
    public String sendMessage(String message) {
        try {
            GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                    .queueName(senderQueueName)
                    .build();

            String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();

            SendMessageRequest sendMessageRequest = SendMessageRequest
                    .builder()
                    .queueUrl(queueUrl)
                    .messageBody(message)
                    .build();


            sqsClient.sendMessage(sendMessageRequest);
            return "Mensaje Enviado";

        } catch (Exception ex) {
            logger.error("Error enviando mensaje: " + ex);
            return "Error enviando mensaje -> "+ message + ": /////VER LOG/////";
        }
    }
}
