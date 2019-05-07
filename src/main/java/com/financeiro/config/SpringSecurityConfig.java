package com.financeiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.financeiro.security.AuthenticationSuccessHandlerImpl;
import com.financeiro.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses=UserDetailsServiceImpl.class)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;

	@Autowired
    private AuthenticationSuccessHandlerImpl successHandler;
	
    /*@Autowired
    private DataSource dataSource;*/
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      	 auth.userDetailsService(userDetailsService)
      	     .passwordEncoder(passwordEncoder());
	  /*    	 .and()
	     auth.authenticationProvider(authenticationProvider())
	         .jdbcAuthentication()
	         .dataSource(dataSource);*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        	    .antMatchers("/resources/**","/js/**","/static/**","/css/**","/images/**").permitAll() 
	            .antMatchers("/").permitAll()
	            .antMatchers("/login").permitAll()
	            .antMatchers("/registrar/**").permitAll()
	            .antMatchers("/recuperar/**").permitAll()
	            .antMatchers("/rolepermissao/**").permitAll()
	            .antMatchers("/pessoa/**").hasAnyRole("ADMINISTRADOR","USUARIO","VISITANTE")
	            .antMatchers("/telefone/**").hasAnyRole("ADMINISTRADOR","USUARIO","VISITANTE")
	            .antMatchers("/trocar/**").hasAnyAuthority("ADMINISTRADOR","USUARIO","VISITANTE")
			    .antMatchers("/usuario/**").hasRole("ADMINISTRADOR")
			    .antMatchers("/permissao/**").hasRole("ADMINISTRADOR")
			    .antMatchers("/role/**").hasRole("ADMINISTRADOR")
			    .antMatchers("/direitos/**").hasRole("ADMINISTRADOR")
	            .anyRequest()
	            .authenticated()
	            .and()
	        .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=false")
                /*.defaultSuccessUrl("/home",true)*/
                .usernameParameter("email")
                .passwordParameter("password")
   			 	.successHandler(successHandler)
                .permitAll()
                .and()
             .logout()
             	.logoutUrl("/login")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
             	.and()
             .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
             .sessionManagement()
			 	.invalidSessionUrl("/login")
			 	.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			 	.maximumSessions(1).sessionRegistry(sessionRegistry()).and()
		        .sessionFixation().none()
			    .and()
             .csrf();
    }
    

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/resources/**");
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
    
    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }*/
    
        
 }
