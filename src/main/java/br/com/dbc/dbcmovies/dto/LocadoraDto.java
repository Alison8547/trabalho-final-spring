package br.com.dbc.dbcmovies.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocadoraDto {
    private UsuarioDto usuario;
    private LocalDateTime data;
    private String nomeItem;
    private Integer diaAlugado;

}
