package br.com.dbc.dbcmovies.entity;

public enum TipoUsuario {
    CLIENTE("cliente"),
    ADMINISTRADOR("administrador");

    private String descricao;

    TipoUsuario(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
