package br.com.dbc.dbcmovies.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FilmeCreateDto extends ItemEntretenimentoCreateDto{

    @NotNull
    private String duracao;
}
