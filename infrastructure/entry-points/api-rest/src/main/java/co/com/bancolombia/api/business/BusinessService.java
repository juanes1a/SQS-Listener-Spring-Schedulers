package co.com.bancolombia.api.business;

import co.com.bancolombia.usecase.business.BusinessUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/business", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class BusinessService {

    private final BusinessUseCase businessUseCase;


    @GetMapping(path = "/do")
    public String send(@RequestParam String someString) {
        return businessUseCase.doSomeBusinessRule(someString);
    }
}
