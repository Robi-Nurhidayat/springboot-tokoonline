package com.programmerpagi.toko_online.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama;
    private String kategori;
    private Long harga;
    private Integer stok;
    private String deskripsi;
    private String image;


    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade =  CascadeType.ALL)
    @JsonManagedReference("product-carts")
    private List<Cart> carts;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade =  CascadeType.ALL)
    @JsonManagedReference("product-orderItems")
    private List<OrderItem> orderItems;

}
