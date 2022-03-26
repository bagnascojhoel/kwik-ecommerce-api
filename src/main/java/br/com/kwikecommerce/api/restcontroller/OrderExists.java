package br.com.kwikecommerce.api.restcontroller;

import br.com.kwikecommerce.api.order.OrderRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = OrderExists.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderExists {

    String message() default "{v.order.does-not-exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @RequiredArgsConstructor
    class Validator implements ConstraintValidator<OrderExists, Long> {

        private final OrderRepository orderRepository;

        @Override
        public boolean isValid(Long orderId, ConstraintValidatorContext constraintValidatorContext) {
            return orderRepository.existsById(orderId);
        }

    }

}
