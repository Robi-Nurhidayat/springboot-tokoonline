package com.programmerpagi.toko_online.config;

import com.programmerpagi.toko_online.filter.CsrfCookieFilter;
import com.programmerpagi.toko_online.filter.JWTTokenGenerator;
import com.programmerpagi.toko_online.filter.JWTTokenValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");

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

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(csrf -> csrf.
                        csrfTokenRequestHandler(requestAttributeHandler)
                        .ignoringRequestMatchers("/api/v1/register","/api/v1/login")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                );
        http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
//        http.addFilterAfter(new JWTTokenGenerator(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JWTTokenValidator(), BasicAuthenticationFilter.class);
//        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/login","/api/v1/register").permitAll());
        // products
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(HttpMethod.GET,"/api/v1/products","/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/v1/products/**").authenticated()
                .requestMatchers(HttpMethod.DELETE,"/api/v1/products/**").authenticated());



//        http.httpBasic(Customizer.withDefaults());
       return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){

        CustomUsernamePwdAuthenticationProvider authenticationProvider = new CustomUsernamePwdAuthenticationProvider(userDetailsService,passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;

    }
}
