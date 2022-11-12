package com.backend.backend.dto;

import com.backend.backend.model.Cidade;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PessoaDTO {
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String apelido;

    @NotNull
    private String timeCoracao;

    @NotNull
    private String cpf;

    @NotNull
    private String hobbie;

    @NotNull
    private Cidade cidade;
}
