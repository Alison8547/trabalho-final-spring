package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.FilmeCreateDto;
import br.com.dbc.dbcmovies.dto.ItemEntretenimentoDto;
import br.com.dbc.dbcmovies.dto.PageDTO;
import br.com.dbc.dbcmovies.dto.SerieCreateDto;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.ItemRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(itemService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateFilme() {

        // Criar variaveis (SETUP)
        FilmeCreateDto createFilmeDto = getFilmeCreateDto();
        ItemEntretenimentoEntity itemEntretenimentoEntityFilme = getItemEntretenimentoEntityFilme();
        when(itemRepository.save(any())).thenReturn(itemEntretenimentoEntityFilme);


        // Ação (ACT)
        ItemEntretenimentoDto filmeDtoRetorno = itemService.createFilme(createFilmeDto);


        // Verificação (ASSERT)
        assertNotNull(filmeDtoRetorno);
        assertNotNull(filmeDtoRetorno.getIdItem());
        assertEquals("Hulk",filmeDtoRetorno.getNome());
        assertNull(filmeDtoRetorno.getEpisodios());
        assertNull(filmeDtoRetorno.getTemporadas());
    }

    @Test
    public void deveTestarCreateSerie() {

        // Criar variaveis (SETUP)
        SerieCreateDto serieCreateDto = getSerieCreateDto();
        ItemEntretenimentoEntity itemEntretenimentoEntitySerie = getItemEntretenimentoEntitySerie();
        when(itemRepository.save(any())).thenReturn(itemEntretenimentoEntitySerie);

        // Ação (ACT)
        ItemEntretenimentoDto serieDtoRetorno = itemService.createSerie(serieCreateDto);


        // Verificação (ASSERT)
        assertNotNull(serieDtoRetorno);
        assertNotNull(serieDtoRetorno.getIdItem());
        assertEquals("A Maldição da Mansão Bly",serieDtoRetorno.getNome());
        assertNull(serieDtoRetorno.getDuracao());

    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        List<ItemEntretenimentoEntity> itemEntretenimentoEntityList = new ArrayList<>();
        itemEntretenimentoEntityList.add(getItemEntretenimentoEntityFilme());
        when(itemRepository.findAll()).thenReturn(itemEntretenimentoEntityList);

        // Ação (ACT)
        List<ItemEntretenimentoDto> list = itemService.list();

        // Verificação (ASSERT)
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1,list.size());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        Integer busca = 1;
        ItemEntretenimentoEntity itemEntretenimentoEntityFilme = getItemEntretenimentoEntityFilme();
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(itemEntretenimentoEntityFilme));

        // Ação (ACT)
        ItemEntretenimentoEntity itemEntretenimentoEntity = itemService.findById(busca);

        // Verificação (ASSERT)
        assertNotNull(itemEntretenimentoEntity);
        assertEquals(1, itemEntretenimentoEntity.getIdItem());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());


        // Ação (ACT)
        itemService.findById(busca);
    }

    @Test
    public void deveTestarGetItemComSucesso() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        Integer busca = 1;
        ItemEntretenimentoEntity itemEntretenimentoEntityFilme = getItemEntretenimentoEntityFilme();
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(itemEntretenimentoEntityFilme));

        // Ação (ACT)
        ItemEntretenimentoDto itemDto = itemService.getItem(busca);

        // Verificação (ASSERT)
        assertNotNull(itemDto);
        assertEquals(1, itemDto.getIdItem());
    }


    @Test
    public void deveTestarUpdateComSucessoFilme() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        Integer id = 12;
        FilmeCreateDto filmeCreateDto = getFilmeCreateDto();
        ItemEntretenimentoEntity itemEntretenimentoEntityFilme1 = getItemEntretenimentoEntityFilme();
        itemEntretenimentoEntityFilme1.setNome("Velozes e Furiosos");
        itemEntretenimentoEntityFilme1.setIdItem(id);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(itemEntretenimentoEntityFilme1));

        ItemEntretenimentoEntity itemEntretenimentoEntityFilme = getItemEntretenimentoEntityFilme();
        when(itemRepository.save(any())).thenReturn(itemEntretenimentoEntityFilme);

        // Ação (ACT)
        ItemEntretenimentoDto itemEntretenimentoDto = itemService.updateFilme(id, filmeCreateDto);

        // Verificação (ASSERT)
        assertNotNull(itemEntretenimentoDto);
        assertNotEquals("Velozes e Furiosos",itemEntretenimentoDto.getNome());
    }

    @Test
    public void deveTestarUpdateComSucessoSerie() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        Integer id = 12;
        SerieCreateDto serieCreateDto = getSerieCreateDto();
        ItemEntretenimentoEntity itemEntretenimentoEntitySerie1 = getItemEntretenimentoEntitySerie();
        itemEntretenimentoEntitySerie1.setNome("Stranger Things");
        itemEntretenimentoEntitySerie1.setIdItem(id);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(itemEntretenimentoEntitySerie1));

        ItemEntretenimentoEntity itemEntretenimentoEntitySerie = getItemEntretenimentoEntitySerie();
        when(itemRepository.save(any())).thenReturn(itemEntretenimentoEntitySerie);

        // Ação (ACT)
        ItemEntretenimentoDto itemEntretenimentoDto = itemService.updateSerie(id, serieCreateDto);

        // Verificação (ASSERT)
        assertNotNull(itemEntretenimentoDto);
        assertNotEquals("Stranger Things",itemEntretenimentoDto.getNome());
    }


    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        Integer id = 1;

        //pessoaRepository.findById(id)
        ItemEntretenimentoEntity itemEntretenimentoEntityFilme = getItemEntretenimentoEntityFilme();
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(itemEntretenimentoEntityFilme));

        // Ação (ACT)
        itemService.delete(id);

        // Verificação (ASSERT)
        verify(itemRepository, times(1)).delete(any());

    }

    @Test
    public void deveTestarFilterComSucesso(){

        // Criar variaveis (SETUP)
        String tipo = "Filme";
        String genero = "Ação";
        Integer classificacao = 16;
        List<ItemEntretenimentoEntity> itemEntretenimentoEntityList = new ArrayList<>();
        ItemEntretenimentoEntity itemEntretenimentoEntityFilme = getItemEntretenimentoEntityFilme();
        itemEntretenimentoEntityList.add(itemEntretenimentoEntityFilme);
        when(itemRepository.filtrar(any(),any(),anyInt())).thenReturn(itemEntretenimentoEntityList);

        // Ação (ACT)
        List<ItemEntretenimentoDto> itemEntretenimentoDtoList = itemService.filter(tipo, genero, classificacao);

        // Verificação (ASSERT)
        assertNotNull(itemEntretenimentoDtoList);
        assertEquals("Hulk",itemEntretenimentoEntityFilme.getNome());
        assertEquals("Ação",itemEntretenimentoEntityFilme.getGenero());
        assertEquals(16,itemEntretenimentoEntityFilme.getClassificacao());

    }


    @Test
    public void deveTestarListPaginadoComSucesso(){
        // SETUP
        Integer pagina = 5;
        Integer quantidade = 3;

        ItemEntretenimentoEntity itemEntretenimentoEntityFilme = getItemEntretenimentoEntityFilme();
        Page<ItemEntretenimentoEntity> paginaMock = new PageImpl<>(List.of(itemEntretenimentoEntityFilme));
        when(itemRepository.findAll(any(Pageable.class))).thenReturn(paginaMock);

        // ACT
        PageDTO<ItemEntretenimentoDto> itemEntretenimentoDtoPageDTO = itemService.listaItemEntretenimentoPaginado(pagina, quantidade);

        // ASSERT
        assertNotNull(itemEntretenimentoDtoPageDTO);
        assertEquals(1, itemEntretenimentoDtoPageDTO.getQuantidadePaginas());
        assertEquals(1, itemEntretenimentoDtoPageDTO.getTotalElementos());
    }





    private static ItemEntretenimentoEntity getItemEntretenimentoEntityFilme() {
        ItemEntretenimentoEntity itemEntretenimentoEntity = new ItemEntretenimentoEntity();
        itemEntretenimentoEntity.setIdItem(1);
        itemEntretenimentoEntity.setGenero("Ação");
        itemEntretenimentoEntity.setTipo("Filme");
        itemEntretenimentoEntity.setNome("Hulk");
        itemEntretenimentoEntity.setSinopse("Um monstro verde");
        itemEntretenimentoEntity.setClassificacao(16);
        itemEntretenimentoEntity.setAnoLancamento("2011");
        itemEntretenimentoEntity.setPlataforma("Cinema");
        itemEntretenimentoEntity.setDuracao("160");

        return itemEntretenimentoEntity;
    }

    private static ItemEntretenimentoEntity getItemEntretenimentoEntitySerie() {
        ItemEntretenimentoEntity itemEntretenimentoEntity = new ItemEntretenimentoEntity();
        itemEntretenimentoEntity.setIdItem(1);
        itemEntretenimentoEntity.setGenero("Terror");
        itemEntretenimentoEntity.setTipo("Serie");
        itemEntretenimentoEntity.setNome("A Maldição da Mansão Bly");
        itemEntretenimentoEntity.setSinopse("Uma babá norte-americana chega a Bly Manor e começa a ver aparições em uma propriedade inglesa.");
        itemEntretenimentoEntity.setClassificacao(18);
        itemEntretenimentoEntity.setAnoLancamento("2018");
        itemEntretenimentoEntity.setPlataforma("Netflix");
        itemEntretenimentoEntity.setEpisodios(12);
        itemEntretenimentoEntity.setTemporadas(2);

        return itemEntretenimentoEntity;
    }

    private static FilmeCreateDto getFilmeCreateDto() {
        FilmeCreateDto filmeCreateDto = new FilmeCreateDto();
        filmeCreateDto.setGenero("Acão");
        filmeCreateDto.setTipo("Filme");
        filmeCreateDto.setNome("Hulk");
        filmeCreateDto.setSinopse("Um monstro verde");
        filmeCreateDto.setClassificacao(16);
        filmeCreateDto.setAnoLancamento("2011");
        filmeCreateDto.setPlataforma("Cinema");
        filmeCreateDto.setDuracao("160");

        return filmeCreateDto;
    }

    private static SerieCreateDto getSerieCreateDto() {
        SerieCreateDto serieCreateDto = new SerieCreateDto();
        serieCreateDto.setGenero("Terror");
        serieCreateDto.setTipo("Serie");
        serieCreateDto.setNome("A Maldição da Mansão Bly");
        serieCreateDto.setSinopse("Uma babá norte-americana chega a Bly Manor e começa a ver aparições em uma propriedade inglesa.");
        serieCreateDto.setClassificacao(18);
        serieCreateDto.setAnoLancamento("2018");
        serieCreateDto.setPlataforma("Netflix");
        serieCreateDto.setEpisodios(12);
        serieCreateDto.setTemporadas(2);

        return serieCreateDto;
    }
}
