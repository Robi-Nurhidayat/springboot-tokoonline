package com.programmerpagi.toko_online.service;

import com.programmerpagi.toko_online.dto.ProductRequestDTO;
import com.programmerpagi.toko_online.dto.ProductResponseDTO;
import com.programmerpagi.toko_online.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {

    List<ProductResponseDTO> getAll();
    ProductResponseDTO create(MultipartFile image, ProductRequestDTO productDTO);

    ProductResponseDTO findById(Long id);

    void delete(Long id);
}
