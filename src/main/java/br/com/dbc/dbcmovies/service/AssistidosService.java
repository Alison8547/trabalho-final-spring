package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.entity.ItemEntretenimento;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.AssistidosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssistidosService {

    private AssistidosRepository assistidosRepository;
    private UsuarioService usuarioService;
    private ItemService itemService;

    public AssistidosService(AssistidosRepository assistidosRepository, UsuarioService usuarioService, ItemService itemService) {
        this.assistidosRepository = assistidosRepository;
        this.usuarioService = usuarioService;
        this.itemService = itemService;
    }

    public List<ItemEntretenimento> listarAssistidos(Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        usuarioService.findById(idUsuario);

        List<ItemEntretenimento> resultList = assistidosRepository.listarAssistidos(idUsuario);
        resultList.forEach(item -> item.setMediaAvaliacoes(itemService.calcularAvaliacao(item.getId())));

        return resultList;
    }

    public void deletarAssistido(Integer idItem, Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        itemService.findById(idItem);
        usuarioService.findById(idUsuario);
        assistidosRepository.deletarAssistido(idItem, idUsuario);
    }

    public ItemEntretenimento marcarAssistido(Integer idUsuario, Integer idItem) throws RegraDeNegocioException, BancoDeDadosException {
        itemService.findById(idItem);
        usuarioService.findById(idUsuario);

       assistidosRepository.marcarAssistido(idUsuario,idItem);

       return itemService.findById(idItem);
    }

    public void incluirIndicacao(String itemNome,Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException {
        usuarioService.findById(idUsuario);
        assistidosRepository.incluirIndicacao(itemNome,idUsuario);
    }

}
