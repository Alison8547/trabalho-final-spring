package br.com.dbc.dbcmovies.dto;

import lombok.Data;

@Data
public class UsuarioNomeDto {

    private Integer idUsuario;
    private String nome;
    private String email;
}
