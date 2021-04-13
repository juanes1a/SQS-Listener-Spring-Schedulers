package co.com.bancolombia.config;

import co.com.bancolombia.model.business.gateways.FileGateway;
import co.com.bancolombia.model.business.gateways.MessageGateway;
import co.com.bancolombia.usecase.business.BusinessUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public BusinessUseCase createBusinessUseCase(MessageGateway messageGateway, FileGateway fileGateway) {
        return new BusinessUseCase(messageGateway, fileGateway);
    }
}
