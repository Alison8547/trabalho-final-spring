package br.com.dbc.dbcmovies.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LocadoraCreateDto {

    @NotNull
    @Schema(description = "Preço do filme" , example = "10,0")
    private Double preco;

    @NotNull
    @Schema(description = "Dias da locação do Filme/Série" , example = "4")
    private Integer diasLocacao;

    @NotBlank
    @Schema(description = "Nome do Filme/Série" , example = "Hulk")
    private String nomeItem;

    @NotNull
    @Schema(description = "Disponibilidade do Filme/Série" , example = "true")
    private boolean disponibilidade;

}
