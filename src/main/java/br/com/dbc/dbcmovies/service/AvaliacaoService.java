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
import java.util.Optional;

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
        List<AvaliacaoEntity> avaliacaoEntities = avaliacaoRepository.findAll();

       return avaliacaoEntities.stream()
                .map(avaliacaoEntity -> {
                    AvaliacaoItemDto avaliacaoItemDto = objectMapper.convertValue(avaliacaoEntity, AvaliacaoItemDto.class);
                    avaliacaoItemDto.setIdUsuario(avaliacaoEntity.getAvaliacaoPK().getIdUsuario());
                    avaliacaoItemDto.setIdItem(avaliacaoEntity.getAvaliacaoPK().getIdItem());
                    return avaliacaoItemDto;
                }).toList();

    }
    public List<AvaliacaoItemDto> listByUsers(Integer id) throws RegraDeNegocioException {
        List<AvaliacaoEntity> avaliacaoEntities = avaliacaoRepository.pegarUsuario(id);
        return avaliacaoEntities.stream()
                    .map(avaliacaoEntity -> {
                        AvaliacaoItemDto avaliacaoItemDto = objectMapper.convertValue(avaliacaoEntity, AvaliacaoItemDto.class);
                        avaliacaoItemDto.setIdUsuario(avaliacaoEntity.getAvaliacaoPK().getIdUsuario());
                        avaliacaoItemDto.setIdItem(avaliacaoEntity.getAvaliacaoPK().getIdItem());
                        return avaliacaoItemDto;
                    }).toList();

    }

    public AvaliacaoItemDto update(AvaliacaoCreateDto avaliacaoAtualizar, Integer idUsuario, Integer idItem) throws RegraDeNegocioException{

            AvaliacaoEntity avaliacaoRecuperada = avaliacaoRepository.pegar(idUsuario, idItem);
            avaliacaoRecuperada.setNota(avaliacaoAtualizar.getNota());
            avaliacaoRecuperada.setComentario(avaliacaoAtualizar.getComentario());

            avaliacaoRepository.save(avaliacaoRecuperada);

            return getAvaliacao(idUsuario,idItem);

    }

    public void delete(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {
            avaliacaoRepository.remover(idUsuario, idItem);
    }

    public AvaliacaoItemDto getAvaliacao(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {

        AvaliacaoEntity avaliacaoEntity = avaliacaoRepository.pegar(idUsuario,idItem);
        AvaliacaoItemDto avaliacaoItemDto = objectMapper.convertValue(avaliacaoEntity, AvaliacaoItemDto.class);
        avaliacaoItemDto.setIdItem(avaliacaoEntity.getAvaliacaoPK().getIdItem());
        avaliacaoItemDto.setIdUsuario(avaliacaoEntity.getAvaliacaoPK().getIdUsuario());

        return avaliacaoItemDto;
    }

    public AvaliacaoEntity find(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {
        return avaliacaoRepository.pegar(idUsuario, idItem);
    }
}
