package com.fpt.capstone.tourism.config;

import com.fpt.capstone.tourism.service.impl.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JWTAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/v1/swagger-ui/**", "/v1/v3/api-docs/**", "/v1/swagger-resources/**", "/v1/swagger-ui.html", "/v1/webjars/**").permitAll()
                        .requestMatchers("/public/**", "/auth/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/forgot-password", "/reset-password").permitAll()
                        .requestMatchers("/v1/ceo/**").hasAnyAuthority("CEO")
                        .requestMatchers("/v1/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/v1/marketing/**").hasAnyAuthority("MARKETER")
                        .requestMatchers("/v1/sales/**").hasAnyAuthority("SALESMAN")
                        .requestMatchers("/v1/operator/**").hasAnyAuthority("OPERATOR")
                        .requestMatchers("/v1/accountant/**").hasAnyAuthority("ACCOUNTANT")
                        .requestMatchers("/v1/service-provider/**").hasAnyAuthority("SERVICE_PROVIDER")
                        .requestMatchers("/v1/head-of-business/**").hasAnyAuthority("HEAD_OF_BUSINESS")
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                )
                .rememberMe(rememberMe ->
                        rememberMe.key("tempKey")
                                .rememberMeCookieName("remember-me")
                                .tokenValiditySeconds(7 * 24 * 60 * 60)
                                .rememberMeParameter("remember-me")
                                .useSecureCookie(false)
                                .userDetailsService(userDetailsService)
                );
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
