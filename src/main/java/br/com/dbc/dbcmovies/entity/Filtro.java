package br.com.dbc.dbcmovies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Filtro  {
    public String tipo;
    private String genero;
    private Integer classificacao;

    //Construtores
    public Filtro() {
    }

}
