package br.com.dbc.dbcmovies.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ItemEntretenimentoCreateDto {

    @NotBlank
    private String tipo;
    @NotBlank
    @Size(min = 3, max = 255, message = "O nome deve ter de 3 a 255 caracteres.")
    private String nome;
    @NotBlank
    @Size(min = 3, max = 255, message = "O genero deve ter de 3 a 255 caracteres.")
    private String genero;
    @Size(min = 3, message = "Sinopse invália")
    private String sinopse;
    @NotBlank
    private String anoLancamento;
    @NotNull
    private Integer classificacao;
    @NotBlank
    private String plataforma;

}
