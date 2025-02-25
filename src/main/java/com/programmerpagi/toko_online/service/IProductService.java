package com.programmerpagi.toko_online.service;

import com.programmerpagi.toko_online.dto.ProductDTO;
import com.programmerpagi.toko_online.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {

    List<Product> getAll();
    Product create(MultipartFile image, ProductDTO productDTO);

    Product findById(Long id);

    void delete(Long id);
}
