package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.ItemEntretenimentoDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.AssistidosRepository;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssistidosServiceTest {

    @InjectMocks
    private AssistidosService assistidosService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AssistidosRepository assistidosRepository;

    @Mock
    private UsuarioService usuarioService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(assistidosService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarListarAssistidosComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)

        UsernamePasswordAuthenticationToken dto
                = new UsernamePasswordAuthenticationToken(getUsuarioEntity(), null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        List<ItemEntretenimentoEntity> lista = new ArrayList<>();
        lista.add(getItemEntretenimento());

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setItemEntretenimentos(Set.of(getItemEntretenimento()));

        when(usuarioService.getLoggedUser()).thenReturn((getUsuarioDto()));

        when(usuarioService.findById(anyInt())).thenReturn(usuarioEntity);

        // Ação (ACT)
        List<ItemEntretenimentoDto> itens = assistidosService.listarAssistidos();

        // Verificação (ASSERT)
        assertNotNull(itens);
        assertTrue(itens.size() > 0);
        assertEquals(1, lista.size());
    }

    @Test
    public void deveTestarVerificarItemAssistidoComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        ItemEntretenimentoEntity itemEntity = getItemEntretenimento();
        ItemEntretenimentoEntity itemEntity2 = getItemEntretenimento();
        itemEntity2.setIdItem(5);
        usuarioEntity.setItemEntretenimentos(Set.of(itemEntity2));

        // Ação (ACT)
        boolean retorno = assistidosService.verificarItemAssistido(usuarioEntity, itemEntity.getIdItem());

        // Verificação (ASSERT)
        assertFalse(retorno);
    }

    @Test
    public void deveTestarVerificarItemNaTabelaComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        ItemEntretenimentoEntity itemEntity = getItemEntretenimento();
        usuarioEntity.setItemEntretenimentos(Set.of(itemEntity));

        // Ação (ACT)
        boolean retorno = assistidosService.verificarItemNaTabela(itemEntity.getIdItem(), usuarioEntity);

        // Verificação (ASSERT)
        assertTrue(retorno);
    }

    @Test
    public void deveTestarVerificarUsuarioNaTabelaComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        ItemEntretenimentoEntity itemEntity = getItemEntretenimento();
        usuarioEntity.setItemEntretenimentos(Set.of(itemEntity));

        when(assistidosRepository.verificarUsuarioNaTabela(usuarioEntity.getIdUsuario())).thenReturn(getUsuarioEntity().getIdUsuario());

        // Ação (ACT)
        boolean retorno = assistidosService.verificarUsuarioNaTabela(usuarioEntity.getIdUsuario());

        // Verificação (ASSERT)
        assertTrue(retorno);
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

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(2);
        usuarioEntity.setNome("Luiz Martins");
        usuarioEntity.setIdade(25);
        usuarioEntity.setEmail("luiz@dbccompany.com.br");
        usuarioEntity.setSenha("123");
        usuarioEntity.setAtivo(1);
        usuarioEntity.setCargos(new HashSet<>());
        usuarioEntity.setItemEntretenimentos(new HashSet<>());
        return usuarioEntity;
    }

    private static UsuarioDto getUsuarioDto() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setIdUsuario(2);
        usuarioDto.setNome("Luiz Martins");
        usuarioDto.setIdade(25);
        usuarioDto.setEmail("luiz@dbccompany.com.br");
        return usuarioDto;
    }
}
