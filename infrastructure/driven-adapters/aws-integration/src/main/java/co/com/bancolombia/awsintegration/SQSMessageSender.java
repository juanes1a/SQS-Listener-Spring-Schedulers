package co.com.bancolombia.awsintegration;

import co.com.bancolombia.model.business.gateways.MessageGateway;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SQSMessageSender implements MessageGateway {

    private static final Logger logger = LoggerFactory.getLogger(SQSMessageSender.class);

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${application.send_queue_name}")
    private String senderQueueName;

    @Override
    public String sendMessage(String message) {
        try {
            queueMessagingTemplate.send(
                    senderQueueName,
                    MessageBuilder.withPayload(message).build());

            return "Mensaje Enviado";

        } catch (Exception ex) {
            logger.error("Error enviando mensaje: " + ex);
            return "Error enviando mensaje -> "+ message + ": /////VER LOG/////";
        }
    }
}
