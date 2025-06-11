package com.biblioteca.apibiblioteca.service;

import com.biblioteca.apibiblioteca.domain.Autor;
import com.biblioteca.apibiblioteca.domain.Livro;
import com.biblioteca.apibiblioteca.repository.AutorRepository;
import com.biblioteca.apibiblioteca.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository; // Necessário para associar autores

    @Autowired
    public LivroServiceImpl(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    @Transactional
    public Livro salvar(Livro livro) {
        // Validação: Verificar se o ISBN já existe
        livroRepository.findByIsbn(livro.getIsbn()).ifPresent(l -> {
            throw new IllegalArgumentException("ISBN já cadastrado: " + livro.getIsbn());
        });
        // Lógica para garantir que os autores associados existam ou sejam criados/recuperados
        // Se os autores já têm IDs, eles devem existir no banco.
        // Se são novos autores (sem ID), a cascata PERSIST no @ManyToMany pode criá-los.
        // Para uma abordagem mais controlada, você pode buscar cada autor pelo ID aqui.
        return livroRepository.save(livro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> buscarTodos() {
        return livroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Livro> buscarPorIsbn(String isbn) {
        return livroRepository.findByIsbn(isbn);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    @Transactional
    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com ID: " + id));

        // Validação: Se o ISBN foi alterado, verificar se o novo ISBN já existe para outro livro
        if (!livroExistente.getIsbn().equals(livroAtualizado.getIsbn())) {
            livroRepository.findByIsbn(livroAtualizado.getIsbn()).ifPresent(l -> {
                if (!l.getId().equals(livroExistente.getId())) { // Garante que não é o mesmo livro
                    throw new IllegalArgumentException("Novo ISBN já cadastrado para outro livro: " + livroAtualizado.getIsbn());
                }
            });
        }

        livroExistente.setTitulo(livroAtualizado.getTitulo());
        livroExistente.setIsbn(livroAtualizado.getIsbn());
        livroExistente.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        livroExistente.setEditora(livroAtualizado.getEditora());
        livroExistente.setNumeroPaginas(livroAtualizado.getNumeroPaginas());
        // A atualização de autores associados pode ser mais complexa e pode exigir métodos dedicados
        // como adicionarAutor/removerAutor, ou limpar e adicionar todos os novos autores.
        // Por simplicidade, não estamos atualizando a lista de autores aqui diretamente.
        return livroRepository.save(livroExistente);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new EntityNotFoundException("Livro não encontrado com ID: " + id);
        }
        livroRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> buscarPorAutorId(Long autorId) {
        if (!autorRepository.existsById(autorId)) {
            throw new EntityNotFoundException("Autor não encontrado com ID: " + autorId);
        }
        return livroRepository.findByAutorId(autorId);
    }

    @Override
    @Transactional
    public Livro adicionarAutorAoLivro(Long livroId, Long autorId) {
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com ID: " + livroId));
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com ID: " + autorId));

        livro.adicionarAutor(autor); // Usa o método helper da entidade Livro
        return livroRepository.save(livro);
    }

    @Override
    @Transactional
    public Livro removerAutorDoLivro(Long livroId, Long autorId) {
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com ID: " + livroId));
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com ID: " + autorId));

        livro.removerAutor(autor); // Usa o método helper da entidade Livro
        return livroRepository.save(livro);
    }
}