package br.com.dbc.dbcmovies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioAvaliacaoPersonalizadoDto {

    private Integer idUsuario;
    private String nome;
    private Integer idade;
    private String email;
    private String nomeItem;
    private Double nota;
    private String comentario;
}
