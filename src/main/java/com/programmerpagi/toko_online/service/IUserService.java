package com.programmerpagi.toko_online.service;

import com.programmerpagi.toko_online.dto.RegisterResponseDTO;
import com.programmerpagi.toko_online.model.User;

public interface IUserService {

    public RegisterResponseDTO registerUser(User user);


}
