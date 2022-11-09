package br.com.dbc.dbcmovies.entity;

import br.com.dbc.dbcmovies.entity.pk.IndicacaoPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "INDICACAO")
public class IndicacaoEntity {

    @EmbeddedId
    private IndicacaoPK indicacaoPK;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;
}

