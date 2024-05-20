package br.com.fiap.pessoa.resource;


import br.com.fiap.pessoa.dto.request.PessoaRequest;
import br.com.fiap.pessoa.dto.response.PessoaResponse;
import br.com.fiap.pessoa.entity.Foto;
import br.com.fiap.pessoa.entity.Pessoa;
import br.com.fiap.pessoa.service.FotoService;
import br.com.fiap.pessoa.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pessoas")
@RequiredArgsConstructor
public class PessoaResource implements ResourceDTO<PessoaRequest, PessoaResponse> {

    private final PessoaService service;
    private final FotoService fotoService;


    @GetMapping
    public ResponseEntity<Collection<PessoaResponse>> findAll(

            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "sobrenome", required = false) String sobrenome,
            @RequestParam(name = "cpf", required = false) String cpf,
            @RequestParam(name = "email", required = false) String email

    ) {

        var pessoa = Pessoa.builder()
                .nome(nome)
                .sobrenome(sobrenome)
                .cpf(cpf)
                .email(email)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Pessoa> example = Example.of(pessoa, matcher);

        var entities = service.findAll(example);

        if (entities.isEmpty()) return ResponseEntity.notFound().build();

        var response = entities.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);

    }


    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<PessoaResponse> findById(@PathVariable Long id) {
        var entity = service.findById(id);
        if (entity.isEmpty()) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity.get());
        return ResponseEntity.ok(response);
    }


    @PostMapping
    @Transactional
    @Override
    public ResponseEntity<PessoaResponse> save(@RequestBody @Valid PessoaRequest r) {
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


    @PostMapping(value = "/{id}/fotos")
    @Transactional
    public ResponseEntity<PessoaResponse> addFoto(@RequestPart MultipartFile file, @PathVariable Long id) {
        //TODO: Precisamos implementar.

        var entity = service.findById(id);

        if (entity.isEmpty()) return ResponseEntity.badRequest().build();

        var extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        // Se precisar restringir é só criar uma coleção com o que é permitido e verificar se a extensão está na lista


        Foto foto = Foto.builder()
                .src(entity.get().getClass().getSimpleName().toLowerCase()
                        + "_" + entity.get().getId()
                        + "_" + UUID.randomUUID().toString()
                        + "." + extension)
                .build();

        var salvou = fotoService.upload(file, foto);

        if (!salvou) return ResponseEntity.badRequest().build();


        if (Objects.isNull(entity.get().getFotos())) entity.get().setFotos(new LinkedHashSet<>());


        entity.get().getFotos().add(foto);

        var response = service.toResponse(entity.get());

        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(entity.get().getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

}
