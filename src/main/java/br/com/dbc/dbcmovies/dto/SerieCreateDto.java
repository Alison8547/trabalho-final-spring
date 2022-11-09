package br.com.dbc.dbcmovies.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SerieCreateDto extends ItemEntretenimentoCreateDto{
    @NotNull(message = "Quantidade de temporadas é obrigatório")
    private Integer temporadas;

    @NotNull(message = "Quantidade de episodios é obrigatório")
    private Integer episodios;
}
