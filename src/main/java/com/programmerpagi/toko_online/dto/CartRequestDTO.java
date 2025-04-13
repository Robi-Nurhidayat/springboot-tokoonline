package com.programmerpagi.toko_online.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartRequestDTO {
    private Long productId;
    private Long userId;
    private Integer quantity;
}
