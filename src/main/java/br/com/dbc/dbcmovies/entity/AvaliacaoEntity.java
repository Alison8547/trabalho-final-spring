package br.com.dbc.dbcmovies.entity;

import br.com.dbc.dbcmovies.entity.pk.AvaliacaoPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Embeddable
@Entity(name = "AVALIACAO")
public class AvaliacaoEntity {

    @Id
    @EmbeddedId
    private AvaliacaoPK avaliacaoPK;

    @Column(name = "nota")
    private Double nota;

    @Column(name = "comentario")
    private String comentario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idItem")
    @JoinColumn(name = "id_Item_Entretenimento")
    private ItemEntretenimentoEntity itemEntretenimento;

}
