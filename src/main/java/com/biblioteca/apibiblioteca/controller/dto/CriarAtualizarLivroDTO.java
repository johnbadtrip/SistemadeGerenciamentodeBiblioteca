package com.biblioteca.apibiblioteca.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.Set;

@Data
public class CriarAtualizarLivroDTO {

    @NotBlank(message = "O título não pode estar em branco.")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres.")
    private String titulo;

    @NotBlank(message = "O ISBN não pode estar em branco.")
    @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$|^(?:ISBN(?:-10)?:? )?(?=[0-9]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", message = "ISBN inválido.")
    // Regex acima é um exemplo, pode precisar de ajuste para cobrir todos os formatos de ISBN 10/13
    private String isbn;

    private Integer anoPublicacao;

    @Size(max = 100, message = "A editora deve ter no máximo 100 caracteres.")
    private String editora;

    private Integer numeroPaginas;

    @NotNull(message = "A lista de IDs de autores não pode ser nula.")
    private Set<Long> autorIds;
}