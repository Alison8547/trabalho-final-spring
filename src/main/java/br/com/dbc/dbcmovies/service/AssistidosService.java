package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.entity.ItemEntretenimento;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.AssistidosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistidosService {

    private final AssistidosRepository assistidosRepository;
    private final UsuarioService usuarioService;
    private final ItemService itemService;


    public List<ItemEntretenimento> listarAssistidos(Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.findById(idUsuario);

        List<ItemEntretenimento> resultList = null;
        try {
            resultList = assistidosRepository.listarAssistidos(idUsuario);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
        resultList.forEach(item -> item.setMediaAvaliacoes(itemService.calcularAvaliacao(item.getId())));

        return resultList;
    }

    public void deletarAssistido(Integer idItem, Integer idUsuario) throws RegraDeNegocioException {
        itemService.findById(idItem);
        usuarioService.findById(idUsuario);
        try {
            assistidosRepository.deletarAssistido(idItem, idUsuario);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
    }

    public ItemEntretenimento marcarAssistido(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {
        itemService.findById(idItem);
        usuarioService.findById(idUsuario);

        try {
            assistidosRepository.marcarAssistido(idUsuario, idItem);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }

        return itemService.findById(idItem);
    }

    public void incluirIndicacao(String itemNome, Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException {
        usuarioService.findById(idUsuario);
        assistidosRepository.incluirIndicacao(itemNome, idUsuario);
    }

}
