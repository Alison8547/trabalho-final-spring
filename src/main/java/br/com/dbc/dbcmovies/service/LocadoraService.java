package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.LocadoraCreateDto;
import br.com.dbc.dbcmovies.dto.LocadoraDto;
import br.com.dbc.dbcmovies.entity.LocadoraEntity;
import br.com.dbc.dbcmovies.repository.LocadoraRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocadoraService {

    private final LocadoraRepository locadoraRepository;
    private final ObjectMapper objectMapper;

    public LocadoraDto createLancamento(LocadoraCreateDto locadoraCreateDto) {
        LocadoraEntity locadoraEntity = objectMapper.convertValue(locadoraCreateDto, LocadoraEntity.class);
        return objectMapper.convertValue(locadoraRepository.save(locadoraEntity), LocadoraDto.class);
    }




}
