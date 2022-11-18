package br.com.dbc.dbcmovies.dto;

import lombok.Data;

@Data
public class LocadoraDto {

    private String idLocadora;
    private String nomePessoa;
    private Double preco;
    private Integer diasLocacao;
    private String nomeItem;
    private boolean disponibilidade;
}
