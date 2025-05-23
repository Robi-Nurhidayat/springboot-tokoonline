package com.programmerpagi.toko_online.filter;

import com.programmerpagi.toko_online.contants.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JWTTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(AppConstants.JWT_TOKEN_HEADER);

        if (jwtToken != null) {

            SecretKey secretKey = Keys.hmacShaKeyFor(AppConstants.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwtToken).getPayload();
            String username = String.valueOf(claims.get("username"));
            System.out.println("block token validator running");
            System.out.println("isi dari role " + claims.get("authorities"));
            System.out.println("isi dari username " + username);
            String authorities = String.valueOf(claims.get("authorities"));
            log.info(authorities);
            List<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority("ROLE_"+claims.get("authorities")));
            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null, list);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }




        filterChain.doFilter(request,response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/login");
    }
}
