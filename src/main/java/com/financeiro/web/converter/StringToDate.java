package com.financeiro.web.converter;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;

@Configuration
public class StringToDate {
	
	@Bean
	public DateTimeFormatterRegistrar getDateTimeFormatter() {
		
		DefaultFormattingConversionService conversion = new DefaultFormattingConversionService();
	    DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
		dateTimeFormatter.registerFormatters(conversion);
		return dateTimeFormatter;
	}
	
}
