package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.UsuarioAvaliacaoPersonalizadoDto;
import br.com.dbc.dbcmovies.dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.dto.UsuarioItemPersonalizadoDto;
import br.com.dbc.dbcmovies.entity.CargoEntity;
import br.com.dbc.dbcmovies.entity.TipoCargo;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.UsuarioRepository;
import br.com.dbc.dbcmovies.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final int USUARIO_ATIVO = 1;
    private static final int USUARIO_INATIVO = 0;

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final CargoService cargoService;
    private final TokenService tokenService;


    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream()
                .map(item -> objectMapper.convertValue(item, UsuarioDto.class))
                .toList();
    }

    public UsuarioDto pegar(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);
        return objectMapper.convertValue(usuarioEntity, UsuarioDto.class);
    }

    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public UsuarioDto getLoggedUser() throws RegraDeNegocioException {
        return objectMapper.convertValue(findById(getIdLoggedUser()), UsuarioDto.class);
    }

    public UsuarioDto cadastrar(UsuarioCreateDto usuario) throws RegraDeNegocioException {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuario, UsuarioEntity.class);
        CargoEntity cargo = cargoService.findById(TipoCargo.CLIENTE.getCargo());
        usuarioEntity.setCargos(Set.of(cargo));
        usuarioEntity.setAtivo(USUARIO_ATIVO);
        usuarioEntity.setSenha(senhaCriptografada);

        return objectMapper.convertValue(usuarioRepository.save(usuarioEntity), UsuarioDto.class);
    }

    public UsuarioDto desativarConta(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(idUsuario);
        usuarioEncontrado.setAtivo(USUARIO_INATIVO);
        usuarioRepository.save(usuarioEncontrado);

        return objectMapper.convertValue(usuarioEncontrado, UsuarioDto.class);
    }

    public UsuarioDto tornarContaAdmin(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(idUsuario);
        CargoEntity cargo = cargoService.findById(TipoCargo.ADMIN.getCargo());
        Set<CargoEntity> cargoSet = new HashSet<>();
        cargoSet.add(cargo);
        usuarioEncontrado.setCargos(cargoSet);
        usuarioRepository.save(usuarioEncontrado);
        return objectMapper.convertValue(usuarioEncontrado, UsuarioDto.class);
    }

    public List<UsuarioDto> contasInativas(){
        return usuarioRepository.findByAtivo(USUARIO_INATIVO).stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity,UsuarioDto.class))
                .toList();
    }

    public UsuarioDto editar(Integer id, UsuarioCreateDto usuarioAtualizar) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(id);
        usuarioEncontrado.setNome(usuarioAtualizar.getNome());
        usuarioEncontrado.setIdade(usuarioAtualizar.getIdade());
        usuarioEncontrado.setEmail(usuarioAtualizar.getEmail());
        usuarioEncontrado.setSenha(passwordEncoder.encode(usuarioAtualizar.getSenha()));

        usuarioRepository.save(usuarioEncontrado);

        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioEncontrado, UsuarioDto.class);

        emailService.sendEmailUsuario(usuarioDto, TipoTemplate.UPDATE);

        return usuarioDto;

    }

    public void remover(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioEntity, UsuarioDto.class);
        usuarioRepository.delete(usuarioEntity);
        emailService.sendEmailUsuario(usuarioDto, TipoTemplate.DELETE);

    }

    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario n??o encontrado!"));
    }


    public List<UsuarioItemPersonalizadoDto> listaPersonalizadaUsuarioItem(Integer idUsuario) {
        return usuarioRepository.listaPersonalizadaUsuarioItem(idUsuario)
                .stream().toList();
    }

    public List<UsuarioAvaliacaoPersonalizadoDto> listaPersonalizadaUsuarioAvaliacao(Integer idUsuario) {
        return usuarioRepository.listaPersonalizadaUsuarioAvaliacao(idUsuario)
                .stream().toList();
    }

    public void recuperarSenha(String email) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraDeNegocioException("E-mail n??o encontrado!"));

        CargoEntity modoRecuperacao = cargoService.findById(TipoCargo.RECUPERACAO.getCargo());

        usuarioEntity.getCargos().add(modoRecuperacao);
        String tokenRecuperacaoSenha = tokenService.getToken(usuarioEntity, true);

        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntity);

        emailService.sendEmailRecuperacaoSenha(usuarioSalvo, "email-recuperacao-senha-template.html", tokenRecuperacaoSenha);
    }

    public void alterarSenha(String senha) throws RegraDeNegocioException {
        UsuarioEntity usuario = (UsuarioEntity) this.findByEmail(getLoggedUser().getEmail())
                .orElseThrow(() -> new RegraDeNegocioException("Usuario n??o encontrado!"));

        CargoEntity modoRecuperacao = cargoService.findById(TipoCargo.RECUPERACAO.getCargo());

        usuario.getCargos().stream()
                .filter(cargo -> cargo.getIdCargo() == modoRecuperacao.getIdCargo())
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Senha ja foi alterada!"));

        String senhaCriptografada = passwordEncoder.encode(senha);
        usuario.setSenha(senhaCriptografada);
        usuario.getCargos().remove(modoRecuperacao);
        usuarioRepository.save(usuario);
    }
}
