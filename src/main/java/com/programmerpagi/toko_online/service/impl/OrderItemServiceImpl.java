package com.programmerpagi.toko_online.service.impl;

import com.programmerpagi.toko_online.model.OrderItem;
import com.programmerpagi.toko_online.repository.OrderItemRepository;
import com.programmerpagi.toko_online.service.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll() {

        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems;
    }
}
