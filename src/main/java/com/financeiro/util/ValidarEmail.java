package com.financeiro.util;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.financeiro.util.validator.EmailValidator;

@Target({ TYPE, FIELD, ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidarEmail {

    String message() default "E-mail inválido ou já está cadastrado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
