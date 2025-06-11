package com.biblioteca.apibiblioteca.service;

import com.biblioteca.apibiblioteca.domain.Autor;
import com.biblioteca.apibiblioteca.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorServiceImpl(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    @Transactional
    public Autor salvar(Autor autor) {
        // Adicionar validações aqui se necessário antes de salvar
        return autorRepository.save(autor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> buscarTodos() {
        return autorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Autor> buscarPorNome(String nome) {
        return autorRepository.findByNome(nome);
    }

    @Override
    @Transactional
    public Autor atualizar(Long id, Autor autorAtualizado) {
        Autor autorExistente = autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com ID: " + id));

        autorExistente.setNome(autorAtualizado.getNome());
        autorExistente.setNacionalidade(autorAtualizado.getNacionalidade());
        // Não atualizamos a lista de livros diretamente aqui, isso seria gerenciado pela entidade Livro
        return autorRepository.save(autorExistente);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new EntityNotFoundException("Autor não encontrado com ID: " + id);
        }
        // Considerar o que acontece com os livros associados. 
        // A configuração atual do relacionamento pode precisar de ajustes (ex: cascade on delete)
        // ou lógica de remoção explícita dos livros ou da associação.
        autorRepository.deleteById(id);
    }
}