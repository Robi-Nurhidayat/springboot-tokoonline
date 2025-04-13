package com.programmerpagi.toko_online.service;

import com.programmerpagi.toko_online.dto.OrderRequestDto;
import com.programmerpagi.toko_online.dto.OrderResponseDTO;
import com.programmerpagi.toko_online.model.Order;

import java.util.List;

public interface IOrderService {

    void createOrder(OrderRequestDto order);
    void createOrderWithoutFromCart(Order order);

    List<OrderResponseDTO> getAllOrder();

    List<OrderResponseDTO> getOrderByUser(Long userId);

}
