package com.digitalatmosphere.ausys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	String[] resources = new String[] { "/include/**", "/css/**", "/icons/**", "/img/**", "/js/**", "/layer/**" };

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 	.antMatchers("/users").authenticated()
		 	.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/write/**").hasAnyRole("STATISTICS", "ADMIN")
			.antMatchers("/read/**").hasAnyRole("SECRETARY", "STATISTICS","ADMIN")
			.antMatchers("/pdf/**").hasAnyRole("SECRETARY", "ADMIN")
			.antMatchers("/show/**").hasAnyRole("SECRETARY", "ADMIN")
	             .anyRequest().permitAll()
	             .and()
	             .formLogin()
	                 .usernameParameter("username")
	                 .defaultSuccessUrl("/")
	                 .permitAll()
	             .and()
	             .logout().logoutSuccessUrl("/").permitAll()
	             .and()
	 			.exceptionHandling().accessDeniedPage("/403");
	}

}
