package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.UsuarioAvaliacaoPersonalizadoDto;
import br.com.dbc.dbcmovies.dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.dto.UsuarioItemPersonalizadoDto;
import br.com.dbc.dbcmovies.entity.CargoEntity;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.CargoRepository;
import br.com.dbc.dbcmovies.repository.UsuarioRepository;
import br.com.dbc.dbcmovies.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final CargoRepository cargoRepository;
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
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public UsuarioDto getLoggedUser() throws RegraDeNegocioException {
        return objectMapper.convertValue(findById(getIdLoggedUser()), UsuarioDto.class);
    }

    public UsuarioDto cadastrar(UsuarioCreateDto usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuario, UsuarioEntity.class);
        Optional<CargoEntity> cargo = cargoRepository.findById(2);
        usuarioEntity.setCargos(Set.of(cargo.get()));
        usuarioEntity.setAtivo(1);
        usuarioEntity.setSenha(senhaCriptografada);

        return objectMapper.convertValue(usuarioRepository.save(usuarioEntity), UsuarioDto.class);
    }

    public UsuarioDto desativarConta(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(idUsuario);
        usuarioEncontrado.setAtivo(0);
        usuarioRepository.save(usuarioEncontrado);

        return objectMapper.convertValue(usuarioEncontrado,UsuarioDto.class);
    }

    public UsuarioDto editar(Integer id, UsuarioCreateDto usuarioAtualizar) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(id);
        usuarioEncontrado.setNome(usuarioAtualizar.getNome());
        usuarioEncontrado.setIdade(usuarioAtualizar.getIdade());
        usuarioEncontrado.setEmail(usuarioAtualizar.getEmail());
        usuarioEncontrado.setSenha(usuarioAtualizar.getSenha());

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

    public UsuarioDto pegarLogin(String email, String senha) {
        return objectMapper.convertValue(usuarioRepository.findByEmailAndSenha(email, senha),UsuarioDto.class);
    }


    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
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
                .orElseThrow(() -> new RegraDeNegocioException("E-mail não encontrado!"));

        Optional<CargoEntity> modoRecuperacao = cargoRepository.findById(3);

        usuarioEntity.getCargos().add(modoRecuperacao.get());
        String tokenRecuperacaoSenha = tokenService.getToken(usuarioEntity, true);

        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntity);

        emailService.sendEmailRecuperacaoSenha(usuarioSalvo, "email-recuperacao-senha-template.html", tokenRecuperacaoSenha);
    }

    public void alterarSenha(String senha) throws RegraDeNegocioException {
        UsuarioEntity usuario = (UsuarioEntity) this.findByEmail(getLoggedUser().getEmail())
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));

        Optional<CargoEntity> modoRecuperacao = cargoRepository.findById(3);

        usuario.getCargos().stream()
                .filter(cargo -> cargo.getIdCargo() == modoRecuperacao.get().getIdCargo())
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Senha ja foi alterada!"));

        String senhaCriptografada = passwordEncoder.encode(senha);
        usuario.setSenha(senhaCriptografada);
        usuario.getCargos().remove(modoRecuperacao.get());
        usuarioRepository.save(usuario);
    }
}
