package com.programmerpagi.toko_online.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
}
