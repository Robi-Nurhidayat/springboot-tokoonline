package com.programmerpagi.toko_online.controller;

import com.programmerpagi.toko_online.dto.ProductDTO;
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

        List<Product> products = productService.getAll();

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", products));

    }

    @PostMapping("/products")
    public ResponseEntity<ResponseDTO> create(@RequestParam("image")MultipartFile image, @ModelAttribute ProductDTO productDTO) {


        Product product = productService.create(image, productDTO);

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", product));

    }

}
