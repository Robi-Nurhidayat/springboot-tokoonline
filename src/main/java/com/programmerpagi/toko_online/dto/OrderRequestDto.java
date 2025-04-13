package com.programmerpagi.toko_online.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.programmerpagi.toko_online.model.OrderItem;
import com.programmerpagi.toko_online.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {


//    private String orderNumber;
    private String name;
    private String address;
//    private String orderDate;


    // ini jika user order tanpa cart
    private Long productId;
    private Integer kuantity;
    private Long userId;




}
