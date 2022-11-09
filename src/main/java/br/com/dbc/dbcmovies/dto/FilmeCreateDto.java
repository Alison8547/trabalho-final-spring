package br.com.dbc.dbcmovies.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FilmeCreateDto extends ItemEntretenimentoCreateDto{
    @NotNull
    private String duracao;
}
