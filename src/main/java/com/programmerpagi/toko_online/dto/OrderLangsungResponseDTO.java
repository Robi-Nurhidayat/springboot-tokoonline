package com.programmerpagi.toko_online.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderLangsungResponseDTO {

    private Long id;
    private Long userId;
    private String orderNumber;
    private String name;
    private String address;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String status;


    private List<OrderItemResponseDTO> product;





}
