package com.financeiro.util;

import com.financeiro.util.validator.ValidarSenhaValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidarSenhaValidator.class })
public @interface ValidarSenha {
	
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Senhas não conferem!";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	String senha();
	
	String contraSenha();

}
