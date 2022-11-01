package br.com.dbc.dbcmovies.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IndicacaoCreateDto {
    @NotBlank
    @Schema(example = "Hulk")
    private String itemNome;
}
