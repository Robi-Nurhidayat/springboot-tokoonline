package com.programmerpagi.toko_online.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private String name;
    private String address;
    private LocalDate orderDate;
    private Long totalAmount;



    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-orders")
    private User user;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade =  CascadeType.ALL)
    @JsonManagedReference("order-orderItems")
    private List<OrderItem> orderItems;


}
