package com.programmerpagi.toko_online.controller;

import com.programmerpagi.toko_online.dto.ResponseDTO;
import com.programmerpagi.toko_online.model.User;
import com.programmerpagi.toko_online.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody User user) {

        User newUser = userService.registerUser(user);

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", newUser));

    }
}
