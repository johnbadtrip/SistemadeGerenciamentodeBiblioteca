package com.biblioteca.apibiblioteca.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {
    private Long id;
    private String nome;
    private String nacionalidade;
    // Poderíamos incluir IDs ou DTOs simplificados dos livros aqui, se necessário
    // private Set<Long> livroIds; 
}