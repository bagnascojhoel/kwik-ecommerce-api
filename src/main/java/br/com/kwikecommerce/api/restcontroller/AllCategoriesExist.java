package br.com.kwikecommerce.api.restcontroller;

import br.com.kwikecommerce.api.category.CategoryRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;


@Constraint(validatedBy = AllCategoriesExist.Validator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllCategoriesExist {

    String message() default "{v.category.some-does-not-exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @RequiredArgsConstructor
    class Validator implements ConstraintValidator<AllCategoriesExist, Collection<Long>> {

        private final CategoryRepository categoryRepository;

        @Override
        public boolean isValid(Collection<Long> categoriesIds, ConstraintValidatorContext constraintValidatorContext) {
            return categoriesIds.size() == categoryRepository.countByIdIn(categoriesIds);
        }

    }

}
