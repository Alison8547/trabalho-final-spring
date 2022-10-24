package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.Dto.AvaliacaoCreateDto;
import br.com.dbc.dbcmovies.Dto.AvaliacaoDto;
import br.com.dbc.dbcmovies.entity.Avaliacao;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
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

    public AvaliacaoDto create(AvaliacaoCreateDto avaliacaoDto, Integer idUsuario, Integer idItem) throws RegraDeNegocioException, BancoDeDadosException {
        usuarioService.findById(idUsuario);
        itemService.findById(idItem);

        Avaliacao avaliacao = objectMapper.convertValue(avaliacaoDto, Avaliacao.class);
        avaliacao = avaliacaoRepository.adicionar(avaliacao, idUsuario, idItem);

        return objectMapper.convertValue(avaliacao, AvaliacaoDto.class);
    }

    public List<AvaliacaoDto> list() throws BancoDeDadosException, RegraDeNegocioException {

        return avaliacaoRepository.listarAvaliacoes(usuarioService, itemService).stream()
                .map(item -> objectMapper.convertValue(item, AvaliacaoDto.class))
                .toList();
    }

    public List<AvaliacaoDto> listByUsers(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        usuarioService.findById(id);
        return avaliacaoRepository.listarAvaliacoesUsuario(id, usuarioService, itemService).stream()
                .map(item -> objectMapper.convertValue(item, AvaliacaoDto.class))
                .toList();
    }

    public AvaliacaoDto update(AvaliacaoCreateDto avaliacaoDto, Integer idUsuario, Integer idItem) throws RegraDeNegocioException, BancoDeDadosException {
        usuarioService.findById(idUsuario);
        itemService.findById(idItem);

        Avaliacao avaliacao = objectMapper.convertValue(avaliacaoDto, Avaliacao.class);

        if(avaliacaoRepository.editar(avaliacao, idUsuario, idItem)){
            return objectMapper.convertValue(find(idUsuario, idItem), AvaliacaoDto.class);
        }else {
            throw new RegraDeNegocioException("Avaliação não encontrada");
        }
    }

    public void delete(Integer idUsuario, Integer idItem) throws BancoDeDadosException {
        avaliacaoRepository.remover(idUsuario, idItem);
    }

    public AvaliacaoDto getAvaliacao(Integer idUsuario, Integer idItem) throws BancoDeDadosException, RegraDeNegocioException {
        Avaliacao avaliacao = avaliacaoRepository.pegar(idUsuario, idItem, usuarioService, itemService);
        return objectMapper.convertValue(avaliacao, AvaliacaoDto.class);
    }

    public Avaliacao find(Integer idUsuario, Integer idItem) throws BancoDeDadosException, RegraDeNegocioException {
        return avaliacaoRepository.pegar(idUsuario, idItem, usuarioService, itemService);
    }
}
