package com.backend.backend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeDTO {
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String estado;

    @NotNull
    private Long qtdHabitantes;
}
