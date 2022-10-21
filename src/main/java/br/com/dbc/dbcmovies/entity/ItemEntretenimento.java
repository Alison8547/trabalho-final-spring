package br.com.dbc.dbcmovies.entity;

public class ItemEntretenimento{

    protected Integer id;
    protected String tipo;
    private String nome;
    private String genero;
    private String sinopse;
    private String anoLancamento;
    private Integer classificacao;
    private String plataforma;
    private Double MediaAvaliacoes;
    private String duracao;
    private Integer temporadas;
    private Integer episodios;


    //Construtores
    public ItemEntretenimento() {
    }

    public ItemEntretenimento(String nome, String genero) {
        this.nome = nome;
        this.genero = genero;
    }

    public ItemEntretenimento(Integer id, String nome, String genero, String sinopse, String anoLancamento, Integer classificacao, String plataforma) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.sinopse = sinopse;
        this.anoLancamento = anoLancamento;
        this.classificacao = classificacao;
        this.plataforma = plataforma;
    }

    //GETTERS AND SETTERS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Double getMediaAvaliacoes() {
        return MediaAvaliacoes;
    }

    public void setMediaAvaliacoes(Double mediaAvaliacoes) {
        MediaAvaliacoes = mediaAvaliacoes;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Integer getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(Integer temporadas) {
        this.temporadas = temporadas;
    }

    public Integer getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Integer episodios) {
        this.episodios = episodios;
    }


}
