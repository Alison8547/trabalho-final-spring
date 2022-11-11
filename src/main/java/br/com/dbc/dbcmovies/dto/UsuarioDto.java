package br.com.dbc.dbcmovies.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioDto {
    @Schema(description = "Nome do usuário", example = "Alison")
    private String nome;


    @NotNull
    @Schema(description = "Idade do usuário", example = "20")
    private Integer idade;

    @Email
    @Schema(description = "Email do usuário", example = "alison@hotmail.com")
    private String email;


    @Schema(description = "Id do usuário")
    private Integer idUsuario;
}
