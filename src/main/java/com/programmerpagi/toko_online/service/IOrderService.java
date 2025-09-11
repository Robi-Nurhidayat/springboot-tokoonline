package com.programmerpagi.toko_online.service;

import com.programmerpagi.toko_online.dto.OrderFromCartDto;
import com.programmerpagi.toko_online.dto.OrderLangsungDTO;
import com.programmerpagi.toko_online.dto.OrderResponseDTO;
import com.programmerpagi.toko_online.model.Order;

import java.util.List;

public interface IOrderService {

    void createOrder(OrderFromCartDto order);
    String createOrderLangsung(OrderLangsungDTO orderLangsungDTO);

    List<OrderResponseDTO> getAllOrder();

    List<OrderResponseDTO> getOrderByUser(Long userId);

}
