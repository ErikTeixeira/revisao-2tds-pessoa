package br.com.fiap.pessoa.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record PessoaRequest(

        @NotNull
        @NotBlank
        String nome,

        @NotNull
        @NotBlank
        String sobrenome,

        @Email
        @NotNull
        @NotBlank
        String email,

        @NotNull
        @NotBlank
        @CPF String cpf

) {
}
