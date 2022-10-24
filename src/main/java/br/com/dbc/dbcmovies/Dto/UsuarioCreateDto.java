package br.com.dbc.dbcmovies.Dto;

import br.com.dbc.dbcmovies.entity.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioCreateDto {
    private String nome;
    @NotNull
    private Integer idade;
    @Email
    private String email;
    @NotNull
    @JsonIgnore
    private String senha;

    protected TipoUsuario tipoUsuario;
}
