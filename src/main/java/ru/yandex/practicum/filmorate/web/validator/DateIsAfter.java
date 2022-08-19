package ru.yandex.practicum.filmorate.web.validator;

import ru.yandex.practicum.filmorate.web.validator.impl.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface DateIsAfter{
    String message() default "{DateIsAfter.invalid}";
    String current();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}