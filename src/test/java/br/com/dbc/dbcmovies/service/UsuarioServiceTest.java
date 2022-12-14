package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.CargoEntity;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CargoService cargoService;

    @Mock
    private TokenService tokenService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarListarComSucesso() {
        // Criar variaveis (SETUP)
        List<UsuarioEntity> lista = new ArrayList<>();
        lista.add(getUsuarioEntity());
        when(usuarioRepository.findAll()).thenReturn(lista);

        // A????o (ACT)
        List<UsuarioDto> usuarios = usuarioService.listar();

        // Verifica????o (ASSERT)
        assertNotNull(usuarios);
        assertTrue(usuarios.size() > 0);
        assertEquals(1, lista.size());
    }

    @Test
    public void deveTestarPegarComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // A????o (ACT)
        UsuarioDto usuarioDto = usuarioService.pegar(busca);

        // Verifica????o (ASSERT)
        assertNotNull(usuarioDto);
        assertEquals(1, usuarioDto.getIdUsuario());
    }

    @Test
    public void deveTestarFindByEmailComSucesso() {
        // Criar variaveis (SETUP)
        String email = "luiz@dbccompany.com.br";
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(getUsuarioEntity()));

        // A????o (ACT)
        Optional<UsuarioEntity> usuarioEntity = usuarioService.findByEmail(getUsuarioEntity().getEmail());

        // Verifica????o (ASSERT)
        assertNotNull(usuarioEntity);
        assertEquals(email, usuarioEntity.get().getEmail());
    }

    @Test
    public void deveTestarFindByEmailComErro() {
        // Criar variaveis (SETUP)
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // A????o (ACT)
        Optional<UsuarioEntity> usuarioEntity = usuarioService.findByEmail(getUsuarioEntity().getEmail());
    }

    @Test
    public void deveTestarCadastrarComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioCreateDto usuarioCreateDto = getUsuarioCreateDto();

        UsuarioEntity usuarioEntity = getUsuarioEntity();

        String senhaCriptografada = "j183nsur74bd83gr7";
        when(passwordEncoder.encode(anyString())).thenReturn(senhaCriptografada);

        when(cargoService.findById(anyInt())).thenReturn(getCargoEntity());

        usuarioEntity.setIdUsuario(1);
        when(usuarioRepository.save(any())).thenReturn(usuarioEntity);

        // A????o (ACT)
        UsuarioDto usuarioDto = usuarioService.cadastrar(usuarioCreateDto);

        // Verifica????o (ASSERT)
        assertNotNull(usuarioDto);
        assertNotNull(usuarioDto.getIdUsuario());
        assertEquals("luiz@dbccompany.com.br", usuarioDto.getEmail());
    }

    @Test
    public void deveTestarDesativarContaComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        Integer contaInativa = 0;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setAtivo(contaInativa);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // A????o (ACT)
        UsuarioDto usuarioDto = usuarioService.desativarConta(busca);

        // Verifica????o (ASSERT)
        assertNotNull(usuarioDto);
        assertEquals(1, usuarioDto.getIdUsuario());
        assertEquals(contaInativa, usuarioDto.getAtivo());
    }

    @Test
    public void deveTestarTornarContaAdminComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        CargoEntity cargo = getCargoEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setCargos(Set.of(cargo));

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        when(cargoService.findById(anyInt())).thenReturn(getCargoEntity());

        when(usuarioRepository.save(any())).thenReturn(usuarioEntity);

        // A????o (ACT)
        UsuarioDto usuarioDto = usuarioService.tornarContaAdmin(busca);

        // Verifica????o (ASSERT)
        assertNotNull(usuarioDto);
        assertEquals(1, usuarioDto.getIdUsuario());
        assertEquals(usuarioEntity.getCargos().stream()
                        .toList()
                        .get(0)
                        .getIdCargo(),
                getCargoEntity().getIdCargo());
    }

    @Test
    public void deveTestarListarContasInativasComSucesso() {
        // Criar variaveis (SETUP)
        Integer contaInativa = 0;
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setAtivo(contaInativa);
        List<UsuarioEntity> lista = new ArrayList<>();
        lista.add(usuarioEntity);
        when(usuarioRepository.findByAtivo(any())).thenReturn(lista);

        // A????o (ACT)
        List<UsuarioDto> usuarios = usuarioService.contasInativas();

        // Verifica????o (ASSERT)
        assertNotNull(usuarios);
        assertTrue(usuarios.size() > 0);
        assertEquals(1, lista.size());
        assertEquals(lista.get(0).getAtivo(), contaInativa);
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id= 10;
        UsuarioCreateDto usuarioCreateDto = getUsuarioCreateDto();

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setNome("Eduardo Sedrez");
        usuarioEntity.setIdUsuario(1);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        String senhaCriptografa = "j183nsur74bd83gr7";
        when(passwordEncoder.encode(anyString())).thenReturn(senhaCriptografa);

        UsuarioEntity usuario = getUsuarioEntity();
        when(usuarioRepository.save(any())).thenReturn(usuario);

        // A????o (ACT)
        UsuarioDto usuarioDto = usuarioService.editar(id, usuarioCreateDto);

        // Verifica????o (ASSERT)
        assertNotNull(usuarioDto);
        assertNotEquals("Eduardo Sedrez", usuarioDto.getNome());
        verify(emailService, times(1)).sendEmailUsuario(any(), any());
    }

    @Test
    public void deveTestarRemoverComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 10;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // A????o (ACT)
        usuarioService.remover(id);

        // Verifica????o (ASSERT)
        verify(usuarioRepository, times(1)).delete(any());

        verify(emailService, times(1)).sendEmailUsuario(any(), any());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // A????o (ACT)
        UsuarioEntity usuarioRecuperado = usuarioService.findById(busca);

        // Verifica????o (ASSERT)
        assertNotNull(usuarioRecuperado);
        assertEquals(1, usuarioRecuperado.getIdUsuario());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // A????o (ACT)
        usuarioService.findById(busca);
    }

    @Test
    public void deveTestarRecuperarSenhaComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        String email = "luiz@dbccompany.com.br";
        Integer roleRecuperacao = 3;
        String token = "$d38fh94bg8e8547gbv9";

        CargoEntity cargoRecuperacao = getCargoEntity();
        cargoRecuperacao.setIdCargo(roleRecuperacao);

        UsuarioEntity usuario = getUsuarioEntity();

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        when(cargoService.findById(any())).thenReturn(cargoRecuperacao);

        when(tokenService.getToken(any(), anyBoolean())).thenReturn(token);

        when(usuarioRepository.save(any())).thenReturn(usuario);

        // A????o (ACT)
        usuarioService.recuperarSenha(email);

        // Verifica????o (ASSERT)
        verify(emailService, times(1)).sendEmailRecuperacaoSenha(any(), any(), any());
    }


    @Test
    public void deveTestargetIdLoggedUser(){

        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        // A????o (ACT)
        Integer idLoggedUser = usuarioService.getIdLoggedUser();


        // Verifica????o (ASSERT)
        assertEquals(1,idLoggedUser);
    }

    @Test
    public void deveTestargetLoggedUser() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // A????o (ACT)
        UsuarioDto loggedUser = usuarioService.getLoggedUser();
        loggedUser.setIdUsuario(1);

        // Verifica????o (ASSERT)
        assertEquals(1,loggedUser.getIdUsuario());
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

    private static UsuarioCreateDto getUsuarioCreateDto() {
        UsuarioCreateDto usuarioCreateDto = new UsuarioCreateDto();
        usuarioCreateDto.setNome("Alison Silva");
        usuarioCreateDto.setIdade(24);
        usuarioCreateDto.setEmail("alison@dbccompany.com.br");
        usuarioCreateDto.setSenha("123");
        return usuarioCreateDto;
    }

    private static CargoEntity getCargoEntity() {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setNome("ROLE_ADMIN");
        return cargoEntity;
    }
}
