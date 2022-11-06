package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.UsuarioAssistidoPersonalizadoDto;
import br.com.dbc.dbcmovies.dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.dto.UsuarioItemPersonalizadoDto;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.entity.TipoUsuario;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;


    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream()
                .map(item -> objectMapper.convertValue(item, UsuarioDto.class))
                .toList();
    }

    public UsuarioDto pegar(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);
        return objectMapper.convertValue(usuarioEntity, UsuarioDto.class);
    }

    public UsuarioDto adicionar(UsuarioCreateDto usuario) {
        UsuarioEntity usuarioEntityAdicionado = objectMapper.convertValue(usuario, UsuarioEntity.class);
        usuarioEntityAdicionado = usuarioRepository.save(usuarioEntityAdicionado);
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioEntityAdicionado, UsuarioDto.class);

        emailService.sendEmailUsuario(usuarioDto, TipoTemplate.CREATE);

        return usuarioDto;
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

    public UsuarioDto tornarUsuarioAdmin(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(id);
        usuarioEncontrado.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
        usuarioRepository.save(usuarioEncontrado);

        return objectMapper.convertValue(usuarioEncontrado, UsuarioDto.class);

    }

    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
    }


    public List<UsuarioItemPersonalizadoDto> listaPersonalizadaUsuarioItem(Integer idUsuario) {
        return usuarioRepository.listaPersonalizadaUsuarioItem(idUsuario)
                .stream().toList();
    }

    public List<UsuarioAssistidoPersonalizadoDto> listaPersonalizadaUsuarioAvaliacao(Integer idUsuario) {
        return usuarioRepository.listaPersonalizadaUsuarioAvaliacao(idUsuario)
                .stream().toList();
    }
}
