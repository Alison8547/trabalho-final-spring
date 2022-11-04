package br.com.dbc.dbcmovies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FiltroEntity {
    public String tipo;
    private String genero;
    private Integer classificacao;

    //Construtores
    public FiltroEntity() {
    }

}
