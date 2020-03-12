package com.financeiro.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SpringMailConfig {
	
	/*@Autowired
	private Environment env;*/
	
	@Bean
	public JavaMailSender mailSender() {
		
		JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
		
		emailSender.setHost("smtp.gmail.com");
		emailSender.setPort(587);
		emailSender.setUsername("fsergio.santos@gmail.com");
		emailSender.setPassword("mks18mks");
		
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.debug", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		emailSender.setJavaMailProperties(props);
		
		return emailSender;
	}

}
