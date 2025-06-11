package com.biblioteca.apibiblioteca.controller;

import com.biblioteca.apibiblioteca.domain.Autor;
import com.biblioteca.apibiblioteca.service.AutorService;
import com.biblioteca.apibiblioteca.controller.dto.AutorDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autores") // Define o caminho base para os endpoints deste controlador
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // POST /api/autores - Criar um novo autor
    @PostMapping
    public ResponseEntity<AutorDTO> criarAutor(@Valid @RequestBody AutorDTO autorDTO) {
        Autor autor = new Autor(autorDTO.getNome(), autorDTO.getNacionalidade());
        Autor novoAutor = autorService.salvar(autor);
        AutorDTO responseDTO = new AutorDTO(novoAutor.getId(), novoAutor.getNome(), novoAutor.getNacionalidade());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // GET /api/autores - Listar todos os autores
    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarAutores() {
        List<AutorDTO> autoresDTO = autorService.buscarTodos().stream()
                .map(autor -> new AutorDTO(autor.getId(), autor.getNome(), autor.getNacionalidade()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(autoresDTO);
    }

    // GET /api/autores/{id} - Buscar autor por ID
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarAutorPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id)
                .map(autor -> new AutorDTO(autor.getId(), autor.getNome(), autor.getNacionalidade()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/autores/{id} - Atualizar um autor existente
    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> atualizarAutor(@PathVariable Long id, @Valid @RequestBody AutorDTO autorDTO) {
        Autor autorAtualizado = new Autor(autorDTO.getNome(), autorDTO.getNacionalidade());
        try {
            Autor autorSalvo = autorService.atualizar(id, autorAtualizado);
            AutorDTO responseDTO = new AutorDTO(autorSalvo.getId(), autorSalvo.getNome(), autorSalvo.getNacionalidade());
            return ResponseEntity.ok(responseDTO);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/autores/{id} - Deletar um autor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id) {
        try {
            autorService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}