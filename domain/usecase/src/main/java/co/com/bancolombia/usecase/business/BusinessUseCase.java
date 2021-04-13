package co.com.bancolombia.usecase.business;

import co.com.bancolombia.model.business.gateways.FileGateway;
import co.com.bancolombia.model.business.gateways.MessageGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BusinessUseCase {

    private final MessageGateway messageGateway;
    private final FileGateway fileGateway;

    public String doSomeBusinessRule(String someParam){
        return messageGateway.sendMessage(someParam);
    }

    public void doSomethingWithMessage(String message) {
        //TODO: Do some business rules with the message received
        fileGateway.uploadFile(message);
    }
}
