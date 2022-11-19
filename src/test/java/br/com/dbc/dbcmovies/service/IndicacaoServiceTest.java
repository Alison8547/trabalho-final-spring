package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.IndicacaoCreateDto;
import br.com.dbc.dbcmovies.dto.IndicacaoDto;
import br.com.dbc.dbcmovies.dto.PageDTO;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.IndicacaoEntity;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.entity.pk.IndicacaoPK;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.IndicacaoRepository;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class IndicacaoServiceTest {

    @InjectMocks
    private IndicacaoService indicacaoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private IndicacaoRepository indicacaoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(indicacaoService, "objectMapper", objectMapper);
    }


    @Test
    public void deveTestarIncluirIndicacaoComSucesso() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);
        IndicacaoCreateDto indicacaoCreateDto = new IndicacaoCreateDto();
        indicacaoCreateDto.setItemNome("Hitmam");
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        IndicacaoPK indicacaoPK = new IndicacaoPK(1, indicacaoCreateDto.getItemNome());
        IndicacaoEntity indicacaoEntity = objectMapper.convertValue(indicacaoCreateDto, IndicacaoEntity.class);
        indicacaoEntity.setIndicacaoPK(indicacaoPK);
        indicacaoEntity.setUsuario(usuarioEntity);

        when(usuarioService.getLoggedUser()).thenReturn(getUsuarioDto());
        when(indicacaoRepository.save(any())).thenReturn(indicacaoEntity);

        // Ação (ACT)
        IndicacaoDto indicacaoDto = indicacaoService.incluirIndicacao(indicacaoCreateDto);
        usuarioService.getLoggedUser().getIdUsuario();

        // Verificação (ASSERT)
        assertNotNull(indicacaoDto);
        assertEquals("Hitmam", indicacaoDto.getItemNome());

    }

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
        usuarioEntity.setIdUsuario(1);
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
        usuarioDto.setIdUsuario(1);
        usuarioDto.setNome("Eduardo Sedrez");
        usuarioDto.setIdade(24);
        usuarioDto.setEmail("eduardo@dbccompany.com.br");
        return usuarioDto;
    }

}
