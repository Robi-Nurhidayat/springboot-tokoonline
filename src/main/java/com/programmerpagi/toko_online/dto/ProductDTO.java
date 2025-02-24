package com.programmerpagi.toko_online.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductDTO {

    private Long id;
    private String nama;
    private String kategori;
    private Double harga;
    private Integer stok;
    private String deskripsi;
}
