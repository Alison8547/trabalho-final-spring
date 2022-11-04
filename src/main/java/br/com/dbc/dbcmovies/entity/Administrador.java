package br.com.dbc.dbcmovies.entity;

public class Administrador extends UsuarioEntity {

    public Administrador() {
        super();
        tipoUsuario = TipoUsuario.ADMINISTRADOR;
    }

    public Administrador(Integer id, String nome, int idade, String email, String senha) {
        super(id, nome, idade, email, senha, TipoUsuario.ADMINISTRADOR);
    }
}
