package com.biblioteca.apibiblioteca.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;
    
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    
    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;
    
    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;
    
    @Column(name = "devolvido", nullable = false)
    private boolean devolvido = false;
    
    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;
}