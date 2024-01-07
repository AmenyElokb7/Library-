package com.example.library_backend.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final ObjectMapper objectMapper; // Inject ObjectMapper to serialize JSON
    UserDetailsService userDetailsService;
    PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:3000","http://127.0.0.1:8081","http://localhost:3000","http://localhost:8082","http://127.0.0.1:8082","http://localhost:8081","http://192.168.49.2:30606"));
                            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
                            config.setAllowCredentials(true);
                            return config;
                        }))
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                            .requestMatchers("/api/books/secure/**", "/api/messages/secure/**", "/api/reviews/secure/**").authenticated();
                           // .requestMatchers("/api/login").permitAll();
                });
        http.httpBasic(Customizer.withDefaults());
        http.userDetailsService(userDetailsService);
        http.csrf(c-> c.disable());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();

    }
  /*  @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    } */

}
