package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.*;
import br.com.dbc.dbcmovies.entity.AvaliacaoEntity;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.LancamentosEntity;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.entity.pk.AvaliacaoPK;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.AvaliacaoRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvaliacaoServiceTest {

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ItemService itemService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(avaliacaoService, "objectMapper", objectMapper);
    }


//    @Test
//    public void deveTestarCreateAvaliacaoComSucesso() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        AvaliacaoCreateDto avaliacaoCreateDto = getAvaliacaoCreateDto();
//
//        AvaliacaoEntity avaliacaoEntity = getAvaliacaoEntity();
//
//        // avaliacaoRepository.save(avaliacaoEntity);
//        avaliacaoEntity.setNota(9.0);
//        avaliacaoEntity.setComentario("Muito Bom o filme.");
//        AvaliacaoPK avaliacaoPK = new AvaliacaoPK();
//        avaliacaoPK.setIdItem(1);
//        avaliacaoPK.setIdUsuario(1);
//        avaliacaoEntity.setAvaliacaoPK(avaliacaoPK);
//        when(avaliacaoRepository.save(any())).thenReturn(avaliacaoEntity);
//
//        // Ação (ACT)
//        AvaliacaoDto avaliacaoDto = avaliacaoService.create(avaliacaoCreateDto, 1);
//
//        // Verificação (ASSERT)
//        assertNotNull(avaliacaoDto);
//        assertNotNull(avaliacaoDto.getIdUsuario());
//        assertNotNull(avaliacaoDto.getUsuario());
//        assertEquals(9.0,avaliacaoDto.getNota());
//        assertEquals("Muito Bom o filme.", avaliacaoDto.getComentario());
//    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        // lancamentosRepository.findAll()
        List<AvaliacaoEntity> lista = new ArrayList<>();
        lista.add(getAvaliacaoEntity());
        when(avaliacaoRepository.findAll()).thenReturn(lista);

        // Ação (ACT)
        List<AvaliacaoDto> list = avaliacaoService.list();

        // Verificação (ASSERT)
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());
    }

    @Test
    public void deveTestarListByUsersComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        // lancamentosRepository.findByUsers()
        List<AvaliacaoEntity> lista = new ArrayList<>();
        lista.add(getAvaliacaoEntity());
        when(avaliacaoRepository.pegarUsuario(1)).thenReturn(lista);

        // Ação (ACT)
        List<AvaliacaoDto> list = avaliacaoService.listByUsers(1);

        // Verificação (ASSERT)
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());
    }

    @Test
    public void deveTestarFindByIdsAvaliacaoComSucesso() throws RegraDeNegocioException {
        // SETUP
        AvaliacaoEntity avaliacaoEntity = getAvaliacaoEntity();
        when(avaliacaoRepository.findByIdAvaliacao(1,1)).thenReturn(avaliacaoEntity);

        // ACT
        AvaliacaoEntity avaliacaoEntity1 = avaliacaoService.findByIdAvaliacao(1, 1);

        // ASSERT
        assertNotNull(avaliacaoEntity1);
        assertEquals(1, avaliacaoEntity1.getUsuario().getIdUsuario());
        assertEquals(1, avaliacaoEntity1.getItemEntretenimento().getIdItem());
    }
    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdsAvaliacaoComFalha() throws RegraDeNegocioException {
        // SETUP
        AvaliacaoEntity avaliacaoEntity = getAvaliacaoEntity();

        // ACT
        AvaliacaoEntity avaliacaoEntity1 = avaliacaoService.findByIdAvaliacao(5, 5);
    }

    @Test
    public void deveTestarGetByIds() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        Integer idUsuario = 1;
        Integer idItem = 1;
        AvaliacaoEntity avaliacaoEntity = getAvaliacaoEntity();
        when(avaliacaoRepository.findByIdAvaliacao(anyInt(), anyInt())).thenReturn(avaliacaoEntity);

        // Ação (ACT)
        AvaliacaoDto avaliacaoDto = avaliacaoService.getByIds(idUsuario, idItem);

        // Verificação (ASSERT)
        assertNotNull(avaliacaoDto);
        assertEquals(1, avaliacaoDto.getIdUsuario());
        assertEquals(1, avaliacaoDto.getIdItemEntretenimento());
    }

    @Test
    public void deveTestarVerificarItemAvalicadoComSucesso(){
        // SETUP


        // ACT


        // ASSERT
    }



//    @Test
//    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
//        // SETUP
//        Integer idItem = 1;
//        AvaliacaoCreateDto avaliacaoCreateDto = getAvaliacaoCreateDto();
//
//        // findById(id);
//        AvaliacaoEntity avaliacaoEntity = getAvaliacaoEntity();
//        avaliacaoCreateDto.setNota(9.0);
//        avaliacaoCreateDto.setComentario("Muito bom, excelente efeitos visuais.");
//        when(avaliacaoRepository.findById(any())).thenReturn(Optional.of(avaliacaoEntity));
//
//        // pessoaRepository.save(pessoaEntityRecuperada);
//        AvaliacaoEntity avaliacaoEntity1 = getAvaliacaoEntity();
//        when(avaliacaoRepository.save(any())).thenReturn(avaliacaoEntity1);
//
//        // ACT
//        AvaliacaoDto avaliacaoDto = avaliacaoService.update(avaliacaoCreateDto, 1);
//
//        // ASSERT
//        assertNotNull(avaliacaoDto);
//        assertNotEquals(9.0, avaliacaoDto.getNota());
//        assertNotEquals("Muito bom, excelente efeitos visuais.",avaliacaoDto.getComentario());
//    }

    private static AvaliacaoEntity getAvaliacaoEntity() {
        AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
        AvaliacaoPK avaliacaoPK = new AvaliacaoPK();
        avaliacaoPK.setIdItem(1);
        avaliacaoPK.setIdUsuario(1);
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome("Eduardo");
        usuarioEntity.setEmail("eduardo@dbccompany.com.br");
        usuarioEntity.setIdade(30);
        usuarioEntity.setAtivo(1);
        usuarioEntity.setSenha("1234");
        usuarioEntity.setIdUsuario(1);
        ItemEntretenimentoEntity itemEntretenimentoEntity = new ItemEntretenimentoEntity();
        itemEntretenimentoEntity.setIdItem(1);
        avaliacaoEntity.setNota(9.0);
        avaliacaoEntity.setComentario("Muito bom, excelente efeitos visuais.");
        avaliacaoEntity.setAvaliacaoPK(avaliacaoPK);
        avaliacaoEntity.setUsuario(usuarioEntity);
        avaliacaoEntity.setItemEntretenimento(itemEntretenimentoEntity);
        return avaliacaoEntity;
    }

    private static AvaliacaoCreateDto getAvaliacaoCreateDto() {
        AvaliacaoCreateDto avaliacaoCreateDto = new AvaliacaoCreateDto();
        avaliacaoCreateDto.setNota(9.0);
        avaliacaoCreateDto.setComentario("Muito bom, excelente efeitos visuais.");
        return avaliacaoCreateDto;
    }
}
