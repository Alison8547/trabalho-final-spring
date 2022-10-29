package br.com.dbc.dbcmovies.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Indicacao {

    private String nomeItem;
    private Integer idUsuario;

}
