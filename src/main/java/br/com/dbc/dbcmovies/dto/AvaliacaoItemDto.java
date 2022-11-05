package br.com.dbc.dbcmovies.dto;

import lombok.Data;

@Data
public class AvaliacaoItemDto {
    private Integer idUsuario;
    private Integer idItem;
    private Double nota;
    private String comentario;
}
