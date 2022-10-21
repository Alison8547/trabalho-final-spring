package br.com.dbc.dbcmovies.entity;


public  class Usuario {

    private Integer id;
    private String nome;
    private Integer idade;
    private String email;
    private String senha;

    protected TipoUsuario tipoUsuario;

    //Construtores
    public Usuario(Integer id, String nome, int idade, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.senha = senha;
        tipoUsuario = TipoUsuario.CLIENTE;
    }

    //GETTERS AND SETTERS

    public Usuario() {
        tipoUsuario = TipoUsuario.CLIENTE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //GETTERS AND SETTERS
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario(){
        return this.tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setUsuarioAdmin(Usuario cliente){
        if(this.tipoUsuario.equals(TipoUsuario.ADMINISTRADOR)){
            cliente.tipoUsuario = TipoUsuario.ADMINISTRADOR;
        }
    }

}
