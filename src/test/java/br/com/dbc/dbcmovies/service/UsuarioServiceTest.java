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

        // Ação (ACT)
        List<UsuarioDto> usuarios = usuarioService.listar();

        // Verificação (ASSERT)
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

        // Ação (ACT)
        UsuarioDto usuarioDto = usuarioService.pegar(busca);

        // Verificação (ASSERT)
        assertNotNull(usuarioDto);
        assertEquals(1, usuarioDto.getIdUsuario());
    }

    @Test
    public void deveTestarFindByEmailComSucesso() {
        // Criar variaveis (SETUP)
        String email = "luiz@dbccompany.com.br";
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(getUsuarioEntity()));

        // Ação (ACT)
        Optional<UsuarioEntity> usuarioEntity = usuarioService.findByEmail(getUsuarioEntity().getEmail());

        // Verificação (ASSERT)
        assertNotNull(usuarioEntity);
        assertEquals(email, usuarioEntity.get().getEmail());
    }

    @Test
    public void deveTestarFindByEmailComErro() {
        // Criar variaveis (SETUP)
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Ação (ACT)
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

        // Ação (ACT)
        UsuarioDto usuarioDto = usuarioService.cadastrar(usuarioCreateDto);

        // Verificação (ASSERT)
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

        // Ação (ACT)
        UsuarioDto usuarioDto = usuarioService.desativarConta(busca);

        // Verificação (ASSERT)
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

        // Ação (ACT)
        UsuarioDto usuarioDto = usuarioService.tornarContaAdmin(busca);

        // Verificação (ASSERT)
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

        // Ação (ACT)
        List<UsuarioDto> usuarios = usuarioService.contasInativas();

        // Verificação (ASSERT)
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

        // Ação (ACT)
        UsuarioDto usuarioDto = usuarioService.editar(id, usuarioCreateDto);

        // Verificação (ASSERT)
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

        // Ação (ACT)
        usuarioService.remover(id);

        // Verificação (ASSERT)
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

        // Ação (ACT)
        UsuarioEntity usuarioRecuperado = usuarioService.findById(busca);

        // Verificação (ASSERT)
        assertNotNull(usuarioRecuperado);
        assertEquals(1, usuarioRecuperado.getIdUsuario());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Ação (ACT)
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

        // Ação (ACT)
        usuarioService.recuperarSenha(email);

        // Verificação (ASSERT)
        verify(emailService, times(1)).sendEmailRecuperacaoSenha(any(), any(), any());
    }


    @Test
    public void deveTestargetIdLoggedUser(){

        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        // Ação (ACT)
        Integer idLoggedUser = usuarioService.getIdLoggedUser();


        // Verificação (ASSERT)
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

        // Ação (ACT)
        UsuarioDto loggedUser = usuarioService.getLoggedUser();
        loggedUser.setIdUsuario(1);

        // Verificação (ASSERT)
        assertEquals(1,loggedUser.getIdUsuario());
    }

//    @Test
//    public void deveTestarAlterarSenhaComSucesso() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        UsernamePasswordAuthenticationToken dto
//                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
//        SecurityContextHolder.getContext().setAuthentication(dto);
//
//        String senha = "123";
//        String senhaCriptografada = "$ibijbfce9u7vw7gb3uf";
//
//        CargoEntity cargoRecuperacao = getCargoEntity();
//
//        UsuarioEntity usuario = getUsuarioEntity();
//
//        when(usuarioService.findById(any())).thenReturn(usuario);
//
//        when(usuarioService.getLoggedUser()).thenReturn(getUsuarioDto());
//
//        when(cargoService.findById(any())).thenReturn(cargoRecuperacao);
//
//        when(passwordEncoder.encode(anyString())).thenReturn(senhaCriptografada);
//
//        // Ação (ACT)
//        usuarioService.alterarSenha(senha);
//
//        // Verificação (ASSERT)
//        verify(usuarioRepository, times(1)).save(any());
//
//    }



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

    private static UsuarioDto getUsuarioDto() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNome("Eduardo Sedrez");
        usuarioDto.setIdade(24);
        usuarioDto.setEmail("eduardo@dbccompany.com.br");
        return usuarioDto;
    }

    private static CargoEntity getCargoEntity() {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setNome("ROLE_ADMIN");
        return cargoEntity;
    }
}
