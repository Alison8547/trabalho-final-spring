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

    public List<ItemEntretenimentoDto> listarAssistidos() throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.findById(usuarioService.getLoggedUser().getIdUsuario());
        return usuarioEntity.getItemEntretenimentos().stream()
                .map(item -> objectMapper.convertValue(item, ItemEntretenimentoDto.class)).toList();
    }

    public ItemEntretenimentoDto marcarAssistido(Integer idItem) throws RegraDeNegocioException {
        ItemEntretenimentoEntity item = itemService.findById(idItem);
        UsuarioEntity usuario = usuarioService.findById(usuarioService.getLoggedUser().getIdUsuario());

        verificarItemAssistido(usuario, idItem);

        usuario.getItemEntretenimentos().add(item);
        item.getUsuarios().add(usuario);

        itemRepository.save(item);
        usuarioRepository.save(usuario);

        return objectMapper.convertValue(item, ItemEntretenimentoDto.class);
    }

    public void deletarAssistido(Integer idItem) throws RegraDeNegocioException {
        ItemEntretenimentoEntity item = itemService.findById(idItem);
        UsuarioEntity usuario = usuarioService.findById(usuarioService.getLoggedUser().getIdUsuario());

        this.verificarUsuarioNaTabela(usuario.getIdUsuario());
        verificarItemNaTabela(idItem, usuario);

        item.getUsuarios().remove(usuario);
        usuario.getItemEntretenimentos().remove(item);

        usuarioRepository.save(usuario);
        itemRepository.save(item);
    }

    public void verificarItemAssistido(UsuarioEntity usuario, Integer idItem) throws RegraDeNegocioException {
        ItemEntretenimentoEntity itemPego = usuario.getItemEntretenimentos().stream()
                .filter(item -> item.getIdItem() == idItem)
                .findFirst()
                .orElse(null);

        if(itemPego != null) {
            throw new RegraDeNegocioException("Item já está marcado como assistido para o usuário selecionado!");
        }
    }

    public void verificarItemNaTabela(Integer idItem, UsuarioEntity usuario) throws RegraDeNegocioException {
        usuario.getItemEntretenimentos().stream()
                .filter(item -> item.getIdItem() == idItem)
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Item ainda não foi marcado como assistido para o usuário selecionado"));
    }
    public void verificarUsuarioNaTabela(Integer idUsuario) throws RegraDeNegocioException {
        if(assistidosRepository.verificarUsuarioNaTabela(idUsuario) == 0) {
            throw new RegraDeNegocioException("Usuário selecionado não existe na tebela de assistidos!");
        }
    }
}
