package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.AvaliacaoDto;
import br.com.dbc.dbcmovies.entity.AvaliacaoEntity;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
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
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvaliacaoServiceTest {

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

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

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
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
    public void deveTestarListByUsersComSucesso() {
        // Criar variaveis (SETUP)
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

        when(usuarioService.findById(anyInt())).thenReturn(getUsuarioEntity());

        when(itemService.findById(anyInt())).thenReturn(getItemEntretenimento());

        when(avaliacaoRepository.findByIdAvaliacao(anyInt(), anyInt())).thenReturn(avaliacaoEntity);

        // Ação (ACT)
        AvaliacaoDto avaliacaoDto = avaliacaoService.getByIds(idUsuario, idItem);

        // Verificação (ASSERT)
        assertNotNull(avaliacaoDto);
        assertEquals(1, avaliacaoDto.getIdUsuario());
        assertEquals(1, avaliacaoDto.getIdItemEntretenimento());
    }

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

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setNome("Luiz Martins");
        usuarioEntity.setIdade(25);
        usuarioEntity.setEmail("luiz@dbccompany.com.br");
        usuarioEntity.setSenha("123");
        usuarioEntity.setAtivo(1);
        usuarioEntity.setCargos(new HashSet<>());
        usuarioEntity.setItemEntretenimentos(new HashSet<>());
        return usuarioEntity;
    }

    public static ItemEntretenimentoEntity getItemEntretenimento() {
        ItemEntretenimentoEntity item = new ItemEntretenimentoEntity();
        item.setIdItem(1);
        item.setNome("Homem Aranha");
        item.setTipo("Filme");
        item.setGenero("ação");
        item.setSinopse("O homem que sobe pelas paredes e joga teia");
        item.setAnoLancamento("02/02/2002");
        item.setClassificacao(16);
        item.setPlataforma("Netflix");
        item.setDuracao("155");
        item.setUsuarios(new HashSet<>());
        return item;
    }
}
