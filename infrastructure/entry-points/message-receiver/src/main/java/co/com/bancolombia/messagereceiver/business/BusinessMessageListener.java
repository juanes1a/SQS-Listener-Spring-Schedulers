package co.com.bancolombia.messagereceiver.business;

import co.com.bancolombia.usecase.business.BusinessUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(BusinessMessageListener.class);

    private final BusinessUseCase businessUseCase;

    @SqsListener("${application.receive_queue_name}")
    public void receiveMessages(String messageString){
        logger.info("Receiving message: "+messageString);
        businessUseCase.doSomethingWithMessage(messageString);
    }

}
