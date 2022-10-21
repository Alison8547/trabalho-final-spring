package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.entity.Usuario;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() throws BancoDeDadosException {
        return usuarioRepository.listar();
    }

    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        return usuarioRepository.adicionar(usuario);
    }

    public Usuario editar(Integer id, Usuario usuario) throws BancoDeDadosException, RegraDeNegocioException {
        findById(id);
        if (usuarioRepository.editar(id, usuario)) {
            return usuarioRepository.pegar(id);
        } else {
            throw new RegraDeNegocioException("Não foi possível atualizar o Usuário!");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        findById(id);
        usuarioRepository.remover(id);
    }

    public Usuario pegarUsuario(Usuario usuarioLogin) throws BancoDeDadosException {
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
