package com.wwm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;


@Configuration
@EnableWebSecurity
@ConditionalOnProperty(prefix="spring.security", name ="enabled", matchIfMissing=false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	    private UserDetailsService userService;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private SimpleUrlLogoutSuccessHandler logoutHandler;
	    
	    
	    @Bean    
	    public BCryptPasswordEncoder passwordEncoder() {
	        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	        return bCryptPasswordEncoder;
	    }
	    
	    @Bean
	    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userService) {
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userService);
	        authenticationProvider.setPasswordEncoder(passwordEncoder);
	        return authenticationProvider;
	    }
	    
	    
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) { 
	        auth.authenticationProvider(authenticationProvider(userService));
	    }
	 
	    
	    @Override
	    public void configure(WebSecurity web) {
	        //web.ignoring().antMatchers("*.css", "*.js", "/img/**", "/*.ico", "/js/*", "/manifest.json");
	    	web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/lib/**", "/fonts/**");
	    }
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	
	        CharacterEncodingFilter filter = new CharacterEncodingFilter();
	        filter.setEncoding("UTF-8");
	        filter.setForceEncoding(true);
	        http.addFilterBefore(filter,CsrfFilter.class);
	        
	        http
	        	.headers().frameOptions().disable()
	        	.and()
	            .authorizeRequests()
	            	.antMatchers("/test*").permitAll()
	                .antMatchers("/loginPage").permitAll()
	                .antMatchers("/login/findUserId.json").permitAll()
	                .antMatchers("/login/findUserPassword.json").permitAll()
	                .antMatchers("/expiredId.action").permitAll()
	                .antMatchers("/login").permitAll()
	                .antMatchers("/apply/*/*.json").permitAll()
	                .antMatchers("/apply/*.action").permitAll()
	                .antMatchers("/json/common/checkSession.json").permitAll()
	                .antMatchers("/registration").permitAll()
	        		.antMatchers("/externCall.action").permitAll()
	                .antMatchers("/main").hasAuthority("ADMIN")
	                .antMatchers("/home").hasAuthority("ADMIN") 
	            .anyRequest()
	                .authenticated()
	                .and().csrf().disable()
	            .formLogin() 
	                .loginPage("/loginPage")
	                .loginProcessingUrl("/login")
	                .usernameParameter("loginId")
	                .passwordParameter("password")
	                .defaultSuccessUrl("/dashboard/facility/dashboard.action", true)
	            .and()
	                .exceptionHandling()
	                .accessDeniedPage("/access-denied")
	            .and()
	                .logout()
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                .logoutSuccessHandler(logoutHandler)
	                .invalidateHttpSession(true)
	                .clearAuthentication(true)
	                .deleteCookies("JSESSIONID")
	                .logoutSuccessUrl("/")
	            .and()
	            .sessionManagement()
	            .maximumSessions(1) 
	            .maxSessionsPreventsLogin(false) 
	            .expiredUrl("/loginPage.action")
	            .sessionRegistry(sessionRegistry()); 
	          
	    }
	    
	    @Bean
	    public SessionRegistry sessionRegistry() {
	        return new SessionRegistryImpl();
	    }
	    
	    @Bean 
	    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() { 
	    	return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher()); 
	    }


}
