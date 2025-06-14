package com.biblioteca.apibiblioteca.service;

import com.biblioteca.apibiblioteca.domain.Emprestimo;
import com.biblioteca.apibiblioteca.domain.Livro;
import com.biblioteca.apibiblioteca.domain.Usuario;
import com.biblioteca.apibiblioteca.repository.EmprestimoRepository;
import com.biblioteca.apibiblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class EmprestimoService {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private EmprestimoRepository emprestimoRepository;
    
    @Transactional
    public void emprestarLivro(Long livroId, Long usuarioId) {
        // Verificar se o usuário já tem 3 empréstimos ativos
        long quantidadeEmprestimosAtivos = emprestimoRepository.countByUsuarioIdAndDevolvidoFalse(usuarioId);
        if (quantidadeEmprestimosAtivos >= 3) {
            throw new IllegalStateException("Usuário não pode ter mais de três empréstimos ativos.");
        }
        
        // Buscar o livro
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        
        // Verificar se o livro está disponível
        if (livro.isEmprestado()) {
            throw new IllegalStateException("Livro já está emprestado");
        }
        
        // Marcar livro como emprestado
        livro.setEmprestado(true);
        livroRepository.save(livro);
        
        // Criar registro de empréstimo
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuarioId(usuarioId);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDevolvido(false);
        emprestimoRepository.save(emprestimo);
    }
}