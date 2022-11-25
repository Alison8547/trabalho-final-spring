package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.FilmeDisponivelDto;
import br.com.dbc.dbcmovies.dto.LocadoraDto;
import br.com.dbc.dbcmovies.dto.UsuarioLocacaoDto;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LocadoraService {

    private final ItemService itemService;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final LocadoraProdudorService locadoraProdudorService;
    public void locarFilme(Integer idFilme, Integer qtdDiasLocacao) throws RegraDeNegocioException, JsonProcessingException {
        ItemEntretenimentoEntity itemEntity = itemService.findById(idFilme);
        FilmeDisponivelDto filme = objectMapper.convertValue(itemEntity, FilmeDisponivelDto.class);

        Integer idUsuario = usuarioService.getLoggedUser().getIdUsuario();
        UsuarioLocacaoDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idUsuario), UsuarioLocacaoDto.class);

        LocadoraDto locadora = new LocadoraDto();

        locadora.setUsuario(usuarioDto);
        locadora.setFilme(filme);
        locadora.setQtdDiasLocacao(qtdDiasLocacao);
        locadora.setData(LocalDateTime.now());

        locadoraProdudorService.sendTo(locadora);
    }
}
