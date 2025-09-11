package com.programmerpagi.toko_online.dto;

import lombok.Data;

@Data
public class OrderLangsungDTO {

    private Long userId;
    private Long produkId;
    private String name;
    private String address;
    private int quantity;


}
