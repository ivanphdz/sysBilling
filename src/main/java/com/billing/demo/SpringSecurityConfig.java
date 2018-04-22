package com.billing.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.billing.demo.auth.LoginSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		//build.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/client/","/css/**","/js/**","/images/**","/client/list").permitAll()
		.antMatchers("/client/view/**").hasAnyRole("USER")
		.antMatchers("/client/uploads/**").hasAnyRole("USER")
		.antMatchers("/client/create/**").hasAnyRole("ADMIN")
		.antMatchers("/client/delete/**").hasAnyRole("ADMIN")
		.antMatchers("/invoice/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().successHandler(successHandler).loginPage("/login").permitAll()
		.and().logout().permitAll()
		.and().exceptionHandling().accessDeniedPage("/error_403")
		.and().formLogin().defaultSuccessUrl("/client/list");
	}
	
	
}
