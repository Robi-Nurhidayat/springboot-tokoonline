package com.programmerpagi.toko_online.service;

import com.programmerpagi.toko_online.model.OrderItem;

import java.util.List;

public interface IOrderItemService {

    List<OrderItem> findAll();
}
