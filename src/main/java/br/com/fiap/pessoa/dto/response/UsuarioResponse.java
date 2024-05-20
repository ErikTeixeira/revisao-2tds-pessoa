package br.com.fiap.pessoa.dto.response;


import lombok.Builder;

@Builder
public record UsuarioResponse(

        Long id,
        String username,
        PessoaResponse pessoa

) {
}
