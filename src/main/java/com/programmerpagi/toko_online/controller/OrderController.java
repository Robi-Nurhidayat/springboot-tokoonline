package com.programmerpagi.toko_online.controller;

import com.programmerpagi.toko_online.dto.OrderFromCartDto;
import com.programmerpagi.toko_online.dto.OrderResponseDTO;
import com.programmerpagi.toko_online.dto.ResponseDTO;
import com.programmerpagi.toko_online.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<ResponseDTO> getAllOrder() {

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", orderService.getAllOrder()));
    }

    @GetMapping("/orders/user")
    public ResponseEntity<ResponseDTO> findOrderByUser(@RequestParam Long id) {

        List<OrderResponseDTO> orderByUserId = orderService.getOrderByUser(id);

        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success",  orderByUserId));
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseDTO> createOrder(@RequestBody OrderFromCartDto order) {

        System.out.println(order);
        orderService.createOrder(order);
        return ResponseEntity.status(200).body(new ResponseDTO(200, "Success", null));
    }


}
