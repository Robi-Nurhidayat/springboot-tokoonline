package com.programmerpagi.toko_online.service;

import com.programmerpagi.toko_online.dto.CartRequestDTO;
import com.programmerpagi.toko_online.dto.CartResponseDTO;

import java.util.List;

public interface ICartService {

    List<CartResponseDTO> findAll();

    List<CartResponseDTO> findByUserId(Long userId);

    CartResponseDTO addCart(CartRequestDTO cartRequestDTO);

}
