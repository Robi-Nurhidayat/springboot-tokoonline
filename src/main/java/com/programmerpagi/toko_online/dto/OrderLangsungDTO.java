package com.programmerpagi.toko_online.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderLangsungDTO {

    private Long userId;
    private Long produkId;
    private String name;
    private String address;
    private Integer quantity;




}
