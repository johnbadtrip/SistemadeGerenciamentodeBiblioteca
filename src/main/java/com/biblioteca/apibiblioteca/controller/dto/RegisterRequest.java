package com.biblioteca.apibiblioteca.controller.dto;

import com.biblioteca.apibiblioteca.domain.Role;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class RegisterRequest {
    @NotBlank
    private String nome;
    
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    @Size(min = 6)
    private String senha;
    
    private Role role = Role.USUARIO;
}