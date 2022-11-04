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
public class IndicacaoPK implements Serializable {

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nome_item")
    private String nomeItem;

}
