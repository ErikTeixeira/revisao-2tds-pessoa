package br.com.fiap.pessoa.service;

import br.com.fiap.pessoa.dto.request.UsuarioRequest;
import br.com.fiap.pessoa.dto.response.UsuarioResponse;
import br.com.fiap.pessoa.entity.Usuario;
import br.com.fiap.pessoa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements ServiceDTO<Usuario, UsuarioRequest, UsuarioResponse> {

    private final UsuarioRepository repo;

    private final PessoaService pessoaService;


    @Override
    public Usuario toEntity(UsuarioRequest r) {

        var pessoa = pessoaService.toEntity(r.pessoa());

        return Usuario.builder()
                .pessoa(pessoa)
                .username(r.username())
                .password(r.password())
                .build();
    }

    @Override
    public UsuarioResponse toResponse(Usuario e) {
        var pessoa = pessoaService.toResponse(e.getPessoa());

        return UsuarioResponse.builder()
                .pessoa(pessoa)
                .username(e.getUsername())
                .id(e.getId())
                .build();
    }

    @Override
    public List<Usuario> findAll(Example<Usuario> example) {
        return repo.findAll(example);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Usuario save(Usuario e) {
        return repo.save(e);
    }
}
