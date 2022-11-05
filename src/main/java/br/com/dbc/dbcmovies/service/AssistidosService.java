package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.ItemEntretenimentoDto;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.AssistidosRepository;
import br.com.dbc.dbcmovies.repository.ItemRepository;
import br.com.dbc.dbcmovies.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistidosService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final AssistidosRepository assistidosRepository;
    private final ObjectMapper objectMapper;

    public List<ItemEntretenimentoDto> listarAssistidos(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.findById(idUsuario);
        return usuarioEntity.getItemEntretenimentos().stream()
                .map(item -> objectMapper.convertValue(item, ItemEntretenimentoDto.class)).toList();
    }

    public ItemEntretenimentoDto marcarAssistido(Integer idItem, Integer idUsuario) throws RegraDeNegocioException {
        ItemEntretenimentoEntity item = itemService.findById(idItem);
        UsuarioEntity usuario = usuarioService.findById(idUsuario);

        verificarItemAssistido(idItem, usuario);

        usuario.getItemEntretenimentos().add(item);
        item.getUsuarios().add(usuario);

        itemRepository.save(item);
        usuarioRepository.save(usuario);

        return objectMapper.convertValue(item, ItemEntretenimentoDto.class);
    }

    public void deletarAssistido(Integer idItem, Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuario = usuarioService.findById(idUsuario);
        ItemEntretenimentoEntity item = itemService.findById(idItem);

        this.verificarUsuarioNaTabela(idUsuario);
        verificarItemNaTabela(idItem, usuario);

        item.getUsuarios().remove(usuario);
        usuario.getItemEntretenimentos().remove(item);

        usuarioRepository.save(usuario);
        itemRepository.save(item);
    }

    public void verificarItemAssistido(Integer idItem, UsuarioEntity usuario) throws RegraDeNegocioException {
        usuario.getItemEntretenimentos().stream()
                .filter(item -> !(item.getIdItem() == idItem))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Item já está marcado como assistido para o usuário selecionado!"));
    }

    public void verificarItemNaTabela(Integer idItem, UsuarioEntity usuario) throws RegraDeNegocioException {
        usuario.getItemEntretenimentos().stream()
                .filter(item -> item.getIdItem() == idItem)
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Item ainda não foi maracado como assistido para o usuário selecionado"));
    }
    public void verificarUsuarioNaTabela(Integer idUsuario) throws RegraDeNegocioException {
        if(assistidosRepository.verificarUsuarioNaTabela(idUsuario) == 0) {
            throw new RegraDeNegocioException("Usuário selecionado não existe na tebela de assistidos!");
        }
    }
}
