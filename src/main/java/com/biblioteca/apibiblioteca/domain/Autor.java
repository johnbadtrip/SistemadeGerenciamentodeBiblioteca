package com.biblioteca.apibiblioteca.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 50)
    private String nacionalidade;

    // Mapeamento para a relação muitos-para-muitos com Livro
    // 'mappedBy = "autores"' indica que a entidade Livro é a dona do relacionamento
    @ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
    private Set<Livro> livros = new HashSet<>();

    // Construtor para facilitar a criação sem a lista de livros inicialmente
    public Autor(String nome, String nacionalidade) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    // Métodos helper para gerenciar a relação bidirecional (opcional, mas bom para consistência)
    public void adicionarLivro(Livro livro) {
        this.livros.add(livro);
        livro.getAutores().add(this);
    }

    public void removerLivro(Livro livro) {
        this.livros.remove(livro);
        livro.getAutores().remove(this);
    }
}