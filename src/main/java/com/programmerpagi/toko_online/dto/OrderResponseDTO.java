package com.programmerpagi.toko_online.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderResponseDTO {

    private Long id;
    private String orderNumber;
    private Long userId;
    private String name;
    private String address;
    private LocalDate orderDate;

}
