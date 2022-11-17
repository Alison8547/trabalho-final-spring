package br.com.dbc.dbcmovies.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "LANCAMENTOS")
@Document(collection = "log")
public class LancamentosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LANCAMENTOS_SEQ")
    @SequenceGenerator(name = "LANCAMENTOS_SEQ", sequenceName = "SEQ_LANCAMENTOS", allocationSize = 1)
    @Column(name = "id_lancamentos")
    private Integer idLancamentos;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "genero")
    private String genero;

    @Column(name = "sinopse")
    private String sinopse;

    @Column(name = "ano_lancamento")
    private String anoLancamento;

    @Column(name = "classificacao")
    private Integer classificacao;

    @Column(name = "plataforma")
    private String plataforma;

    @Column(name = "duracao")
    private String duracao;
}
