package com.programmerpagi.toko_online.controller;

import com.programmerpagi.toko_online.dto.CartRequestDTO;
import com.programmerpagi.toko_online.dto.CartResponseDTO;
import com.programmerpagi.toko_online.dto.ResponseDTO;
import com.programmerpagi.toko_online.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @GetMapping("/carts")
    public ResponseEntity<ResponseDTO> findAllCart() {
        List<CartResponseDTO> carts = cartService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200,"success", carts));

    }

    @GetMapping("/carts/{userId}")
    public ResponseEntity<ResponseDTO> findCartByUserId(@PathVariable Long userId) {
        List<CartResponseDTO> carts = cartService.findByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200,"success", carts));

    }
    @PostMapping("/carts")
    public ResponseEntity<ResponseDTO> createCart(@RequestBody CartRequestDTO cartRequestDTO) {
        CartResponseDTO cartResponseDTO = cartService.addCart(cartRequestDTO);

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", cartResponseDTO));

    }
    

}
