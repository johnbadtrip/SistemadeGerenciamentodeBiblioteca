package com.biblioteca.apibiblioteca.service;

import com.biblioteca.apibiblioteca.domain.Livro;
import java.util.List;
import java.util.Optional;

public interface LivroService {
    Livro salvar(Livro livro);
    List<Livro> buscarTodos();
    Optional<Livro> buscarPorId(Long id);
    Optional<Livro> buscarPorIsbn(String isbn);
    List<Livro> buscarPorTitulo(String titulo);
    Livro atualizar(Long id, Livro livroAtualizado);
    void deletar(Long id);
    List<Livro> buscarPorAutorId(Long autorId);
    // Métodos para adicionar/remover autor de um livro
    Livro adicionarAutorAoLivro(Long livroId, Long autorId);
    Livro removerAutorDoLivro(Long livroId, Long autorId);
}