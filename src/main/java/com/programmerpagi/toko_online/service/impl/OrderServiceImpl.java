package com.programmerpagi.toko_online.service.impl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.programmerpagi.toko_online.dto.OrderRequestDto;
import com.programmerpagi.toko_online.dto.OrderResponseDTO;
import com.programmerpagi.toko_online.exception.InsufficientStockException;
import com.programmerpagi.toko_online.exception.ResourceNotFoundException;
import com.programmerpagi.toko_online.model.*;
import com.programmerpagi.toko_online.repository.*;
import com.programmerpagi.toko_online.service.IOrderService;
import com.programmerpagi.toko_online.service.IUserService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderResponseDTO> getAllOrder() {

        List<OrderResponseDTO> data = orderRepository.findAll().stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setId(order.getId());
            dto.setOrderNumber(order.getOrderNumber());
            dto.setName(order.getName());
            dto.setAddress(order.getAddress());
            dto.setOrderDate(order.getOrderDate());
            dto.setUserId(order.getUser().getId()); // Hanya ambil ID user
            return dto;
        }).collect(Collectors.toList());
        return data;
    }

    @Override
    public List<OrderResponseDTO> getOrderByUser(Long userId) {

        List<OrderResponseDTO> data = orderRepository.findByUser_Id(userId).stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setId(order.getId());
            dto.setOrderNumber(order.getOrderNumber());
            dto.setName(order.getName());
            dto.setAddress(order.getAddress());
            dto.setOrderDate(order.getOrderDate());
            dto.setUserId(order.getUser().getId()); // Hanya ambil ID user
            return dto;
        }).collect(Collectors.toList());
        return data;
    }

    @Override
    public void createOrder(OrderRequestDto orderDto) {


        LocalDateTime localDateTime = LocalDateTime.now();
        Order order = new Order();

        OrderRequestDto orderRequestDto = orderDto;
        User findUser = userRepository.findById(orderDto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("user", "id", Long.toString(orderRequestDto.getUserId()))
        );

        if (!findUser.getCarts().isEmpty()) {
            Long total = (long) findUser.getCarts().stream().mapToDouble(cart -> cart.getQuantity() * cart.getProduct().getHarga()).sum();
            order.setTotalAmount(total);
            order.setUser(findUser);
            order.setAddress(orderDto.getAddress());
            order.setName(orderDto.getName());
            order.setOrderDate(localDateTime.toLocalDate());
            order.setOrderNumber(generateOrderNumber());


            order = orderRepository.save(order);
            Order finalOrder = order;
            List<OrderItem> orderItems = findUser.getCarts().stream().map(item -> {

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(finalOrder);
                orderItem.setProduct(item.getProduct());
                orderItem.setKuantity(item.getQuantity());
                orderItem.setHarga(item.getProduct().getHarga());
                orderItem.setNama(item.getProduct().getNama());

                return orderItem;
            }).collect(Collectors.toList());
            List<OrderItem> orderItemsSaved = orderItemRepository.saveAll(orderItems);

            // update stok product
            List<Cart> carts = findUser.getCarts();
            for (var item: carts) {
                Optional<Product> productId = productRepository.findById(item.getProduct().getId());
                if (productId.isPresent()) {
                    productId.get().setStok(productId.get().getStok() - item.getQuantity());
                    productRepository.save(productId.get());
                }
            }


            cartRepository.deleteByUserId(order.getUser().getId());

            System.out.println("ini blok klo ada cart");


        }else {

            // jika user order tanpa cart / langsung order

            // untuk create order
            order.setOrderNumber(generateOrderNumber());
            order.setName(orderDto.getName());
            order.setAddress(orderDto.getAddress());
            order.setOrderDate(localDateTime.toLocalDate());
            order.setUser(findUser);
            Product product = productRepository.getById(orderDto.getProductId());
            order.setTotalAmount(product.getHarga() * orderDto.getKuantity());

            if (product.getStok() < orderDto.getKuantity()) {
                throw new InsufficientStockException("stok tidak mencukupi");
            }
            Order newOrder = orderRepository.save(order);

            // untuk create order item atau list dari order
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(newOrder);
            orderItem.setProduct(product);
            orderItem.setKuantity(orderDto.getKuantity());
            orderItem.setHarga(product.getHarga());
            orderItem.setNama(product.getNama());
            orderItemRepository.save(orderItem);

            // update stok
            product.setStok(product.getStok() - orderDto.getKuantity());
            productRepository.save(product);


        }



    }


    @Override
    public void createOrderWithoutFromCart(Order order) {

    }

    public String generateOrderNumber() {

        LocalDateTime localDateTime = LocalDateTime.now();
        String orderNumber = "TRC-" + localDateTime.getSecond() + localDateTime.getMinute()
                + localDateTime.getHour() + localDateTime.getDayOfMonth()
                + localDateTime.getMonthValue() + localDateTime.getYear();


        return orderNumber;
    }
}
