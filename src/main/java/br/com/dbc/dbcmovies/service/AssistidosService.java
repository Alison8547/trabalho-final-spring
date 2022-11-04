package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.ItemEntretenimentoDto;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.AssistidosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistidosService {

    private final AssistidosRepository assistidosRepository;
    private final UsuarioService usuarioService;
    private final ItemService itemService;
    private final ObjectMapper objectMapper;

    public List<ItemEntretenimentoDto> listarAssistidos(Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.findById(idUsuario);

        List<ItemEntretenimentoEntity> resultList = null;
        try {
            resultList = assistidosRepository.listarAssistidos(idUsuario);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
        resultList.forEach(item -> item.setMediaAvaliacoes(itemService.calcularAvaliacao(item.getId())));

        return resultList.stream()
                .map(item -> objectMapper.convertValue(item, ItemEntretenimentoDto.class))
                .toList();
    }

    public void deletarAssistido(Integer idItem, Integer idUsuario) throws RegraDeNegocioException {
        itemService.findById(idItem);
        usuarioService.findById(idUsuario);
        try {
            assistidosRepository.deletarAssistido(idItem, idUsuario);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public ItemEntretenimentoDto marcarAssistido(Integer idItem, Integer idUsuario) throws RegraDeNegocioException {
        itemService.findById(idItem);
        usuarioService.findById(idUsuario);

        try {
            assistidosRepository.marcarAssistido(idUsuario, idItem);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException(e.getMessage());
        }

        return objectMapper.convertValue(itemService.findById(idItem), ItemEntretenimentoDto.class);
    }
}
