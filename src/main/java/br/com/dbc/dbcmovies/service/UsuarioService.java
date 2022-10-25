package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.Dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.Dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.Usuario;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
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


    public List<UsuarioDto> listar() throws BancoDeDadosException {
        return  usuarioRepository.listar().stream()
                .map(item -> objectMapper.convertValue(item, UsuarioDto.class))
                .toList();
    }

    public Usuario pegar(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        findById(id);
        return usuarioRepository.pegar(id);
    }

    public UsuarioDto adicionar(UsuarioCreateDto usuario) throws BancoDeDadosException {
        Usuario usuarioAdicionado = objectMapper.convertValue(usuario, Usuario.class);
        usuarioAdicionado = usuarioRepository.adicionar(usuarioAdicionado);
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioAdicionado, UsuarioDto.class);
        return usuarioDto;
    }

    public UsuarioDto editar(Integer id, UsuarioCreateDto usuarioCreateDto) throws BancoDeDadosException, RegraDeNegocioException {
        findById(id);
        Usuario usuarioConvertido = objectMapper.convertValue(usuarioCreateDto, Usuario.class);
        if (usuarioRepository.editar(id, usuarioConvertido)) {
            return objectMapper.convertValue(usuarioRepository.pegar(id), UsuarioDto.class) ;
        } else {
            throw new RegraDeNegocioException("Não foi possível atualizar o Usuário!");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        findById(id);
        usuarioRepository.remover(id);
    }

    public Usuario pegarLogin(Usuario usuarioLogin) throws BancoDeDadosException {
        return usuarioRepository.pegarLogin(usuarioLogin);
    }

    public Usuario tornarUsuarioAdmin(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        findById(id);
        if (usuarioRepository.tornarUsuarioAdmin(id)) {
            return usuarioRepository.pegar(id);
        } else {
            throw new RegraDeNegocioException("Não foi possível transformar o Usuário em Administrador!");
        }
    }

    public Usuario findById(Integer id) throws RegraDeNegocioException {
        try {
            return usuarioRepository.pegar(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Usuário não encontrado!");
        }
    }
}
