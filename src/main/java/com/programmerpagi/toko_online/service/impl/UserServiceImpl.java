package com.programmerpagi.toko_online.service.impl;

import com.programmerpagi.toko_online.dto.RegisterResponseDTO;
import com.programmerpagi.toko_online.exception.AlreadyExistsException;
import com.programmerpagi.toko_online.model.User;
import com.programmerpagi.toko_online.repository.UserRepository;
import com.programmerpagi.toko_online.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public RegisterResponseDTO registerUser(User user) {

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            throw new AlreadyExistsException("Email already exists");
        }


        String passwordEncode = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncode);
        User newUser = userRepository.save(user);

        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setId(newUser.getId());
        registerResponseDTO.setName(newUser.getName());
        registerResponseDTO.setEmail(newUser.getEmail());
        registerResponseDTO.setRole(newUser.getRole());

        return registerResponseDTO;
    }
}
