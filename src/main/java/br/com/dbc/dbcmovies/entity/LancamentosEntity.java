package br.com.dbc.dbcmovies.entity;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "lancamentos")
public class LancamentosEntity {

    @Id
    private String idLancamento;

    private String nome;

    private String tipo;

    private String genero;

    private String sinopse;

    private String anoLancamento;

    private Integer classificacao;

    private String plataforma;

    private String duracao;

    private String dataLancamento;
}
