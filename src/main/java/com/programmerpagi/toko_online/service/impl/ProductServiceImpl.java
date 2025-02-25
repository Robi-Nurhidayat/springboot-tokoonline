package com.programmerpagi.toko_online.service.impl;

import com.programmerpagi.toko_online.dto.ProductDTO;
import com.programmerpagi.toko_online.model.Product;
import com.programmerpagi.toko_online.repository.ProductRepository;
import com.programmerpagi.toko_online.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public List<Product> getAll() {

        List<Product> products = productRepository.findAll().stream().map(product -> {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(product.getImage())
                    .toUriString();
            product.setImage(imageUrl);
            return product;
        }).collect(Collectors.toList());

        return products;
    }

    @Override
    public Product create(MultipartFile image, ProductDTO productDTO) {
       Path path = Paths.get(UPLOAD_DIR + image.getOriginalFilename());
        try {
            image.transferTo(path);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Product product = new Product();
        product.setNama(productDTO.getNama());
        product.setKategori(productDTO.getKategori());
        product.setHarga(productDTO.getHarga());
        product.setStok(productDTO.getStok());
        product.setDeskripsi(productDTO.getDeskripsi());
        product.setImage(image.getOriginalFilename());
        productRepository.save(product);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(product.getImage())
                .toUriString();
        product.setImage(imageUrl);
        return product;
    }

    @Override
    public Product findById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(product.getImage()).toUriString();
        product.setImage(imageUrl);


        return product;
    }

    @Override
    public Product delete(Long id) {
        return null;
    }

    private final ProductRepository productRepository;

}
