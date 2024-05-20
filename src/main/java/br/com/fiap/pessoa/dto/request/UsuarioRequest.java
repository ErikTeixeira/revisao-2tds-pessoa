package br.com.fiap.pessoa.dto.request;

public record UsuarioRequest(

        String username,
        String password,
        PessoaRequest pessoa


) {
}
