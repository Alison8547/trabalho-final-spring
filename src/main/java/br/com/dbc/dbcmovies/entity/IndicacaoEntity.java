package br.com.dbc.dbcmovies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IndicacaoEntity {

    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(name = "nomeItem")
    private String nomeItem;
}
