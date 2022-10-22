package br.com.dbc.dbcmovies.Dto;

import lombok.Data;

@Data
public class AvaliacaoDto extends AvaliacaoCreateDto {

    private String nomeUsuario;
    private String nomeEntretenimento;

}
