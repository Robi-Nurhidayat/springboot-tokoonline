package com.programmerpagi.toko_online.dto;

import lombok.Data;

@Data
public class OrderFromCartDto {


//    private String orderNumber;
    private String name;
    private String address;
//    private String orderDate;


    // ini jika user order tanpa cart
    private Long productId;
    private Integer kuantity;
    private Long userId;




}
