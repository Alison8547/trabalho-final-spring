package br.com.dbc.dbcmovies.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UsuarioItemPersonalizadoDto {

    private Integer idUsuario;
    private String nome;
    private Integer idade;
    private String email;
    private String nomeItem;
    private String tipo;
    private String genero;
    private String sinopse;
    private String anoLancamento;
    private Integer classificacao;
}
