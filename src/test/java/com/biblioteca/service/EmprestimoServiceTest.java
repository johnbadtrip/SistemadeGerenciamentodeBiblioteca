package com.biblioteca.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.biblioteca.apibiblioteca.domain.Livro;
import com.biblioteca.apibiblioteca.domain.Usuario;
import com.biblioteca.apibiblioteca.repository.LivroRepository;
import com.biblioteca.apibiblioteca.repository.EmprestimoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

class EmprestimoServiceTest {
    private LivroRepository livroRepository;
    private EmprestimoRepository emprestimoRepository;
    private EmprestimoService emprestimoService;

    @BeforeEach
    void setUp() {
        livroRepository = mock(LivroRepository.class);
        emprestimoRepository = mock(EmprestimoRepository.class);
        emprestimoService = new EmprestimoService(livroRepository, emprestimoRepository);
    }

    @Test
    void naoPermiteEmprestarLivroJaEmprestado() {
        Livro livro = new Livro();
        livro.setEmprestado(true);
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            emprestimoService.emprestarLivro(1L, 1L);
        });
        assertEquals("Livro já está emprestado", ex.getMessage());
    }

    @Test
    void naoPermiteEmprestarMaisDeTresLivros() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(emprestimoRepository.countByUsuarioIdAndAtivoTrue(1L)).thenReturn(3L);
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            emprestimoService.emprestarLivro(1L, 1L);
        });
        assertEquals("Usuário já atingiu o limite de empréstimos", ex.getMessage());
    }

    @Test
    void permiteEmprestarLivroDisponivel() {
        Livro livro = new Livro();
        livro.setEmprestado(false);
        when(livroRepository.findById(2L)).thenReturn(Optional.of(livro));
        when(emprestimoRepository.countByUsuarioIdAndAtivoTrue(1L)).thenReturn(0L);
        assertDoesNotThrow(() -> emprestimoService.emprestarLivro(2L, 1L));
    }
}