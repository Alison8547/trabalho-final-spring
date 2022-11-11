package br.com.dbc.dbcmovies.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "USUARIO")
public  class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "login")
    private String login;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "usuario")
    private Set<AvaliacaoEntity> avaliacaos;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "usuarios")
    private Set<ItemEntretenimentoEntity> itemEntretenimentos;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "usuario")
    private Set<IndicacaoEntity> indicacao;

}
