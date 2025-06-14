package com.biblioteca.apibiblioteca.controller.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String senha;
}