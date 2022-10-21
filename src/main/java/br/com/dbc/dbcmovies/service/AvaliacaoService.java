package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.entity.Avaliacao;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {
    private AvaliacaoRepository avaliacaoRepository;
    private UsuarioService usuarioService;
    private ItemService itemService;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, UsuarioService usuarioService, ItemService itemService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.usuarioService = usuarioService;
        this.itemService = itemService;
    }

    public Avaliacao create(Avaliacao avaliacao, Integer idUsuario, Integer idItem) throws RegraDeNegocioException, BancoDeDadosException {
        usuarioService.findById(idUsuario);
        itemService.findById(idItem);
        return avaliacaoRepository.adicionar(avaliacao, idUsuario, idItem);
    }

    public List<Avaliacao> list() throws BancoDeDadosException, RegraDeNegocioException {
        return avaliacaoRepository.listarAvaliacoes(usuarioService, itemService);
    }

    public List<Avaliacao> listByUsers(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        usuarioService.findById(id);
        return avaliacaoRepository.listarAvaliacoesUsuario(id, usuarioService, itemService);
    }

    public Avaliacao update(Avaliacao avaliacao, Integer idUsuario, Integer idItem) throws RegraDeNegocioException, BancoDeDadosException {
        usuarioService.findById(idUsuario);
        itemService.findById(idItem);
        if(avaliacaoRepository.editar(avaliacao, idUsuario, idItem)){
            return find(idUsuario, idItem);
        }else {
            throw new RegraDeNegocioException("Avaliação não encontrada");
        }
    }

    public void delete(Integer idUsuario, Integer idItem) throws BancoDeDadosException {
        avaliacaoRepository.remover(idUsuario, idItem);
    }

    public Avaliacao find(Integer idUsuario, Integer idItem) throws BancoDeDadosException, RegraDeNegocioException {
        return avaliacaoRepository.pegar(idUsuario, idItem, usuarioService, itemService);
    }
}
