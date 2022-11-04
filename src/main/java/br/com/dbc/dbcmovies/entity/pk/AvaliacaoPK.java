package br.com.dbc.dbcmovies.entity.pk;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class AvaliacaoPK implements Serializable {

    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(name = "idItem_Entretenimento")
    private Integer idItemEntretenimento;
}
