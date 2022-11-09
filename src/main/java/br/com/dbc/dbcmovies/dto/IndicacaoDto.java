package br.com.dbc.dbcmovies.dto;

import lombok.Data;

@Data
public class IndicacaoDto extends IndicacaoCreateDto{
    private UsuarioDto usuario;
}
