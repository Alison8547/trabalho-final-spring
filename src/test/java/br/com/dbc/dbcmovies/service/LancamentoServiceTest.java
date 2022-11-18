package br.com.dbc.dbcmovies.service;


import br.com.dbc.dbcmovies.dto.LancamentoCreateDto;
import br.com.dbc.dbcmovies.dto.LancamentoDto;
import br.com.dbc.dbcmovies.entity.LancamentosEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.LancamentosRepository;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LancamentoServiceTest {

    @InjectMocks
    private LancamentosService lancamentosService;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private LancamentosRepository lancamentosRepository;


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(lancamentosService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateLancamentoComSucesso(){
        // Criar variaveis (SETUP)
        LancamentoCreateDto lancamentoCreateDto = getLancamentoCreateDto();

        LancamentosEntity lancamentosEntity = getLancamentoEntity();

        // lancamentosRepository.save(lancamentosEntity)
        lancamentosEntity.setIdLancamento("6376a0fae9bf89fa58bc6ebe");
        when(lancamentosRepository.save(any())).thenReturn(lancamentosEntity);

        // Ação (ACT)
        LancamentoDto lancamentoDtoRetorno = lancamentosService.createLancamento(lancamentoCreateDto);

        // Verificação (ASSERT)
        assertNotNull(lancamentoDtoRetorno);
        assertNotNull(lancamentoDtoRetorno.getIdLancamento());
        assertEquals("WakandaForever", lancamentoDtoRetorno.getNome());
        assertEquals("filme",lancamentoDtoRetorno.getTipo());
        assertEquals("ação",lancamentoDtoRetorno.getGenero());
        assertEquals("Uma civilização luta por sua sobrevivencia",lancamentoDtoRetorno.getSinopse());
        assertEquals("2022",lancamentoDtoRetorno.getAnoLancamento());
        assertEquals(16,lancamentoDtoRetorno.getClassificacao());
        assertEquals("Cinema proximo",lancamentoDtoRetorno.getPlataforma());
        assertEquals("160",lancamentoDtoRetorno.getDuracao());
        assertEquals("09/11/2022",lancamentoDtoRetorno.getDataLancamento());
    }


    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        // lancamentosRepository.findAll()
        List<LancamentosEntity> lista = new ArrayList<>();
        lista.add(getLancamentoEntity());
        when(lancamentosRepository.findAll()).thenReturn(lista);

        // Ação (ACT)
        List<LancamentoDto> list = lancamentosService.list();

        // Verificação (ASSERT)
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        String busca = "6376a0fae9bf89fa58bc6ebe";
        when(lancamentosRepository.findById(any())).thenReturn(Optional.empty());

        // Ação (ACT)
        lancamentosService.findById(busca);
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // SETUP
        String id= "6376a0fae9bf89fa58bc6ebe";
        LancamentoCreateDto lancamentoCreateDto = getLancamentoCreateDto();

        // findById(id);
        LancamentosEntity lancamentosEntity = getLancamentoEntity();
        lancamentosEntity.setIdLancamento("1597532486258741369");
        lancamentosEntity.setNome("WakandaForever");
        lancamentosEntity.setTipo("filme");
        lancamentosEntity.setGenero("ação");
        lancamentosEntity.setSinopse("Uma civilização luta por sua sobrevivencia");
        lancamentosEntity.setAnoLancamento("2022");
        lancamentosEntity.setClassificacao(16);
        lancamentosEntity.setPlataforma("Cinema proximo");
        lancamentosEntity.setDuracao("160");
        lancamentosEntity.setDataLancamento("09/11/2022");
        when(lancamentosRepository.findById(any())).thenReturn(Optional.of(lancamentosEntity));

        // pessoaRepository.save(pessoaEntityRecuperada);
        LancamentosEntity lancamentoEntity = getLancamentoEntity();
        when(lancamentosRepository.save(any())).thenReturn(lancamentoEntity);

        // ACT
        LancamentoDto lancamentoDto = lancamentosService.updateLancamento(id, lancamentoCreateDto);

        // ASSERT
        assertNotNull(lancamentoDto);
        assertNotEquals("Homem Formiga e a Vespa", lancamentoDto.getNome());
        assertNotEquals("filmezinho",lancamentoDto.getTipo());
        assertNotEquals("comedia",lancamentoDto.getGenero());
        assertNotEquals("Duas criaturas pequenas fazendo bagunça",lancamentoDto.getSinopse());
        assertNotEquals("2023",lancamentoDto.getAnoLancamento());
        assertNotEquals(14,lancamentoDto.getClassificacao());
        assertNotEquals("Cinema mais proximo",lancamentoDto.getPlataforma());
        assertNotEquals("128",lancamentoDto.getDuracao());
        assertNotEquals("13/02/2022",lancamentoDto.getDataLancamento());
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        String id= "6376a0fae9bf89fa58bc6ebe";

        //pessoaRepository.findById(id)
        LancamentosEntity lancamentosEntity = getLancamentoEntity();
        lancamentosEntity.setIdLancamento("1597532486258741369");
        lancamentosEntity.setNome("WakandaForever");
        lancamentosEntity.setTipo("filme");
        lancamentosEntity.setGenero("ação");
        lancamentosEntity.setSinopse("Uma civilização luta por sua sobrevivencia");
        lancamentosEntity.setAnoLancamento("2022");
        lancamentosEntity.setClassificacao(16);
        lancamentosEntity.setPlataforma("Cinema proximo");
        lancamentosEntity.setDuracao("160");
        lancamentosEntity.setDataLancamento("09/11/2022");
        when(lancamentosRepository.findById(any())).thenReturn(Optional.of(lancamentosEntity));

        // Ação (ACT)
        lancamentosService.delete(id);

        // Verificação (ASSERT)
        // verificar se chamou pelo menos 1 vez o metodo pessoaRepository.delete(pessoaEntityRecuperada);
        verify(lancamentosRepository, times(1)).delete(any());
    }

    private static LancamentosEntity getLancamentoEntity() {
        LancamentosEntity lancamentosEntity = new LancamentosEntity();
        lancamentosEntity.setNome("WakandaForever");
        lancamentosEntity.setTipo("filme");
        lancamentosEntity.setGenero("ação");
        lancamentosEntity.setSinopse("Uma civilização luta por sua sobrevivencia");
        lancamentosEntity.setAnoLancamento("2022");
        lancamentosEntity.setClassificacao(16);
        lancamentosEntity.setPlataforma("Cinema proximo");
        lancamentosEntity.setDuracao("160");
        lancamentosEntity.setDataLancamento("09/11/2022");
        return lancamentosEntity;
    }

    private static LancamentoCreateDto getLancamentoCreateDto() {
        LancamentoCreateDto lancamentoCreateDto = new LancamentoCreateDto();
        lancamentoCreateDto.setNome("WakandaForever");
        lancamentoCreateDto.setTipo("filme");
        lancamentoCreateDto.setGenero("ação");
        lancamentoCreateDto.setSinopse("Uma civilização luta por sua sobrevivencia");
        lancamentoCreateDto.setAnoLancamento("2022");
        lancamentoCreateDto.setClassificacao(16);
        lancamentoCreateDto.setPlataforma("Cinema proximo");
        lancamentoCreateDto.setDuracao("160");
        lancamentoCreateDto.setDataLancamento("09/11/2022");
        return lancamentoCreateDto;
    }

}
