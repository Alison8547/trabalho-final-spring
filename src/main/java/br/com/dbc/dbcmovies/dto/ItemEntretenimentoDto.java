package br.com.dbc.dbcmovies.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ItemEntretenimentoDto extends ItemEntretenimentoCreateDto{

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Integer temporadas;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Integer episodios;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String duracao;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Double mediaAvaliacoes;

    private Integer idItem;
}
