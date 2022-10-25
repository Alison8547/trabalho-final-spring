package br.com.dbc.dbcmovies.Dto;

import br.com.dbc.dbcmovies.entity.TipoUsuario;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioCreateDto {
    @NotNull
    private String nome;
    @NotNull
    private Integer idade;
    @Email
    private String email;
    @NotNull
    private String senha;

    protected TipoUsuario tipoUsuario;
}
