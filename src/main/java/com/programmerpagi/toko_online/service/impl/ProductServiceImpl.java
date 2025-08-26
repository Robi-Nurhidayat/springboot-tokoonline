package com.programmerpagi.toko_online.service.impl;

import com.programmerpagi.toko_online.dto.ProductRequestDTO;
import com.programmerpagi.toko_online.dto.ProductResponseDTO;
import com.programmerpagi.toko_online.exception.AlreadyExistsException;
import com.programmerpagi.toko_online.exception.ResourceNotFoundException;
import com.programmerpagi.toko_online.model.Product;
import com.programmerpagi.toko_online.repository.ProductRepository;
import com.programmerpagi.toko_online.service.IProductService;
import com.programmerpagi.toko_online.utils.ImageFileNameUtil;
import com.programmerpagi.toko_online.utils.ImageSaveUtil;
import com.programmerpagi.toko_online.utils.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private static final String UPLOAD_DIR = "uploads/";
    private final ProductRepository productRepository;

    @Override
    public List<ProductResponseDTO> getAll() {

        List<ProductResponseDTO> products = productRepository.findAll().stream().map(product -> {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(product.getImage())
                    .toUriString();

            // mapper product ke product response dto
            ProductResponseDTO productResponseDTO = ProductMapper.toProductResponseDTO(product, imageUrl);

            return productResponseDTO;
        }).collect(Collectors.toList());

        return products;
    }

    @Override
    public ProductResponseDTO findById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product","id", Long.toString(id)));

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(product.getImage()).toUriString();

        // mapper product ke product response dto
        ProductResponseDTO productResponseDTO = ProductMapper.toProductResponseDTO(product, imageUrl);

        return productResponseDTO;
    }


    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {

        Optional<Product> foundProduk = productRepository.findByNama(productRequestDTO.getNama());

        if (foundProduk.isPresent()) {
            throw new AlreadyExistsException(productRequestDTO.getNama() + " sudah ada!!!");
        }

        // membuat nama image yg akan disimpan di db
        String imageName = ImageFileNameUtil.generate(productRequestDTO.getImage());

        Product product = new Product();
        product.setNama(productRequestDTO.getNama());
        product.setKategori(productRequestDTO.getKategori());
        product.setHarga(productRequestDTO.getHarga());
        product.setStok(productRequestDTO.getStok());
        product.setDeskripsi(productRequestDTO.getDeskripsi());
        product.setImage(imageName);
        productRepository.save(product);

        // untuk menyimpan gambar
        ImageSaveUtil.save(UPLOAD_DIR,productRequestDTO.getImage(),imageName);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(imageName)
                .toUriString();

        // mapper product ke product response dto
        ProductResponseDTO productResponseDTO = ProductMapper.toProductResponseDTO(product, imageUrl);
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO update(ProductRequestDTO productRequestDTO, Long id) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("product", "id", Long.toString(id))
        );

        System.out.println("isi id : " + product.getId());
        if ( productRequestDTO.getImage() != null && !productRequestDTO.getImage().isEmpty()) {
            Path imageUrl = Paths.get(UPLOAD_DIR).resolve(product.getImage());

            System.out.println("blok ini di eksekusi");
            if(Files.exists(imageUrl)) {
                try {
                    Files.delete(imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        String imageName = "";
        String uuid = UUID.randomUUID().toString();
        if ( productRequestDTO.getImage() != null && !productRequestDTO.getImage().isEmpty()) {
            imageName = uuid + "_" + productRequestDTO.getImage().getOriginalFilename().replaceAll("\\s+", "_");
        }else {
            imageName = uuid + "_produk.jpg";
        }


        product.setNama(productRequestDTO.getNama());
        product.setKategori(productRequestDTO.getKategori());
        product.setHarga(productRequestDTO.getHarga());
        product.setStok(productRequestDTO.getStok());
        product.setDeskripsi(productRequestDTO.getDeskripsi());
        product.setImage(imageName);
        productRepository.save(product);

        // untuk menyimpan gambar
        ImageSaveUtil.save(UPLOAD_DIR,productRequestDTO.getImage(),imageName);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(imageName)
                .toUriString();

        // mapper product ke product response dto
        ProductResponseDTO productResponseDTO = ProductMapper.toProductResponseDTO(product, imageUrl);
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



}
