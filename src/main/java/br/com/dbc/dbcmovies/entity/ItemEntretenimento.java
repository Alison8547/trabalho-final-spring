package br.com.dbc.dbcmovies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntretenimento{

    private Integer id;
    private String tipo;
    private String nome;
    private String genero;
    private String sinopse;
    private String anoLancamento;
    private Integer classificacao;
    private String plataforma;
    private Double mediaAvaliacoes;
    private String duracao;
    private int temporadas;
    private int episodios;

    //Construtor
    public ItemEntretenimento(Integer id, String nome, String genero, String sinopse, String anoLancamento, Integer classificacao, String plataforma) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.sinopse = sinopse;
        this.anoLancamento = anoLancamento;
        this.classificacao = classificacao;
        this.plataforma = plataforma;
    }
}
