package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.LocadoraCreateDto;
import br.com.dbc.dbcmovies.dto.LocadoraDto;
import br.com.dbc.dbcmovies.entity.LocadoraEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.LocadoraRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocadoraService {

    private final LocadoraRepository locadoraRepository;
    private final ObjectMapper objectMapper;

    public LocadoraDto alugar(LocadoraCreateDto locadoraCreateDto) {
        LocadoraEntity locadoraEntity = objectMapper.convertValue(locadoraCreateDto, LocadoraEntity.class);
        Integer diasLocacao = locadoraEntity.getDiasLocacao();
        Double preco = locadoraEntity.getPreco();
        Double result = preco * diasLocacao;
        locadoraEntity.setPreco(result);
        return objectMapper.convertValue(locadoraRepository.save(locadoraEntity), LocadoraDto.class);
    }


    public List<LocadoraDto> list() throws RegraDeNegocioException {
        return locadoraRepository.findAll().stream()
                .map(locadoraEntity -> objectMapper.convertValue(locadoraEntity, LocadoraDto.class))
                .toList();
    }



}
