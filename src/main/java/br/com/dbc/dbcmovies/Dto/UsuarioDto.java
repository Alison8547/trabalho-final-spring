package br.com.dbc.dbcmovies.Dto;

import br.com.dbc.dbcmovies.entity.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioDto {

    @Schema(description = "Usuariodeteste")
    private String nome;

    @Schema(description = "25")
    @NotNull
    private Integer idade;

    @Schema(description = "emaildeteste@gmail.com.br")
    @Email
    private String email;

    protected TipoUsuario tipoUsuario;
    private Integer id;
}
