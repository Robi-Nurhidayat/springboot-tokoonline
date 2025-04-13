package com.programmerpagi.toko_online.repository;

import com.programmerpagi.toko_online.dto.OrderResponseDTO;
import com.programmerpagi.toko_online.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser_Id(Long userId);
}
