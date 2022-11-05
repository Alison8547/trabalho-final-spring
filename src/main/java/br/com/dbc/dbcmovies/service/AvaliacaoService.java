package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.*;
import br.com.dbc.dbcmovies.entity.AvaliacaoEntity;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import br.com.dbc.dbcmovies.entity.pk.AvaliacaoPK;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioService usuarioService;
    private final ItemService itemService;
    private final ObjectMapper objectMapper;
//    private final EmailService emailService;

    public AvaliacaoDto create(AvaliacaoCreateDto avaliacaoDto, Integer idUsuario, Integer idItem) throws RegraDeNegocioException {

        UsuarioEntity usuarioEntity = usuarioService.findById(idUsuario);
        this.verificarItemAvaliado(usuarioEntity, idItem);

        ItemEntretenimentoEntity itemEntity = itemService.findById(idItem);
        AvaliacaoEntity avaliacaoEntity = objectMapper.convertValue(avaliacaoDto, AvaliacaoEntity.class);
        avaliacaoEntity.setAvaliacaoPK(new AvaliacaoPK());

        avaliacaoEntity.getAvaliacaoPK().setIdItem(idItem);
        avaliacaoEntity.getAvaliacaoPK().setIdUsuario(idUsuario);

        avaliacaoEntity.setUsuario(usuarioEntity);
        avaliacaoEntity.setItemEntretenimento(itemEntity);

        avaliacaoRepository.save(avaliacaoEntity);

        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioEntity, UsuarioDto.class);
        ItemEntretenimentoDto itemDto = objectMapper.convertValue(itemEntity, ItemEntretenimentoDto.class);

        AvaliacaoDto avaliacaoDTO = objectMapper.convertValue(avaliacaoEntity, AvaliacaoDto.class);
        avaliacaoDTO.setIdUsuario(avaliacaoEntity.getAvaliacaoPK().getIdUsuario());
        avaliacaoDTO.setIdItemEntretenimento(avaliacaoEntity.getAvaliacaoPK().getIdItem());
        avaliacaoDTO.setUsuarioDto(usuarioDto);
        avaliacaoDTO.setItemEntretenimentoDto(itemDto);

        return avaliacaoDTO;
    }

    public void verificarItemAvaliado(UsuarioEntity usuario, Integer idItem) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = usuario.getAvaliacaos().stream()
                .filter(avaliacao -> avaliacao.getItemEntretenimento().getIdItem() == idItem)
                .findFirst()
                .orElse(null);

        if(avaliacaoEntity != null) {
            throw new RegraDeNegocioException("Item já foi avaliado pelo usuário selecionado!");
        }
    }

    public List<AvaliacaoItemDto> list() throws RegraDeNegocioException {
            return avaliacaoRepository.findItemEntretenimento().stream()
                    .map(item -> objectMapper.convertValue(item, AvaliacaoItemDto.class))
                    .toList();
    }
    public List<AvaliacaoDto> listByUsers(Integer id) throws RegraDeNegocioException {
            usuarioService.findById(id);
            return avaliacaoRepository.findByIdEntretenimento(id).stream()
                    .map(item -> objectMapper.convertValue(item, AvaliacaoDto.class))
                    .toList();
    }

    public AvaliacaoDto update(AvaliacaoCreateDto avaliacaoAtualizar, Integer idUsuario, Integer idItem) throws RegraDeNegocioException{

        UsuarioEntity usuarioRecuperado = usuarioService.findById(idUsuario);
        ItemEntretenimentoEntity itemRecuperado = itemService.findById(idItem);
        AvaliacaoEntity avaliacaoRecuperada = find(idUsuario, idItem);
            avaliacaoRecuperada.setNota(avaliacaoAtualizar.getNota());
            avaliacaoRecuperada.setComentario(avaliacaoAtualizar.getComentario());
            avaliacaoRecuperada.setUsuario(usuarioRecuperado);
            avaliacaoRecuperada.setItemEntretenimento(itemRecuperado);
            avaliacaoRepository.save(avaliacaoRecuperada);
            AvaliacaoEntity avaliacao = objectMapper.convertValue(avaliacaoAtualizar, AvaliacaoEntity.class);
            return objectMapper.convertValue(avaliacaoRecuperada, AvaliacaoDto.class);

    }

    public void delete(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {
            avaliacaoRepository.remover(idUsuario, idItem);
    }

    public AvaliacaoItemDto getAvaliacao(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {
            AvaliacaoEntity avaliacaoEntity = avaliacaoRepository.pegar(idUsuario, idItem);
            return objectMapper.convertValue(avaliacaoEntity, AvaliacaoItemDto.class);
    }

    public AvaliacaoEntity find(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {
        return avaliacaoRepository.pegar(idUsuario, idItem);
    }
}
