package com.programmerpagi.toko_online.service.impl;

import com.programmerpagi.toko_online.dto.ProductRequestDTO;
import com.programmerpagi.toko_online.dto.ProductResponseDTO;
import com.programmerpagi.toko_online.exception.ResourceNotFoundException;
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
    public List<ProductResponseDTO> getAll() {

        List<ProductResponseDTO> products = productRepository.findAll().stream().map(product -> {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(product.getImage())
                    .toUriString();

            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(product.getId());
            productResponseDTO.setNama(product.getNama());
            productResponseDTO.setKategori(product.getKategori());
            productResponseDTO.setHarga(product.getHarga());
            productResponseDTO.setStok(product.getStok());
            productResponseDTO.setDeskripsi(product.getDeskripsi());
            productResponseDTO.setImage(imageUrl);

            return productResponseDTO;
        }).collect(Collectors.toList());

        return products;
    }

    @Override
    public ProductResponseDTO create(MultipartFile image, ProductRequestDTO productRequestDTO) {
       Path path = Paths.get(UPLOAD_DIR + image.getOriginalFilename());
        try {
            image.transferTo(path);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Product product = new Product();
        product.setNama(productRequestDTO.getNama());
        product.setKategori(productRequestDTO.getKategori());
        product.setHarga(productRequestDTO.getHarga());
        product.setStok(productRequestDTO.getStok());
        product.setDeskripsi(productRequestDTO.getDeskripsi());
        product.setImage(image.getOriginalFilename());
        productRepository.save(product);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(product.getImage())
                .toUriString();

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

    @Override
    public ProductResponseDTO findById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product","id", Long.toString(id)));

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(product.getImage()).toUriString();

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

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("product","id",Long.toString(id))
        );
        Path imageUrl = Paths.get(UPLOAD_DIR).resolve(product.getImage());

        if(Files.exists(imageUrl)) {
            try {
                Files.delete(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        productRepository.deleteById(id);
    }

    private final ProductRepository productRepository;

}
