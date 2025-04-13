package com.programmerpagi.toko_online.controller;

import com.programmerpagi.toko_online.dto.ResponseDTO;
import com.programmerpagi.toko_online.service.IOrderItemService;
import com.programmerpagi.toko_online.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderItemController {
    private final IOrderItemService orderItemService;

    @GetMapping("/orderItems")
    public ResponseEntity<ResponseDTO> findAllOrderItem() {

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", orderItemService.findAll()));
    }
}
