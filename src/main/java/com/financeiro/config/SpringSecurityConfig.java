package com.financeiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

import com.financeiro.config.csrf.CookieCsrfFilter;
import com.financeiro.security.UserDetailsServiceImpl;
import com.financeiro.security.custom.CustomAccessDeniedHandler;
import com.financeiro.security.custom.CustomAuthenticationFailureHandler;
import com.financeiro.security.custom.CustomAuthenticationProvider;
import com.financeiro.security.custom.CustomAuthenticationSuccessHandler;
import com.financeiro.security.custom.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses=UserDetailsServiceImpl.class)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;

	@Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	@Autowired
	private CustomAccessDeniedHandler customAccessDaniedHandler;
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
      	auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/resources/**","/js/**","/static/**","/css/**","/images/**").permitAll() 
	        .antMatchers("/").permitAll()
	        .antMatchers("/login").permitAll()
	        .antMatchers("/registrar/**").permitAll()
	        .antMatchers("/recuperar/**").permitAll()
	        .antMatchers("/pessoa/**").hasAnyRole("ADMINISTRADOR","USUARIO","VISITANTE")
	        .antMatchers("/telefone/**").hasAnyRole("ADMINISTRADOR","USUARIO","VISITANTE")
	        .antMatchers("/trocar/**").hasAnyAuthority("ADMINISTRADOR","USUARIO")
			.antMatchers("/usuario/**").hasRole("ADMINISTRADOR")
			.antMatchers("/permissao/**").hasRole("ADMINISTRADOR")
			.antMatchers("/role/**").hasRole("ADMINISTRADOR")
			.antMatchers("/direitos/**").hasRole("ADMINISTRADOR")
	        .anyRequest()
	        .authenticated();
	    http.formLogin()
            .loginPage("/login")
          /*.failureUrl("/login?error=false")
            .defaultSuccessUrl("/home",true)*/
            .usernameParameter("email")
            .passwordParameter("password")
   			.successHandler(customAuthenticationSuccessHandler)
   		    .failureHandler(customAuthenticationFailureHandler)
            .permitAll();
        http.logout()
        	.logoutSuccessHandler(customLogoutSuccessHandler)
            .logoutUrl("/login")
		    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.clearAuthentication(true);
        http.exceptionHandling()
        	.accessDeniedHandler(customAccessDaniedHandler);
//            .accessDeniedPage("/403");
        http.sessionManagement()
  		    .invalidSessionUrl("/login")
			.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			.maximumSessions(1).sessionRegistry(sessionRegistry()).and()
		    .sessionFixation().none();
		http.csrf()
		 	.csrfTokenRepository(csrfTokenRepository())
		 	.and()
		 	.addFilterAfter(new CookieCsrfFilter(), CsrfFilter.class);
        http.cors();
    }
    
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(CookieCsrfFilter.XSRF_TOKEN_HEADER_NAME);
        return repository;
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
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
        customAuthenticationProvider.setUserDetailsService(userDetailsService);
        customAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return customAuthenticationProvider;
    }    

 }
