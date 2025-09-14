package com.programmerpagi.toko_online.controller;

import com.programmerpagi.toko_online.contants.AppConstants;
import com.programmerpagi.toko_online.dto.LoginRequestDTO;
import com.programmerpagi.toko_online.dto.RegisterResponseDTO;
import com.programmerpagi.toko_online.dto.ResponseDTO;
import com.programmerpagi.toko_online.dto.LoginResponseDTO;
import com.programmerpagi.toko_online.model.User;
import com.programmerpagi.toko_online.service.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody User user) {

        RegisterResponseDTO newUser = userService.registerUser(user);

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", newUser));

    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);


        System.out.printf("Block Login Manually is calling");
        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {


//            var stringKey = Encoders.BASE64.encode(AppConstants.JWT_SECRET_KEY.getBytes());
            SecretKey secretKey = Keys.hmacShaKeyFor(AppConstants.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            String authorities = authenticationResponse.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

            String jwtTokenString = Jwts.builder().
                    issuer("PGPagi").
                    subject("JWT Token").
                    claim("username", authentication.getName()).
                    claim("authorities", authorities).
                    issuedAt(new Date()).
                    expiration(new Date(new Date().getTime() + 6000000)).
                    signWith(secretKey).compact();

            return ResponseEntity.status(HttpStatus.OK).header(AppConstants.JWT_TOKEN_HEADER,jwtTokenString).body(new LoginResponseDTO(jwtTokenString));
        }

        return ResponseEntity.status(400).body(null);
    }
}
