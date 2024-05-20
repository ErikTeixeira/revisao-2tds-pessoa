package br.com.fiap.pessoa.resource;

import br.com.fiap.pessoa.dto.request.UsuarioRequest;
import br.com.fiap.pessoa.dto.response.UsuarioResponse;
import br.com.fiap.pessoa.entity.Pessoa;
import br.com.fiap.pessoa.entity.Usuario;
import br.com.fiap.pessoa.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/usuarios")
@RequiredArgsConstructor
public class UsuarioResource implements ResourceDTO<UsuarioRequest, UsuarioResponse> {

    private final UsuarioService service;

    @GetMapping
    public ResponseEntity<Collection<UsuarioResponse>> findAll(

            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "pessoa.nome", required = false) String nome,
            @RequestParam(name = "pessoa.sobrenome", required = false) String sobrenome,
            @RequestParam(name = "pessoa.cpf", required = false) String cpf,
            @RequestParam(name = "pessoa.email", required = false) String email
    ) {

        var pessoa = Pessoa.builder()
                .cpf(cpf)
                .sobrenome(sobrenome)
                .nome(nome)
                .email(email)
                .build();

        var usuario = Usuario.builder()
                .username(username)
                .pessoa(pessoa)
                .build();


        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Usuario> example = Example.of(usuario, matcher);

        var entities = service.findAll(example);

        if (entities.isEmpty()) return ResponseEntity.notFound().build();

        var response = entities.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);

    }


    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {
        var entity = service.findById(id);
        if (entity.isEmpty()) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity.get());
        return ResponseEntity.ok(response);
    }


    @PostMapping
    @Transactional
    @Override
    public ResponseEntity<UsuarioResponse> save(@RequestBody @Valid UsuarioRequest r) {
        var entity = service.toEntity(r);
        service.save(entity);
        var response = service.toResponse(entity);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
