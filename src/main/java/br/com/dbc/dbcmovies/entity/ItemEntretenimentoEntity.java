package br.com.dbc.dbcmovies.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "ITEM_ENTRETENIMENTO")
public class ItemEntretenimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_ENTRETENIMENTO_SEQ")
    @SequenceGenerator(name = "ITEM_ENTRETENIMENTO_SEQ", sequenceName = "SEQ_ITEM_ENTRETENIMENTO", allocationSize = 1)
    @Column(name = "id_item_entretenimento")
    private Integer idItem;

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

    @Column(name = "temporadas")
    private Integer temporadas;

    @Column(name = "episodios")
    private Integer episodios;

    @Column(name = "disponibilidade")
    private Integer disponibilidade;

    @Column(name = "preco")
    private Double preco;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ASSISTIDOS",
            joinColumns = @JoinColumn(name = "id_item_entretenimento"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<UsuarioEntity> usuarios;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "itemEntretenimento")
    private Set<AvaliacaoEntity> avaliacaos;

}
