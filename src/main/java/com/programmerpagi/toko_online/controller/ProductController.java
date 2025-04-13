package com.programmerpagi.toko_online.controller;

import com.programmerpagi.toko_online.dto.ProductRequestDTO;
import com.programmerpagi.toko_online.dto.ProductResponseDTO;
import com.programmerpagi.toko_online.dto.ResponseDTO;
import com.programmerpagi.toko_online.model.Product;
import com.programmerpagi.toko_online.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("/products")
    public ResponseEntity<ResponseDTO> getAllProduct() {

        List<ProductResponseDTO> products = productService.getAll();

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", products));

    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable Long id) {

        ProductResponseDTO product = productService.findById(id);

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", product));

    }

    @PostMapping("/products")
    public ResponseEntity<ResponseDTO> create(@RequestParam("image")MultipartFile image, @ModelAttribute ProductRequestDTO productRequestDTO) {


        ProductResponseDTO product = productService.create(image, productRequestDTO);

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", product));

    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {

        productService.delete(id);
        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", null));

    }

}
