package com.financeiro;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
@EntityScan(basePackages="com.financeiro.model")
public class FinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApplication.class, args);
	}
	
	@Bean  
	public FixedLocaleResolver localeResolver(){  
	    return new FixedLocaleResolver(new Locale("pt", "BR"));  
	}  
	
	/*@Bean
	public Java8TimeDialect java8TimeDialect() {
	   return new Java8TimeDialect();
	}*/
		
}

