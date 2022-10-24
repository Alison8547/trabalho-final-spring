package br.com.dbc.dbcmovies.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public  class Usuario {

    private Integer id;
    private String nome;
    private Integer idade;
    private String email;
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
