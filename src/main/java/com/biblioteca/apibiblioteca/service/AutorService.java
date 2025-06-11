package com.biblioteca.apibiblioteca.service;

import com.biblioteca.apibiblioteca.domain.Autor;
import java.util.List;
import java.util.Optional;

public interface AutorService {
    Autor salvar(Autor autor);
    List<Autor> buscarTodos();
    Optional<Autor> buscarPorId(Long id);
    Optional<Autor> buscarPorNome(String nome);
    Autor atualizar(Long id, Autor autorAtualizado);
    void deletar(Long id);
}