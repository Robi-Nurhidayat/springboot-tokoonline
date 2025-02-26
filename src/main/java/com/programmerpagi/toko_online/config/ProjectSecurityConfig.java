package com.programmerpagi.toko_online.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.lang.reflect.Array;
import java.util.Arrays;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
                corsConfiguration.setAllowedMethods(Arrays.asList("*"));
                corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
                return corsConfiguration;
            }
        }));
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll());


        http.httpBasic(Customizer.withDefaults());
       return http.build();
    }
}
