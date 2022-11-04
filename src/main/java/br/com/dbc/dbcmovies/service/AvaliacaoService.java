package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.AvaliacaoCreateDto;
import br.com.dbc.dbcmovies.dto.AvaliacaoDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.entity.Avaliacao;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.entity.Usuario;
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
    private final EmailService emailService;

    public AvaliacaoDto create(AvaliacaoCreateDto avaliacaoDto, Integer idUsuario, Integer idItem) throws RegraDeNegocioException {
        try{

            Usuario usuario = usuarioService.findById(idUsuario);
            ItemEntretenimentoEntity item = itemService.findById(idItem);

            UsuarioDto usuarioDto = objectMapper.convertValue(usuario, UsuarioDto.class);

            Avaliacao avaliacao = objectMapper.convertValue(avaliacaoDto, Avaliacao.class);

            avaliacao = avaliacaoRepository.adicionar(avaliacao, idUsuario, idItem);
            avaliacao.setUsuario(usuario);
            avaliacao.setItemEntretenimentoEntity(item);

            AvaliacaoDto dto = objectMapper.convertValue(avaliacao, AvaliacaoDto.class);

            emailService.sendEmailAvaliacao(dto, TipoTemplate.CREATE, usuarioDto);

            return dto;

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Não foi possivel criar uma avaliação");
        }
    }

    public List<AvaliacaoDto> list() throws RegraDeNegocioException {

        try{

            return avaliacaoRepository.listarAvaliacoes(usuarioService, itemService).stream()
                    .map(item -> objectMapper.convertValue(item, AvaliacaoDto.class))
                    .toList();

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException(ex.getMessage());
        }
    }

    public List<AvaliacaoDto> listByUsers(Integer id) throws RegraDeNegocioException {

        try{

            usuarioService.findById(id);
            return avaliacaoRepository.listarAvaliacoesUsuario(id, usuarioService, itemService).stream()
                    .map(item -> objectMapper.convertValue(item, AvaliacaoDto.class))
                    .toList();

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException(ex.getMessage());
        }

    }

    public AvaliacaoDto update(AvaliacaoCreateDto avaliacaoDto, Integer idUsuario, Integer idItem) throws RegraDeNegocioException{

        try {

            usuarioService.findById(idUsuario);
            itemService.findById(idItem);

            Avaliacao avaliacao = objectMapper.convertValue(avaliacaoDto, Avaliacao.class);

            if(avaliacaoRepository.editar(avaliacao, idUsuario, idItem)){
                return objectMapper.convertValue(find(idUsuario, idItem), AvaliacaoDto.class);
            }else {
                throw new RegraDeNegocioException("Avaliação não encontrada");
            }

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Não foi possivel atualizar a avaliação.");
        }
    }

    public void delete(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {

        try {
            avaliacaoRepository.remover(idUsuario, idItem);

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Não foi possivel deletar a avaliação");
        }
    }

    public AvaliacaoDto getAvaliacao(Integer idUsuario, Integer idItem) throws RegraDeNegocioException {

        try {

            Avaliacao avaliacao = avaliacaoRepository.pegar(idUsuario, idItem, usuarioService, itemService);
            return objectMapper.convertValue(avaliacao, AvaliacaoDto.class);

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Erro ao pegar avaliação.");
        }

    }

    public Avaliacao find(Integer idUsuario, Integer idItem) throws BancoDeDadosException, RegraDeNegocioException {
        return avaliacaoRepository.pegar(idUsuario, idItem, usuarioService, itemService);
    }
}
