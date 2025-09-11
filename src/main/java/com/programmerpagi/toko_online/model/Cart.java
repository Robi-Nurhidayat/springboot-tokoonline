package com.programmerpagi.toko_online.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "carts")
@Getter @Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("user-carts")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference("product-carts")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @JsonManagedReference("user-carts")
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade =  CascadeType.ALL)
    private List<Cart> carts;
}
