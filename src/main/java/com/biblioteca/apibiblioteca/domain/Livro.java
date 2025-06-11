package com.biblioteca.apibiblioteca.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "livros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Column(length = 100)
    private String editora;

    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    // Mapeamento para a relação muitos-para-muitos com Autor
    // @JoinTable define a tabela de junção
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    // Construtor para facilitar a criação
    public Livro(String titulo, String isbn, Integer anoPublicacao, String editora, Integer numeroPaginas) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.numeroPaginas = numeroPaginas;
    }

    // Métodos helper para gerenciar a relação bidirecional
    public void adicionarAutor(Autor autor) {
        this.autores.add(autor);
        autor.getLivros().add(this);
    }

    public void removerAutor(Autor autor) {
        this.autores.remove(autor);
        autor.getLivros().remove(this);
    }
}