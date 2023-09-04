package by.teachmeskills.shop.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
        String message() default " Длина пароля должна быть не короче 4 символов. Пароль должен содержать как минимум одну цифру," +
                " одну заглавную букву, одну букву нижнего регистра, один специальный символ!";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};

}
