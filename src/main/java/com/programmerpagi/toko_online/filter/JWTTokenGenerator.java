package com.programmerpagi.toko_online.filter;

import com.programmerpagi.toko_online.contants.AppConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class JWTTokenGenerator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String authorities = String.join(",",authentication.getAuthorities().toString());
//        log.info("autititititi" + authorities);
        if (authentication != null) {
            var stringKey = Encoders.BASE64.encode(AppConstants.JWT_SECRET_KEY.getBytes());
            SecretKey secretKey = Keys.hmacShaKeyFor(stringKey.getBytes(StandardCharsets.UTF_8));
//            SecretKey secretKey = Keys.hmacShaKeyFor(AppConstants.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
//            log.info("ini blok generate token : " + authentication.getAuthorities());
//            String authorities = String.join(",",authentication.getAuthorities().toString());
//            System.out.println("autititititi" + authorities);
            System.out.println("ini adalah block generate token");
            String jwtTokenString = Jwts.builder().
                    issuer("PGPagi").
                    subject("JWT Token").
                    claim("username", authentication.getName()).
                    claim("authorities", authentication.getAuthorities().toString()).
                    issuedAt(new Date()).
                    expiration(new Date(new Date().getTime() + 6000000)).
                    signWith(secretKey, SignatureAlgorithm.HS256).compact();

            response.setHeader(AppConstants.JWT_TOKEN_HEADER,jwtTokenString);
        }



        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/v1/login");
    }
}
