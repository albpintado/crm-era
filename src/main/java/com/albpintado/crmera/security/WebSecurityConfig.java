package com.albpintado.crmera.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Spring Security main configuration class
@Configuration
public class WebSecurityConfig {

  /*
  Get a IUserDetails object to check if an account that
  matches the email sent exists in DB.
  */
  private final IUserDetails userDetailsService;

  /*
  Get the JWTAuthorizationFilter to filter the request in
  order to get the access token.
  */
  private final JWTAuthorizationFilter jwtAuthorizationFilter;

  public WebSecurityConfig(IUserDetails IUserDetails, JWTAuthorizationFilter jwtAuthorizationFilter) {
    this.userDetailsService = IUserDetails;
    this.jwtAuthorizationFilter = jwtAuthorizationFilter;
  }

  /*
  Make the filter that imports the filter to manage the request
  in order to authenticate the user.
  */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
    JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
    jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
    jwtAuthenticationFilter.setFilterProcessesUrl("/login");

    /*
    Returns the filter that matches against the http request
    filters it to authenticate the user.
    It disables the csrf to avoid that it will cloak the JWT
    authentication. Also, it permits all request just for
    authenticated users, but /login permits all requests without
    authentication. It manages the session creating one if any
    is already created and passes the filters before build the chain.
    */
    return http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(jwtAuthenticationFilter)
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  /*
  Instantiate the manager that handles the authentication
  process for the user, checking its existence with the
  password matching function with the encoder.
   */
  @Bean
  AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(this.userDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
  }

  /*
  Create a password encoder for the user's password using
  BCrypt password-hashing function.
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
