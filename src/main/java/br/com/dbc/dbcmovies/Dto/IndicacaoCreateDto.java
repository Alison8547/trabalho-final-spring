package br.com.dbc.dbcmovies.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IndicacaoCreateDto {
    @NotBlank
    private String itemNome;
}
