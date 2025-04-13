package com.programmerpagi.toko_online.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartResponseDTO {
    private Long id;
    private String name;
    private Long productId;
    private Long userId;
    private Integer quantity;
}
