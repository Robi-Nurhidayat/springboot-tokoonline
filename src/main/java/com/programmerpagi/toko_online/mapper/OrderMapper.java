package com.programmerpagi.toko_online.mapper;


import com.programmerpagi.toko_online.dto.OrderItemResponseDTO;
import com.programmerpagi.toko_online.dto.OrderLangsungResponseDTO;
import com.programmerpagi.toko_online.model.Order;
import com.programmerpagi.toko_online.model.User;

import java.util.List;

public final class OrderMapper {

    private  OrderMapper(){}

    public static OrderLangsungResponseDTO ToResponseOrderLangsung(Order savedOrder, List<OrderItemResponseDTO> orderItemResponseDTOList, User user) {
        OrderLangsungResponseDTO responseDTO = new OrderLangsungResponseDTO();
        responseDTO.setId(savedOrder.getId());
        responseDTO.setUserId(user.getId());
        responseDTO.setOrderNumber(savedOrder.getOrderNumber());
        responseDTO.setName(savedOrder.getName());
        responseDTO.setAddress(savedOrder.getAddress());
        responseDTO.setOrderDate(savedOrder.getOrderDate());
        responseDTO.setStatus(savedOrder.getStatus());
        responseDTO.setTotalAmount(savedOrder.getTotalAmount());
        responseDTO.setProduct(orderItemResponseDTOList);

        return responseDTO;
    }
}
