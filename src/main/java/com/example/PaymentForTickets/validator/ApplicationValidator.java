package com.example.PaymentForTickets.validator;

import com.example.PaymentForTickets.domain.Application;
import com.example.PaymentForTickets.repository.ApplicationRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class ApplicationValidator implements Validator {

private final ApplicationRepository repository;

    public ApplicationValidator(ApplicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Application.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Application application = (Application) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "routeNumber", "Required");
        if (application.getRouteNumber() < 1000 || application.getRouteNumber() > 9999) {
            errors.rejectValue("routeNumber", "Range.routeNumber");
        }

        if (repository.findByRouteNumber(application.getRouteNumber()) != null) {
            errors.rejectValue("routeNumber", "Duplicate.routeNumber");
        }

    }
}
