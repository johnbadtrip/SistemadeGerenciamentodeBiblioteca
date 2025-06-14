package com.biblioteca.apibiblioteca.repository;

import com.biblioteca.apibiblioteca.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    long countByUsuarioIdAndDevolvidoFalse(Long usuarioId);
    long countByUsuarioIdAndAtivoTrue(Long usuarioId);
}