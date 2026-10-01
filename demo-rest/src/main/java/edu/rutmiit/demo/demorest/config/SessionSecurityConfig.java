package edu.rutmiit.demo.demorest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@Profile("session-auth")
@EnableMethodSecurity
public class SessionSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users() {
        PasswordEncoder encoder = passwordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("auditor")
                        .password(encoder.encode("auditor"))
                        .roles("READER")
                        .build(),
                User.withUsername("manager")
                        .password(encoder.encode("manager"))
                        .roles("READER", "EDITOR")
                        .build()
        );
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName(null);

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(requestHandler)
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/api/clients", true)
                )
                .logout(logout -> logout
                        .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                )
                .sessionManagement(session -> session
                        .sessionFixation(fixation -> fixation.migrateSession())
                        .maximumSessions(1)
                );

        return http.build();
    }
}