package com.programmerpagi.toko_online.service.impl;

import com.programmerpagi.toko_online.dto.*;
import com.programmerpagi.toko_online.exception.InsufficientStockException;
import com.programmerpagi.toko_online.exception.ResourceNotFoundException;
import com.programmerpagi.toko_online.model.*;
import com.programmerpagi.toko_online.repository.*;
import com.programmerpagi.toko_online.service.IOrderService;
import com.programmerpagi.toko_online.utils.TransactionNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

//        List<OrderResponseDTO> data = orderRepository.findAll().stream().map(order -> {
//            OrderResponseDTO dto = new OrderResponseDTO();
//            dto.setId(order.getId());
//            dto.setOrderNumber(order.getOrderNumber());
//            dto.setName(order.getName());
//            dto.setAddress(order.getAddress());
//            dto.setOrderDate(order.getOrderDate());
//            dto.setUserId(order.getUser().getId()); // Hanya ambil ID user
//            return dto;
//        }).collect(Collectors.toList());


        return null;
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
    public void createOrder(OrderFromCartDto orderDto) {

//
//        LocalDateTime localDateTime = LocalDateTime.now();
//        Order order = new Order();
//
//        OrderFromCartDto orderRequestDto = orderDto;
//        User findUser = userRepository.findById(orderDto.getUserId()).orElseThrow(
//                () -> new ResourceNotFoundException("user", "id", Long.toString(orderRequestDto.getUserId()))
//        );
//
//        if (!findUser.getCarts().isEmpty()) {
//            Long total = (long) findUser.getCarts().stream().mapToDouble(cart -> cart.getQuantity() * cart.getProduct().getHarga()).sum();
//            order.setTotalAmount(total);
//            order.setUser(findUser);
//            order.setAddress(orderDto.getAddress());
//            order.setName(orderDto.getName());
//            order.setOrderDate(localDateTime.toLocalDate());
//            order.setOrderNumber(generateOrderNumber());
//
//
//            order = orderRepository.save(order);
//            Order finalOrder = order;
//            List<OrderItem> orderItems = findUser.getCarts().stream().map(item -> {
//
//                OrderItem orderItem = new OrderItem();
//                orderItem.setOrder(finalOrder);
//                orderItem.setProduct(item.getProduct());
//                orderItem.setKuantity(item.getQuantity());
//                orderItem.setTotalHarga(item.getProduct().getHarga());
////                orderItem.setNama(item.getProduct().getNama());
//
//                return orderItem;
//            }).collect(Collectors.toList());
//            List<OrderItem> orderItemsSaved = orderItemRepository.saveAll(orderItems);
//
//            // update stok product
//            List<Cart> carts = findUser.getCarts();
//            for (var item: carts) {
//                Optional<Product> productId = productRepository.findById(item.getProduct().getId());
//                if (productId.isPresent()) {
//                    productId.get().setStok(productId.get().getStok() - item.getQuantity());
//                    productRepository.save(productId.get());
//                }
//            }
//
//
//            cartRepository.deleteByUserId(order.getUser().getId());
//
//            System.out.println("ini blok klo ada cart");
//
//
//        }else {
//
//            // jika user order tanpa cart / langsung order
//
//            // untuk create order
//            order.setOrderNumber(generateOrderNumber());
//            order.setName(orderDto.getName());
//            order.setAddress(orderDto.getAddress());
//            order.setOrderDate(localDateTime.toLocalDate());
//            order.setUser(findUser);
//            Product product = productRepository.getById(orderDto.getProductId());
//            order.setTotalAmount(product.getHarga() * orderDto.getKuantity());
//
//            if (product.getStok() < orderDto.getKuantity()) {
//                throw new InsufficientStockException("stok tidak mencukupi");
//            }
//            Order newOrder = orderRepository.save(order);
//
//            // untuk create order item atau list dari order
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(newOrder);
//            orderItem.setProduct(product);
//            orderItem.setKuantity(orderDto.getKuantity());
//            orderItem.setTotalHarga(product.getHarga());
////            orderItem.setNama(product.getNama());
//            orderItemRepository.save(orderItem);
//
//            // update stok
//            product.setStok(product.getStok() - orderDto.getKuantity());
//            productRepository.save(product);
//
//
//        }
//



    }

    @Override
    @Transactional
    public OrderLangsungResponseDTO createOrderLangsung(OrderLangsungDTO orderLangsungDTO) {

        // 1. Validasi input dasar
        if (orderLangsungDTO.getQuantity() <= 0) {
            throw new InsufficientStockException("Minimal jumlah pembelian harus 1.");
        }

        // findUser
        User user = userRepository.findById(orderLangsungDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User","id", Long.toString(orderLangsungDTO.getUserId()))
        );

        System.out.println("username " + user.getName());

        // find produk
        Product product = productRepository.findById(orderLangsungDTO.getProdukId()).orElseThrow(
                () -> new ResourceNotFoundException("Produk","id", Long.toString(orderLangsungDTO.getUserId()))
        );

        // 3. Validasi stok produk
        if (product.getStok() < orderLangsungDTO.getQuantity()) {
            throw new InsufficientStockException("Stok produk tidak mencukupi.");
        }



        Order order = new Order();
        order.setOrderNumber(TransactionNumberGenerator.generate());
        order.setName(orderLangsungDTO.getName());
        order.setAddress(orderLangsungDTO.getAddress());
        order.setStatus("Pending");

        BigDecimal totalAmount = product.getHarga().multiply(BigDecimal.valueOf(orderLangsungDTO.getQuantity()));
        order.setTotalAmount(totalAmount);
        order.setOrderDate(LocalDate.now());
        Order savedOrder = orderRepository.save(order);


        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(savedOrder);
        orderItem.setProduct(product);
        orderItem.setQuantity(orderLangsungDTO.getQuantity());
        orderItem.setTotalHarga(product.getHarga().multiply(BigDecimal.valueOf(orderLangsungDTO.getQuantity())));
        OrderItem orderItemSaved = orderItemRepository.save(orderItem);

        // update stok product
        product.setStok(product.getStok() - orderItem.getQuantity());
        productRepository.save(product);

        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
        orderItemResponseDTO.setId(orderItemSaved.getId());
        orderItemResponseDTO.setOrderId(savedOrder.getId());
        orderItemResponseDTO.setProductId(product.getId());
        orderItemResponseDTO.setQuantity(orderItemSaved.getQuantity());
        orderItemResponseDTO.setProductName(product.getNama());
        orderItemResponseDTO.setTotalHarga(orderItemSaved.getTotalHarga());

        List<OrderItemResponseDTO> orderItemResponseDTOList = new ArrayList<>();
        orderItemResponseDTOList.add(orderItemResponseDTO);


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
