package br.com.dbc.dbcmovies.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocadoraDto {
    private UsuarioLocacaoDto usuario;
    private FilmeDisponivelDto filme;
    private Integer qtdDiasLocacao;
    private LocalDateTime data;
}
