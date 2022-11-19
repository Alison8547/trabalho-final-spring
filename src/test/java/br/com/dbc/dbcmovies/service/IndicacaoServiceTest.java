package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.*;
import br.com.dbc.dbcmovies.entity.IndicacaoEntity;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.entity.pk.IndicacaoPK;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.IndicacaoRepository;
import br.com.dbc.dbcmovies.repository.UsuarioRepository;
import br.com.dbc.dbcmovies.security.TokenService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class IndicacaoServiceTest {

    @InjectMocks
    private IndicacaoService indicacaoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private IndicacaoRepository indicacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;


    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(indicacaoService, "objectMapper", objectMapper);
    }


//
//    @Test
//    public void deveTestarIncluirIndicacaoComSucesso() throws RegraDeNegocioException {
//
//        // Criar variaveis (SETUP)
//        IndicacaoCreateDto indicacao = new IndicacaoCreateDto();
//        indicacao.setItemNome("Death note");
//        UsernamePasswordAuthenticationToken dto
//                       = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
//                SecurityContextHolder.getContext().setAuthentication(dto);
//
//        Integer idLoggedUser = usuarioService.getIdLoggedUser();
//
//        IndicacaoPK indicacaoPK = new IndicacaoPK(idLoggedUser, indicacao.getItemNome());
//
//        IndicacaoEntity indicacaoEntity = new IndicacaoEntity();
//        indicacaoEntity.setIndicacaoPK(indicacaoPK);
//        indicacaoEntity.setUsuario(usuarioService.findById(idLoggedUser));
//
//
//        UsuarioEntity usuarioEntity = getUsuarioEntity();
//        usuarioEntity.setIdUsuario(1);
//        when(usuarioService.findById(anyInt())).thenReturn(usuarioEntity);
//        when(indicacaoRepository.save(any())).thenReturn(indicacaoEntity);
//
//
//
//
//        // Ação (ACT)
//        IndicacaoDto indicacaoDto = indicacaoService.incluirIndicacao(indicacao);
//
//
//
//
//        // Verificação (ASSERT)
//       // assertNotNull(indicacaoDto);
//        assertEquals(1,idLoggedUser);
//    }

    @Test
    public void deveTestarListPaginadoComSucesso() {
        // SETUP
        Integer pagina = 5;
        Integer quantidade = 3;
        IndicacaoCreateDto indicacao = new IndicacaoCreateDto();
        indicacao.setItemNome("Death note");
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setAtivo(1);

        IndicacaoPK indicacaoPK = new IndicacaoPK(1, indicacao.getItemNome());
        IndicacaoEntity indicacaoEntity = new IndicacaoEntity();
        indicacaoEntity.setIndicacaoPK(indicacaoPK);
        indicacaoEntity.setUsuario(usuarioEntity);
        Page<IndicacaoEntity> paginaMock = new PageImpl<>(List.of(indicacaoEntity));
        when(indicacaoRepository.findAll(any(Pageable.class))).thenReturn(paginaMock);

        // ACT
        PageDTO<IndicacaoDto> indicacaoDtoPageDTO = indicacaoService.listPessoaIndicacaoPaginada(pagina, quantidade);

        // ASSERT
        assertNotNull(indicacaoDtoPageDTO);
        assertEquals(1, indicacaoDtoPageDTO.getQuantidadePaginas());
        assertEquals(1, indicacaoDtoPageDTO.getTotalElementos());
    }

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome("Luiz Martins");
        usuarioEntity.setIdade(25);
        usuarioEntity.setEmail("luiz@dbccompany.com.br");
        usuarioEntity.setSenha("123");
        usuarioEntity.setAtivo(1);
        usuarioEntity.setCargos(new HashSet<>());
        return usuarioEntity;
    }

    private static UsuarioDto getUsuarioDto() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNome("Eduardo Sedrez");
        usuarioDto.setIdade(24);
        usuarioDto.setEmail("eduardo@dbccompany.com.br");
        return usuarioDto;
    }

}
