package com.programmerpagi.toko_online.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class ProductResponseDTO {

    private Long id;
    private String nama;
    private String kategori;
    private BigDecimal harga;
    private Integer stok;
    private String deskripsi;
    private String image;
}
