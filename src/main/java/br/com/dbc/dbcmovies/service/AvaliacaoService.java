package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.AvaliacaoCreateDto;
import br.com.dbc.dbcmovies.dto.AvaliacaoDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.AvaliacaoEntity;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
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

            ItemEntretenimentoEntity item = itemService.findById(idItem);
            UsuarioEntity usuarioEntity = usuarioService.findById(idUsuario);
            UsuarioDto usuarioDto = objectMapper.convertValue(usuarioEntity, UsuarioDto.class);

            AvaliacaoEntity avaliacaoEntity = objectMapper.convertValue(avaliacaoDto, AvaliacaoEntity.class);
            avaliacaoEntity.setUsuario(usuarioEntity);
            avaliacaoEntity.setItemEntretenimento(item);
            avaliacaoRepository.save(avaliacaoEntity);
            AvaliacaoDto dto = objectMapper.convertValue(avaliacaoEntity, AvaliacaoDto.class);
//            emailService.sendEmailAvaliacao(dto, TipoTemplate.CREATE, usuarioDto);
            return dto;
    }

    public List<AvaliacaoDto> list() throws RegraDeNegocioException {
            return avaliacaoRepository.findItemEntretenimento().stream()
                    .map(item -> objectMapper.convertValue(item, AvaliacaoDto.class))
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

    public AvaliacaoDto getAvaliacao(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {
            AvaliacaoEntity avaliacaoEntity = avaliacaoRepository.pegar(idUsuario, idItem);
            return objectMapper.convertValue(avaliacaoEntity, AvaliacaoDto.class);
    }

    public AvaliacaoEntity find(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {
        return avaliacaoRepository.pegar(idUsuario, idItem);
    }
}
