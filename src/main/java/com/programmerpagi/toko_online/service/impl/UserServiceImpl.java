package com.programmerpagi.toko_online.service.impl;

import com.programmerpagi.toko_online.model.User;
import com.programmerpagi.toko_online.repository.UserRepository;
import com.programmerpagi.toko_online.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    @Override
    public User registerUser(User user) {
        User newUser = userRepository.save(user);

        return newUser;
    }
}
