package com.programmerpagi.toko_online.dto;

import com.programmerpagi.toko_online.model.Order;
import com.programmerpagi.toko_online.model.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal totalHarga;

}
