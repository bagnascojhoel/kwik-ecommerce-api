package br.com.kwikecommerce.api.restcontroller;

import br.com.kwikecommerce.api.product.ProductRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = ProductExists.Validator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductExists {
    String message() default "{v.product.does-not-exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @RequiredArgsConstructor
    class Validator implements ConstraintValidator<ProductExists, Long> {

        private final ProductRepository productRepository;

        @Override
        public boolean isValid(Long productId, ConstraintValidatorContext context) {
            return productRepository.existsById(productId);
        }

    }

}
