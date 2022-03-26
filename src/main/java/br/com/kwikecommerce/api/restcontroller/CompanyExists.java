package br.com.kwikecommerce.api.restcontroller;

import br.com.kwikecommerce.api.company.CompanyRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = CompanyExists.Validator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompanyExists {

    String message() default "{v.company.does-not-exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @RequiredArgsConstructor
    class Validator implements ConstraintValidator<CompanyExists, Long> {

        private final CompanyRepository companyRepository;

        @Override
        public boolean isValid(Long companyId, ConstraintValidatorContext constraintValidatorContext) {
            return companyRepository.existsById(companyId);
        }

    }

}
