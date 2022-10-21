package br.com.dbc.dbcmovies.entity;

public class Filtro  {
    public String tipo;
    private String genero;
    private Integer classificacao;

    //Construtores
    public Filtro() {
    }

    public Filtro(String tipo, String genero, int classificacao) {
        this.tipo = tipo;
        this.genero = genero;
        this.classificacao = classificacao;
    }

    //GETTERS AND SETTERS
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

}
