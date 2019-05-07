package com.financeiro.util;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.financeiro.util.validator.CpfCnpjValidator;

@Target({ TYPE, FIELD, ANNOTATION_TYPE, METHOD })
@Retention(RUNTIME)
@Constraint(validatedBy = CpfCnpjValidator.class)
@Documented
public @interface FindCpfCnpj {
	
    String message() default "CPF / CNPJ Já Cadastrado.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
