package com.programmerpagi.toko_online.utils;

import com.programmerpagi.toko_online.dto.ProductRequestDTO;
import com.programmerpagi.toko_online.dto.ProductResponseDTO;
import com.programmerpagi.toko_online.model.Product;

public class ProductMapper {

    private ProductMapper(){}

    public static ProductResponseDTO toProductResponseDTO(Product product, String imageUrl) {

        if (product == null) {
            return null;
        }

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setNama(product.getNama());
        productResponseDTO.setKategori(product.getKategori());
        productResponseDTO.setHarga(product.getHarga());
        productResponseDTO.setStok(product.getStok());
        productResponseDTO.setDeskripsi(product.getDeskripsi());
        productResponseDTO.setImage(imageUrl);

        return productResponseDTO;
    }
}
