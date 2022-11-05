package br.com.dbc.dbcmovies.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ItemEntretenimentoCreateDto {

    @NotBlank
    @Schema(description = "Tipo de item" , example = "filme")
    private String tipo;
    @NotBlank
    @Size(min = 3, max = 255, message = "O nome deve ter de 3 a 255 caracteres.")
    @Schema(description = "Nome do Filme/Série." , example = "Os sem-floresta")
    private String nome;
    @NotBlank
    @Size(min = 3, max = 255, message = "O genero deve ter de 3 a 255 caracteres.")
    @Schema(example = "animacao")
    private String genero;
    @Size(min = 3, message = "Sinopse invália")
    @Schema(description = "Sinopse do Filme/Série", example = "Animais de uma floresta acordam do período de hibernação e descobrem que têm vários vizinhos humanos que vivem nas proximidades.")
    private String sinopse;
    @NotBlank
    @Schema(example = "2006")
    private String anoLancamento;
    @NotNull
    @Schema(description = "Classificação livre = 12",example = "12")
    private Integer classificacao;
    @NotBlank
    @Schema(example = "Apple TV")
    private String plataforma;
}
