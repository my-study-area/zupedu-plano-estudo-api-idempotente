package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Livro;
import br.com.zup.edu.biblioteca.repository.LivroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/livros")
public class CadastrarNovoLivroController {
    private final LivroRepository repository;

    public CadastrarNovoLivroController(LivroRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Void> cadastar(@RequestBody @Valid LivroRequest request, UriComponentsBuilder uriComponentsBuilder) {

        Livro novoLivro = request.paraLivro();

        repository.save(novoLivro);

        URI location = UriComponentsBuilder.fromPath("/livros/{id}")
                .buildAndExpand(novoLivro.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
