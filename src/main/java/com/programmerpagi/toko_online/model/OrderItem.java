package com.programmerpagi.toko_online.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference("order-orderItems")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("product-orderItems")
    private Product product;
    private Integer kuantity;
    private String nama;
    private Long harga;

}
