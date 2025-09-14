package com.programmerpagi.toko_online.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade =  CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade =  CascadeType.ALL)
    private List<Cart> carts;

}
