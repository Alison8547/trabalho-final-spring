package br.com.dbc.dbcmovies.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AvaliacaoCreateDto {
    @NotNull
    @Schema(example = "9.5")
    private Double nota;

    @Schema(example = "Desenho mutio engraçado e bom pra ver com a familia!")
    private String comentario;
}
