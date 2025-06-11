package com.biblioteca.apibiblioteca.repository;

import com.biblioteca.apibiblioteca.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Encontra um livro pelo ISBN (que é único)
    Optional<Livro> findByIsbn(String isbn);

    // Encontra livros por título (pode retornar múltiplos livros)
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    // Encontra livros por ano de publicação
    List<Livro> findByAnoPublicacao(Integer anoPublicacao);

    // Exemplo de consulta mais complexa usando @Query com JPQL
    // Encontra livros que contêm uma palavra-chave no título OU pertencem a uma editora específica
    @Query("SELECT l FROM Livro l WHERE LOWER(l.titulo) LIKE LOWER(concat('%', :palavraChave, '%')) OR LOWER(l.editora) = LOWER(:editora)")
    List<Livro> findByTituloOuEditora(@Param("palavraChave") String palavraChave, @Param("editora") String editora);

    // Encontra livros de um determinado autor (pelo ID do autor)
    // Esta consulta JPQL junta Livro com seus Autores e filtra pelo ID do autor.
    @Query("SELECT l FROM Livro l JOIN l.autores a WHERE a.id = :autorId")
    List<Livro> findByAutorId(@Param("autorId") Long autorId);

}