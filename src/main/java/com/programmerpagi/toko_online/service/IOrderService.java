package com.programmerpagi.toko_online.service;

import com.programmerpagi.toko_online.dto.OrderFromCartDto;
import com.programmerpagi.toko_online.dto.OrderLangsungDTO;
import com.programmerpagi.toko_online.dto.OrderLangsungResponseDTO;
import com.programmerpagi.toko_online.dto.OrderResponseDTO;
import com.programmerpagi.toko_online.model.Order;

import java.util.List;

public interface IOrderService {

    void createOrder(OrderFromCartDto order);
    OrderLangsungResponseDTO createOrderLangsung(OrderLangsungDTO orderLangsungDTO);

    List<OrderResponseDTO> getAllOrder();

    List<OrderResponseDTO> getOrderByUser(Long userId);

}
