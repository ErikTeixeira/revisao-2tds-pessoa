package br.com.fiap.pessoa.dto.response;


import br.com.fiap.pessoa.entity.Foto;
import lombok.Builder;

import java.util.Collection;

@Builder
public record PessoaResponse(


        Long id,

        String nome,

        String sobrenome,

        String email,

        String cpf,

        Collection<Foto> fotos

) {
}
