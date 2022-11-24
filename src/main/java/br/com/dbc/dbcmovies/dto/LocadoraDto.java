package br.com.dbc.dbcmovies.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocadoraDto {
    private UsuarioLocacaoDto usuario;
    private LocalDateTime data;
    private String nomeFilme;
    private Double precoFilme;
    private Integer qtdDiasLocacao;
}
