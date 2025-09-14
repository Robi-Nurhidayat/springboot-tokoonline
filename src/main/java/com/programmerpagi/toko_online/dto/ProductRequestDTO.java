package com.programmerpagi.toko_online.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter

public class ProductRequestDTO {

    private Long id;
    private String nama;
    private String kategori;
    private BigDecimal harga;
    private Integer stok;
    private String deskripsi;
    private MultipartFile image;
}
