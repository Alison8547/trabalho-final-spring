package br.com.dbc.dbcmovies.entity;

public enum NomeFilmes {
    HOMEM_DE_FERRO("Homem de Ferro", 5.99),
    HULK("O Incrível Hulk", 6.99),
    HOMEM_DE_FERRO_2("Homem de Ferro 2", 6.99),
    THOR("Thor", 6.99),
    CAPITAO_AMERICA("Capitão America - O primeiro vingador", 7.78),
    VINGADORES("Os Vingadores", 7.89),
    HOMEM_DE_FERRO_3("Homem de Ferro 3", 7.99),
    THOR_2("Thor - O Mundo Sombrio", 8.39),
    CAPITAO_AMERICA_2("Capitão America 2 - O Soldado Invernal", 8.49),
    GUARDIOES_DA_GALAXIA("Guardiões da Galáxia", 8.89),
    VINGADORES_2("Vingadores - A Era de Ultron", 8.99),
    HOMEM_FORMIGA("Homem Formiga", 9.39),
    CAPITAO_AMERICA_3("Capitão America - Guerra Civil", 9.49),
    DOUTOR_ESTRANHO("Doutor Estranho", 9.49),
    GUARDIOES_DA_GALAXIA_2("Guardiões da Galáxia - Volume 2", 9.59),
    HOMEM_ARANHA("Homem Aranha - De Volta ao Lar", 9.79),
    THOR_3("Thor Ragnarok", 9.98),
    PANTERA_NEGRA("Pantera Negra", 10.00),
    VINGADORES_3("Vingadores - Guerra Infinita", 10.29),
    HOMEM_FORMIGA_2("Homem Formiga e a Vespa", 10.59),
    CAPITA_MARVEL("Capitã Marvel", 10.59),
    VINGADORES_4("Vingadores - Ultimato", 10.79),
    HOMEM_ARANHA_2("Homem Aranha - Sem Volta Pra Casa", 10.99),;

    private String descricao;
    private Double preco;

    NomeFilmes(String descricao, Double preco) {
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }
}
