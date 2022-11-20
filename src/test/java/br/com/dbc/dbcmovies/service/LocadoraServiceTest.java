package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.LocadoraCreateDto;
import br.com.dbc.dbcmovies.dto.LocadoraDto;
import br.com.dbc.dbcmovies.dto.PrecoDto;
import br.com.dbc.dbcmovies.entity.LocadoraEntity;
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
    public void deveTestarAlugarComSucesso() {
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
        assertNotEquals(40, locadoraDto.getPreco());
    }

    @Test
    public void deveTestarListQuantidadePreco() {

        // SETUP
        List<PrecoDto> lista = new ArrayList<>();
        PrecoDto precoDto = new PrecoDto();
        precoDto.setPreco(12.0);
        precoDto.setQuantidade(1);
        lista.add(precoDto);
        when(locadoraRepository.listQuantidadePreco()).thenReturn(lista);

        // ACT
        List<PrecoDto> list = locadoraService.quantidadePrecos();

        // ASSERT
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, list.size());

    }

    @Test
    public void deveTestarFindPreco() {

        // SETUP
        Double preco = 10.4;
        List<LocadoraDto> lista = new ArrayList<>();
        LocadoraDto locadoraDto = new LocadoraDto();
        locadoraDto.setNomePessoa("Alison");
        locadoraDto.setPreco(preco);
        locadoraDto.setIdLocadora("1");
        locadoraDto.setNomeItem("Hulk");
        locadoraDto.setDiasLocacao(4);
        lista.add(locadoraDto);
        when(locadoraRepository.findByPreco(any())).thenReturn(lista);
        // ACT
        List<LocadoraDto> list = locadoraService.findByPreco(preco);

        // ASSERT
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, list.size());

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

    private static LocadoraCreateDto getLocadoraCreateDto() {
        LocadoraCreateDto locadoraCreateDto = new LocadoraCreateDto();
        locadoraCreateDto.setNomePessoa("Eduardo");
        locadoraCreateDto.setPreco(20.0);
        locadoraCreateDto.setDiasLocacao(1);
        locadoraCreateDto.setNomeItem("VHS da xuxa");
        locadoraCreateDto.setDisponibilidade(true);
        return locadoraCreateDto;
    }
}

