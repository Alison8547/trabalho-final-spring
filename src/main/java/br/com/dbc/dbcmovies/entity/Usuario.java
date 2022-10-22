package br.com.dbc.dbcmovies.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public  class Usuario {

    private Integer id;
    @NotNull
    private String nome;
    @NotNull
    private Integer idade;
    @Email
    private String email;
    @NotNull
    private String senha;

    protected TipoUsuario tipoUsuario;

    public Usuario() {

    }


    public void setUsuarioAdmin(Usuario cliente){
        if(this.tipoUsuario.equals(TipoUsuario.ADMINISTRADOR)){
            cliente.tipoUsuario = TipoUsuario.ADMINISTRADOR;
        }
    }

}
