package com.biblioteca.service;

import com.biblioteca.apibiblioteca.domain.Livro;
import com.biblioteca.apibiblioteca.domain.Emprestimo;
import com.biblioteca.apibiblioteca.repository.LivroRepository;
import com.biblioteca.apibiblioteca.repository.EmprestimoRepository;

public class EmprestimoService {
    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(LivroRepository livroRepository, EmprestimoRepository emprestimoRepository) {
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public void emprestarLivro(Long livroId, Long usuarioId) {
        // Verificar se o usuário já atingiu o limite de 3 empréstimos ativos
        long quantidadeEmprestimosAtivos = emprestimoRepository.countByUsuarioIdAndAtivoTrue(usuarioId);
        if (quantidadeEmprestimosAtivos >= 3) {
            throw new IllegalStateException("Usuário já atingiu o limite de empréstimos");
        }
        
        Livro livro = livroRepository.findById(livroId).orElseThrow();
        if (livro.isEmprestado()) {
            throw new IllegalStateException("Livro já está emprestado");
        }
        livro.setEmprestado(true);
        livroRepository.save(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuarioId(usuarioId);
        emprestimo.setDataEmprestimo(java.time.LocalDate.now());
        emprestimo.setDevolvido(false);
        emprestimo.setAtivo(true);
        emprestimoRepository.save(emprestimo);
    }
}