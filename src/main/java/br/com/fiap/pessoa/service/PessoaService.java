package br.com.fiap.pessoa.service;

import br.com.fiap.pessoa.dto.request.PessoaRequest;
import br.com.fiap.pessoa.dto.response.PessoaResponse;
import br.com.fiap.pessoa.entity.Pessoa;
import br.com.fiap.pessoa.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessoaService implements ServiceDTO<Pessoa, PessoaRequest, PessoaResponse> {


    private final PessoaRepository repo;


    @Override
    public Pessoa toEntity(PessoaRequest r) {

        return Pessoa.builder()
                .nome(r.nome())
                .sobrenome(r.sobrenome())
                .cpf(r.cpf())
                .email(r.email())
                .build();

    }

    @Override
    public PessoaResponse toResponse(Pessoa e) {

        return PessoaResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .sobrenome(e.getSobrenome())
                .cpf(e.getCpf())
                .email(e.getEmail())
                .fotos(e.getFotos())
                .build();


    }


    @Override
    public List<Pessoa> findAll(Example<Pessoa> example) {
        return repo.findAll(example);
    }

    @Override
    public Optional<Pessoa> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Pessoa save(Pessoa e) {
        return repo.save(e);
    }
}
