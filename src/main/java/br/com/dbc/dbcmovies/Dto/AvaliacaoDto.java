package br.com.dbc.dbcmovies.Dto;

import lombok.Data;

@Data
public class AvaliacaoDto extends AvaliacaoCreateDto {

    private UsuarioNomeDto usuario;

    private ItemNomeDto itemEntretenimento;
}
