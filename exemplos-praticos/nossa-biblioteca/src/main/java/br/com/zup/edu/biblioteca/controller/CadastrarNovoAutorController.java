package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Autor;
import br.com.zup.edu.biblioteca.repository.AutorRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@RestController
@RequestMapping("/autores")
public class CadastrarNovoAutorController {
    private final AutorRepository repository;

    public CadastrarNovoAutorController(AutorRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Void> cadatrar(@RequestHeader("If-Match") String email, @RequestBody @Valid AutorRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Autor novoAutor = request.paraAutor();
//        boolean exists = repository.existsByEmail(email);
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("email", ignoreCase());
        Example<Autor> example = Example.of(novoAutor, modelMatcher);
        boolean exists = repository.exists(example);
        if(exists) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Já existe um pagamento para este pedido");
        }

        repository.save(novoAutor);
        URI location = uriComponentsBuilder.path("/autores/{id}")
                .buildAndExpand(novoAutor.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
