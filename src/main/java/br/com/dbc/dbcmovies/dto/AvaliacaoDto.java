package br.com.dbc.dbcmovies.dto;

import lombok.Data;

import java.util.List;

@Data
public class AvaliacaoDto extends AvaliacaoCreateDto {

    private List<UsuarioNomeDto> usuarioNomeDto;
    private List<ItemEntretenimentoDto> itemEntretenimentoDto;
}
