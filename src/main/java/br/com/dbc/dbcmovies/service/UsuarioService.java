package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
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


    public List<UsuarioDto> listar() throws RegraDeNegocioException {
        return  usuarioRepository.listar().stream()
                .map(item -> objectMapper.convertValue(item, UsuarioDto.class))
                .toList();
    }

    public UsuarioDto pegar(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity =  findById(id);
        return objectMapper.convertValue(usuarioEntity, UsuarioDto.class);
    }

    public UsuarioDto adicionar(UsuarioCreateDto usuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntityAdicionado = objectMapper.convertValue(usuario, UsuarioEntity.class);
        usuarioEntityAdicionado = usuarioRepository.adicionar(usuarioEntityAdicionado);
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioEntityAdicionado, UsuarioDto.class);

        emailService.sendEmailUsuario(usuarioDto, TipoTemplate.CREATE);

        return usuarioDto;
    }

    public UsuarioDto editar(Integer id, UsuarioCreateDto usuarioCreateDto) throws RegraDeNegocioException {
        findById(id);
        UsuarioEntity usuarioEntityConvertido = objectMapper.convertValue(usuarioCreateDto, UsuarioEntity.class);
        if (usuarioRepository.editar(id, usuarioEntityConvertido)) {
            UsuarioDto usuarioDto = objectMapper.convertValue(usuarioRepository.pegar(id), UsuarioDto.class);
            emailService.sendEmailUsuario(usuarioDto,TipoTemplate.UPDATE);
            return usuarioDto;

        } else {
            throw new RegraDeNegocioException("Não foi possível atualizar o Usuário!");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioEntity, UsuarioDto.class);
        usuarioRepository.remover(id);
        emailService.sendEmailUsuario(usuarioDto,TipoTemplate.DELETE);

    }

    public UsuarioEntity pegarLogin(UsuarioEntity usuarioEntityLogin) throws RegraDeNegocioException {
        return usuarioRepository.pegarLogin(usuarioEntityLogin);
    }

    public UsuarioDto tornarUsuarioAdmin(Integer id) throws RegraDeNegocioException {
        findById(id);
        if (usuarioRepository.tornarUsuarioAdmin(id)) {
            return objectMapper.convertValue(usuarioRepository.pegar(id),UsuarioDto.class);
        } else {
            throw new RegraDeNegocioException("Não foi possível transformar o Usuário em Administrador!");
        }
    }

    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        try {
            return usuarioRepository.pegar(id);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Usuário não encontrado!");
        }
    }
}
