package br.com.dbc.dbcmovies.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
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
