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
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
		// http.authorizeRequests()
		// 	.antMatchers("/users").authenticated()
	    //         .anyRequest().permitAll()
	    //         .and()
	    //         .formLogin()
	    //             .usernameParameter("username")
	    //             .defaultSuccessUrl("/")
	    //             .permitAll()
	    //         .and()
	    //         .logout().logoutSuccessUrl("/").permitAll();
		http.sessionManagement().maximumSessions(1);
		http.authorizeRequests()
			.antMatchers(resources).permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/write/**").hasAnyRole("STATISTICS", "ADMIN")
			.antMatchers("/read/**").hasAnyRole("SECRETARY", "STATISTICS","ADMIN")
			.antMatchers("/main/**").hasRole("ADMIN")
			.antMatchers("/pdf/**").hasAnyRole("SECRETARY", "ADMIN")
			.antMatchers("/show/**").hasAnyRole("SECRETARY", "ADMIN")
			.antMatchers("/").permitAll()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/")
			.usernameParameter("username")
			.passwordParameter("password")
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
			.and()
			.exceptionHandling().accessDeniedPage("/403");
	}

}
