package com.biblioteca.apibiblioteca.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;
import com.biblioteca.apibiblioteca.domain.Livro; // Para o método de conversão

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private Integer anoPublicacao;
    private String editora;
    private Integer numeroPaginas;
    private Set<Long> autorIds; // Apenas os IDs dos autores

    // Método de fábrica para converter de Entidade para DTO
    public static LivroDTO fromEntity(Livro livro) {
        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getEditora(),
                livro.getNumeroPaginas(),
                livro.getAutores().stream().map(autor -> autor.getId()).collect(Collectors.toSet())
        );
    }
}