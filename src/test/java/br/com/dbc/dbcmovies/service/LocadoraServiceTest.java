package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.*;
import br.com.dbc.dbcmovies.entity.*;
import br.com.dbc.dbcmovies.entity.pk.AvaliacaoPK;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.LocadoraRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocadoraServiceTest {
    @InjectMocks
    private LocadoraService locadoraService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private LocadoraRepository locadoraRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(locadoraService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        // lancamentosRepository.findAll()
        List<LocadoraEntity> lista = new ArrayList<>();
        lista.add(getLocadoraEntity());
        when(locadoraRepository.findAll()).thenReturn(lista);

        // Ação (ACT)
        List<LocadoraDto> list = locadoraService.list();

        // Verificação (ASSERT)
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());

    }

    @Test
    public void deveTestarAlugarComSucesso(){
        // SETUP
        LocadoraCreateDto locadoraCreateDto = getLocadoraCreateDto();

        //findById;
        LocadoraEntity locadoraEntity = getLocadoraEntity();
        locadoraEntity.setIdLocadora("1");
        locadoraEntity.setNomePessoa("Eduardo");
        locadoraEntity.setPreco(20.0);
        locadoraEntity.setDiasLocacao(1);
        locadoraEntity.setNomeItem("VHS da xuxa");
        locadoraEntity.setDisponibilidade(true);

        //(locadoraRepository.save(locadoraEntity)
        LocadoraEntity locadoraEntity1 = getLocadoraEntity();
        when(locadoraRepository.save(any())).thenReturn(locadoraEntity1);

        // ACT
        LocadoraDto locadoraDto = locadoraService.alugar(locadoraCreateDto);

        // ASSERT
        assertNotNull(locadoraDto);
        assertNotEquals(2, locadoraDto.getDiasLocacao());
        assertNotEquals(40,locadoraDto.getPreco());
    }


    private static LocadoraEntity getLocadoraEntity() {
        LocadoraEntity locadoraEntity = new LocadoraEntity();
        locadoraEntity.setIdLocadora("1");
        locadoraEntity.setNomePessoa("Eduardo");
        locadoraEntity.setPreco(20.0);
        locadoraEntity.setDiasLocacao(1);
        locadoraEntity.setNomeItem("VHS da xuxa");
        locadoraEntity.setDisponibilidade(true);
        return locadoraEntity;
    }

    private static LocadoraCreateDto getLocadoraCreateDto(){
        LocadoraCreateDto locadoraCreateDto = new LocadoraCreateDto();
        locadoraCreateDto.setNomePessoa("Eduardo");
        locadoraCreateDto.setPreco(20.0);
        locadoraCreateDto.setDiasLocacao(1);
        locadoraCreateDto.setNomeItem("VHS da xuxa");
        locadoraCreateDto.setDisponibilidade(true);
        return locadoraCreateDto;
    }
}

