package fr.imt.musically.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BodyValidator {

    private final Validator validator;

    public BodyValidator(Validator validator) {
        this.validator = validator;
    }


    public void validateBodyRequest(RequestBody requestBody) throws ConstraintViolationException {
        Set<ConstraintViolation<RequestBody>> violations = validator.validate(requestBody);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RequestBody> violation : violations) {
                sb.append(violation.getMessage());
                sb.append("\n");
            }

            throw new ConstraintViolationException(sb.toString(), violations);
        }
    }
}
