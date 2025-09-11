package com.programmerpagi.toko_online.service.impl;

import com.programmerpagi.toko_online.dto.CartRequestDTO;
import com.programmerpagi.toko_online.dto.CartResponseDTO;
import com.programmerpagi.toko_online.exception.InsufficientStockException;
import com.programmerpagi.toko_online.exception.ResourceNotFoundException;
import com.programmerpagi.toko_online.model.Cart;
import com.programmerpagi.toko_online.model.Product;
import com.programmerpagi.toko_online.model.User;
import com.programmerpagi.toko_online.repository.CartRepository;
import com.programmerpagi.toko_online.repository.ProductRepository;
import com.programmerpagi.toko_online.repository.UserRepository;
import com.programmerpagi.toko_online.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {


    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    public List<CartResponseDTO> findAll() {
        List<CartResponseDTO> carts = cartRepository.findAll().stream().map(
                item -> {

                    CartResponseDTO cartResponseDTO = new CartResponseDTO();
                    cartResponseDTO.setId(item.getId());
                    cartResponseDTO.setUserId(item.getUser().getId());
                    cartResponseDTO.setProductId(item.getProduct().getId());
                    cartResponseDTO.setQuantity(item.getQuantity());
                    cartResponseDTO.setName(item.getProduct().getNama());
                    return cartResponseDTO;
                }
        ).collect(Collectors.toList());
        return carts;
    }

    @Override
    public List<CartResponseDTO> findByUserId(Long userId) {
        List<CartResponseDTO> carts = cartRepository.findByUserId(userId).stream().map(
            item -> {
                CartResponseDTO cartResponseDTO = new CartResponseDTO();
                cartResponseDTO.setId(item.getId());
                cartResponseDTO.setUserId(item.getUser().getId());
                cartResponseDTO.setProductId(item.getProduct().getId());
                cartResponseDTO.setName(item.getProduct().getNama());
                cartResponseDTO.setQuantity(item.getQuantity());
                return cartResponseDTO;
            }
        ).collect(Collectors.toList());
        return carts;
    }

    @Override
    public CartResponseDTO addCart(CartRequestDTO cartRequestDTO) {

        Product product = productRepository.findById(cartRequestDTO.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("produk","id", Long.toString(cartRequestDTO.getProductId()))
        );

        User user = userRepository.findById(cartRequestDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("user","id", Long.toString(cartRequestDTO.getUserId()))
        );


        if (user.getCarts().size() > 0) {

        }


        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cartRepository.save(cart);



        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setId(cart.getId());
        cartResponseDTO.setProductId(cart.getProduct().getId());
        cartResponseDTO.setName(cart.getProduct().getNama());
        cartResponseDTO.setUserId(cart.getUser().getId());
        cartResponseDTO.setQuantity(cart.getQuantity());

        return cartResponseDTO;

    }
}
