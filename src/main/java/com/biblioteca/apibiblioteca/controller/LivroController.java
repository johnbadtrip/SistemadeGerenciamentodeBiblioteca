package com.biblioteca.apibiblioteca.controller;

import com.biblioteca.apibiblioteca.domain.Autor;
import com.biblioteca.apibiblioteca.domain.Livro;
import com.biblioteca.apibiblioteca.service.LivroService;
import com.biblioteca.apibiblioteca.service.AutorService;
import com.biblioteca.apibiblioteca.controller.dto.LivroDTO;
import com.biblioteca.apibiblioteca.controller.dto.CriarAtualizarLivroDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;
    private final AutorService autorService; // Para buscar entidades Autor

    @Autowired
    public LivroController(LivroService livroService, AutorService autorService) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    // POST /api/livros - Criar um novo livro
    @PostMapping
    public ResponseEntity<?> criarLivro(@Valid @RequestBody CriarAtualizarLivroDTO livroDTO) {
        try {
            Livro livro = new Livro();
            livro.setTitulo(livroDTO.getTitulo());
            livro.setIsbn(livroDTO.getIsbn());
            livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
            livro.setEditora(livroDTO.getEditora());
            livro.setNumeroPaginas(livroDTO.getNumeroPaginas());

            Set<Autor> autores = livroDTO.getAutorIds().stream()
                    .map(autorId -> autorService.buscarPorId(autorId)
                            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Autor não encontrado com ID: " + autorId)))
                    .collect(Collectors.toSet());
            livro.setAutores(autores);

            Livro novoLivro = livroService.salvar(livro);
            return new ResponseEntity<>(LivroDTO.fromEntity(novoLivro), HttpStatus.CREATED);
        } catch (IllegalArgumentException | jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/livros - Listar todos os livros
    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarLivros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Long autorId) {
        List<Livro> livros;
        if (titulo != null && !titulo.isEmpty()) {
            livros = livroService.buscarPorTitulo(titulo);
        } else if (autorId != null) {
            livros = livroService.buscarPorAutorId(autorId);
        } else {
            livros = livroService.buscarTodos();
        }
        List<LivroDTO> livrosDTO = livros.stream()
                .map(LivroDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(livrosDTO);
    }

    // GET /api/livros/{id} - Buscar livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarLivroPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(LivroDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/livros/{id} - Atualizar um livro existente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarLivro(@PathVariable Long id, @Valid @RequestBody CriarAtualizarLivroDTO livroDTO) {
        try {
            Livro livroAtualizado = new Livro();
            livroAtualizado.setTitulo(livroDTO.getTitulo());
            livroAtualizado.setIsbn(livroDTO.getIsbn());
            livroAtualizado.setAnoPublicacao(livroDTO.getAnoPublicacao());
            livroAtualizado.setEditora(livroDTO.getEditora());
            livroAtualizado.setNumeroPaginas(livroDTO.getNumeroPaginas());

            // Lógica para atualizar autores pode ser mais complexa
            // Aqui, estamos substituindo o conjunto de autores
            Set<Autor> autores = livroDTO.getAutorIds().stream()
                    .map(autorId -> autorService.buscarPorId(autorId)
                            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Autor não encontrado com ID: " + autorId)))
                    .collect(Collectors.toSet());
            // O serviço de atualização deve lidar com a lógica de definir os autores no livro existente
            // livroAtualizado.setAutores(autores); // Esta linha não é necessária se o serviço lida com isso

            Livro livroSalvo = livroService.atualizar(id, livroAtualizado); // O serviço precisa ser ajustado para aceitar e definir os autores
            // Para o método atualizar do service, você precisará passar os IDs dos autores e o serviço
            // deverá buscar as entidades Autor e associá-las ao livro existente.
            // Por enquanto, o método atualizar do LivroServiceImpl não lida com a atualização de autores.
            // Vamos simplificar por agora e o método de serviço atualizará apenas os campos diretos do livro.
            // Para uma atualização completa, o LivroServiceImpl.atualizar precisaria de lógica para lidar com os autores.

            // Re-buscando o livro para obter o estado atualizado com autores (se o serviço os atualizou)
            Livro livroComAutoresAtualizados = livroService.buscarPorId(id).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Livro não encontrado após atualização com ID: " + id));
            livroComAutoresAtualizados.setAutores(autores); // Definindo manualmente para o DTO por enquanto
            livroService.salvar(livroComAutoresAtualizados); // Salva as alterações nos autores

            return ResponseEntity.ok(livroSalvo);
        } catch (IllegalArgumentException | jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/livros/{id} - Deletar um livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        try {
            livroService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/livros/{livroId}/autores/{autorId} - Associar um autor a um livro
    @PostMapping("/{livroId}/autores/{autorId}")
    public ResponseEntity<?> adicionarAutorAoLivro(@PathVariable Long livroId, @PathVariable Long autorId) {
        try {
            Livro livroAtualizado = livroService.adicionarAutorAoLivro(livroId, autorId);
            return ResponseEntity.ok(LivroDTO.fromEntity(livroAtualizado));
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE /api/livros/{livroId}/autores/{autorId} - Desassociar um autor de um livro
    @DeleteMapping("/{livroId}/autores/{autorId}")
    public ResponseEntity<?> removerAutorDoLivro(@PathVariable Long livroId, @PathVariable Long autorId) {
        try {
            Livro livroAtualizado = livroService.removerAutorDoLivro(livroId, autorId);
            return ResponseEntity.ok(LivroDTO.fromEntity(livroAtualizado));
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().body(e.getMessage());
        }
    }
}