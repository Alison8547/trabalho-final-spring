package br.com.dbc.dbcmovies.dto;

import lombok.Data;

@Data
public class AvaliacaoDto extends AvaliacaoCreateDto {

    private Integer idUsuario;

    private Integer idItemEntretenimento;
}
