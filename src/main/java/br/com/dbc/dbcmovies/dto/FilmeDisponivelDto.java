package br.com.dbc.dbcmovies.dto;

import lombok.Data;

@Data
public class FilmeDisponivelDto {
    private Integer idItem;
    private String nome;
    private String genero;
    private String anoLancamento;
    private Integer classificacao;
    private String duracao;
    private Double preco;
}
