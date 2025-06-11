package com.biblioteca.apibiblioteca.repository;

import com.biblioteca.apibiblioteca.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Exemplo de método de consulta personalizado (Spring Data JPA o implementará automaticamente)
    // Encontra um autor pelo nome
    Optional<Autor> findByNome(String nome);

    // Você pode adicionar mais métodos de consulta aqui conforme necessário
    // Ex: List<Autor> findByNacionalidade(String nacionalidade);
}