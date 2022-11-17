package br.com.dbc.dbcmovies.service;


import br.com.dbc.dbcmovies.dto.*;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.LancamentosEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.LancamentosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LancamentosService {

    private final LancamentosRepository lancamentosRepository;
    private final ObjectMapper objectMapper;


    public LancamentoDto createLancamento(LancamentoCreateDto lancamentoCreateDto) {
        LancamentosEntity lancamentosEntity = objectMapper.convertValue(lancamentoCreateDto, LancamentosEntity.class);
        return objectMapper.convertValue(lancamentosRepository.save(lancamentosEntity), LancamentoDto.class);
    }

    public List<LancamentoDto> list() throws RegraDeNegocioException {
        return lancamentosRepository.findAll().stream()
                .map(lancamentosEntity -> objectMapper.convertValue(lancamentosEntity, LancamentoDto.class))
                .toList();
    }

    
    public LancamentoDto updateLancamento(Integer id, LancamentoDto lancamentoAtualizar) throws RegraDeNegocioException {

        LancamentosEntity lancamentoEncontrado = findById(id);
        lancamentoEncontrado.setNome(lancamentoAtualizar.getNome());
        lancamentoEncontrado.setTipo(lancamentoAtualizar.getTipo());
        lancamentoEncontrado.setGenero(lancamentoAtualizar.getGenero());
        lancamentoEncontrado.setSinopse(lancamentoAtualizar.getSinopse());
        lancamentoEncontrado.setAnoLancamento(lancamentoAtualizar.getAnoLancamento());
        lancamentoEncontrado.setClassificacao(lancamentoAtualizar.getClassificacao());
        lancamentoEncontrado.setPlataforma(lancamentoAtualizar.getPlataforma());
        lancamentoEncontrado.setDuracao(lancamentoAtualizar.getDuracao());
        lancamentosRepository.save(lancamentoEncontrado);

        return objectMapper.convertValue(lancamentoEncontrado, LancamentoDto.class);
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        LancamentosEntity lancamentosEntity = findById(id);
        lancamentosRepository.delete(lancamentosEntity);
    }

    public LancamentosEntity findById(Integer id) throws RegraDeNegocioException {
        return lancamentosRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Item não encontrado!"));
    }

}
