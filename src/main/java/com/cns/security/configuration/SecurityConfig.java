package com.cns.security.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cns.security.serviceImp.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private UserDetailsServiceImpl userDetailsServiceImpl;

	private JWTAuthFiltter jwtAuthFiltter;

	public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, JWTAuthFiltter jwtAuthFiltter) {
		super();
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.jwtAuthFiltter = jwtAuthFiltter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults())
				.authorizeHttpRequests(request -> request
						.requestMatchers("/api/v1/auth/**", "/api/v1//public/**")
						.permitAll().requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/user/**").hasAnyAuthority("USER")
						.requestMatchers("/moderator/**").hasAnyAuthority("ADMIN", "USER")
						.anyRequest().authenticated())
				.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFiltter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();

	}

	/*
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity
	 * httpSecurity) throws Exception{
	 * httpSecurity.csrf(AbstractHttpConfigurer::disable)
	 * .cors(Customizer.withDefaults()) .authorizeHttpRequests(request->
	 * request.requestMatchers( "/auth/**", "/public/**", "/swagger-ui.html",
	 * "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
	 * .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
	 * .requestMatchers("/user/**").hasAnyAuthority("USER")
	 * .requestMatchers("/adminuser/**").hasAnyAuthority("ADMIN", "USER")
	 * .anyRequest().authenticated())
	 * .sessionManagement(manager->manager.sessionCreationPolicy(
	 * SessionCreationPolicy.STATELESS))
	 * .authenticationProvider(authenticationProvider()).addFilterBefore(
	 * jwtAuthFilter, UsernamePasswordAuthenticationFilter.class ); return
	 * httpSecurity.build(); }
	 * 
	 * @Bean public AuthenticationProvider authenticationProvider(){
	 * DaoAuthenticationProvider daoAuthenticationProvider = new
	 * DaoAuthenticationProvider();
	 * daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService);
	 * daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); return
	 * daoAuthenticationProvider; }
	 * 
	 * @Bean public PasswordEncoder passwordEncoder(){ return new
	 * BCryptPasswordEncoder(); }
	 * 
	 * @Bean public AuthenticationManager
	 * authenticationManager(AuthenticationConfiguration
	 * authenticationConfiguration) throws Exception{ return
	 * authenticationConfiguration.getAuthenticationManager(); }
	 */

}
