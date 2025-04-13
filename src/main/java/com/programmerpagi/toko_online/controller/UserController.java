package com.programmerpagi.toko_online.controller;

import com.programmerpagi.toko_online.dto.RegisterResponseDTO;
import com.programmerpagi.toko_online.dto.ResponseDTO;
import com.programmerpagi.toko_online.dto.LoginResponseDTO;
import com.programmerpagi.toko_online.model.User;
import com.programmerpagi.toko_online.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody User user) {

        RegisterResponseDTO newUser = userService.registerUser(user);

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", newUser));

    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody ResponseDTO.LoginRequestDTO loginRequestDTO) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(authenticationResponse.getName(),authenticationResponse.getCredentials().toString()));
        }

        return ResponseEntity.status(400).body(null);
    }
}
