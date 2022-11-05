package br.com.dbc.dbcmovies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioAssistidoPersonalizadoDto {

    private Integer idUsuario;
    private String nome;
    private Integer idade;
    private String email;
    private Double nota;
    private String comentario;
}
