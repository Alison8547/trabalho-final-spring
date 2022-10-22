package br.com.dbc.dbcmovies.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AvaliacaoCreateDto {
    @NotNull
    private Double nota;
    private String comentario;
}
