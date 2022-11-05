package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.*;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.TipoUsuario;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    private final UsuarioService usuarioService;
    //  private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public ItemEntretenimentoDto createFilme(ItemEntretenimentoCreateDto itemEntretenimentoDto, Integer idAdmin) throws RegraDeNegocioException {

        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class);

        if (usuarioDto.getTipoUsuario().equals(TipoUsuario.CLIENTE)) {
            throw new RegraDeNegocioException("Usuario precisa ser administrador para cadastrar um filme.");
        }

        ItemEntretenimentoEntity itemEntity = objectMapper.convertValue(itemEntretenimentoDto, ItemEntretenimentoEntity.class);

        ItemEntretenimentoDto dto = objectMapper.convertValue(itemRepository.save(itemEntity), ItemEntretenimentoDto.class);
        // emailService.sendEmailItemEntretenimento(dto, TipoTemplate.CREATE, usuarioDto);
        return objectMapper.convertValue(itemEntity, ItemEntretenimentoDto.class);

    }

    public ItemEntretenimentoDto createSerie(ItemEntretenimentoCreateDto itemEntretenimentoDto, Integer idAdmin) throws RegraDeNegocioException {
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class);

        if (usuarioDto.getTipoUsuario().equals(TipoUsuario.CLIENTE)) {
            throw new RegraDeNegocioException("Usuario precisa ser administrador para cadastrar um filme.");
        }
        ItemEntretenimentoEntity itemEntity = objectMapper.convertValue(itemEntretenimentoDto, ItemEntretenimentoEntity.class);
        ItemEntretenimentoDto dto = objectMapper.convertValue(itemRepository.save(itemEntity), ItemEntretenimentoDto.class);
        //  emailService.sendEmailItemEntretenimento(dto, TipoTemplate.CREATE, usuarioDto);
        return objectMapper.convertValue(itemEntity, ItemEntretenimentoDto.class);

    }

    public List<ItemEntretenimentoDto> list() throws RegraDeNegocioException {

        return itemRepository.findAll().stream()
                .map(itemEntretenimentoEntity -> objectMapper.convertValue(itemEntretenimentoEntity, ItemEntretenimentoDto.class))
                .toList();

    }

    public List<ItemEntretenimentoDto> filter(String tipo, String genero, Integer classificacao) throws RegraDeNegocioException {

        return itemRepository.filtrar(tipo.toUpperCase(), genero.toUpperCase(), classificacao).stream()
                .map(itemEntretenimentoEntity -> objectMapper.convertValue(itemEntretenimentoEntity, ItemEntretenimentoDto.class))
                .toList();
    }

    public ItemEntretenimentoDto updateFilme(Integer id, FilmeCreateDto filmeAtualizar, Integer idAdmin) throws RegraDeNegocioException {
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class);
        if (usuarioDto.getTipoUsuario().equals(TipoUsuario.CLIENTE)) {
            throw new RegraDeNegocioException("Usuario precisa ser administrador para atualizar um filme.");
        }
        ItemEntretenimentoEntity itemEntretenimentoEncontrado = findById(id);
        itemEntretenimentoEncontrado.setNome(filmeAtualizar.getNome());
        itemEntretenimentoEncontrado.setTipo(filmeAtualizar.getTipo());
        itemEntretenimentoEncontrado.setGenero(filmeAtualizar.getGenero());
        itemEntretenimentoEncontrado.setSinopse(filmeAtualizar.getSinopse());
        itemEntretenimentoEncontrado.setAnoLancamento(filmeAtualizar.getAnoLancamento());
        itemEntretenimentoEncontrado.setClassificacao(filmeAtualizar.getClassificacao());
        itemEntretenimentoEncontrado.setPlataforma(filmeAtualizar.getPlataforma());
        itemEntretenimentoEncontrado.setDuracao(filmeAtualizar.getDuracao());
        itemRepository.save(itemEntretenimentoEncontrado);
        ItemEntretenimentoDto dto = objectMapper.convertValue(itemEntretenimentoEncontrado, ItemEntretenimentoDto.class);
        //   emailService.sendEmailItemEntretenimento(dto, TipoTemplate.UPDATE, usuarioDto);


        return dto;

    }

    public ItemEntretenimentoDto updateSerie(Integer id, SerieCreateDto serieAtualizar, Integer idAdmin) throws RegraDeNegocioException {
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class);
        if (usuarioDto.getTipoUsuario().equals(TipoUsuario.CLIENTE)) {
            throw new RegraDeNegocioException("Usuario precisa ser administrador para atualizar um filme.");
        }
        ItemEntretenimentoEntity itemEntretenimentoEncontrado = findById(id);
        itemEntretenimentoEncontrado.setNome(serieAtualizar.getNome());
        itemEntretenimentoEncontrado.setTipo(serieAtualizar.getTipo());
        itemEntretenimentoEncontrado.setGenero(serieAtualizar.getGenero());
        itemEntretenimentoEncontrado.setSinopse(serieAtualizar.getSinopse());
        itemEntretenimentoEncontrado.setAnoLancamento(serieAtualizar.getAnoLancamento());
        itemEntretenimentoEncontrado.setClassificacao(serieAtualizar.getClassificacao());
        itemEntretenimentoEncontrado.setPlataforma(serieAtualizar.getPlataforma());
        itemEntretenimentoEncontrado.setTemporadas(serieAtualizar.getTemporadas());
        itemEntretenimentoEncontrado.setEpisodios(serieAtualizar.getEpisodios());
        itemRepository.save(itemEntretenimentoEncontrado);
        ItemEntretenimentoDto dto = objectMapper.convertValue(itemEntretenimentoEncontrado, ItemEntretenimentoDto.class);
        //   emailService.sendEmailItemEntretenimento(dto, TipoTemplate.UPDATE, usuarioDto);


        return dto;
    }

    public void delete(Integer id, Integer idAdmin) throws RegraDeNegocioException {
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class);
        if (usuarioDto.getTipoUsuario().equals(TipoUsuario.CLIENTE)) {
            throw new RegraDeNegocioException("Usuario precisa ser administrador para apagar um item.");
        }
        ItemEntretenimentoEntity itemEntretenimentoEntity = findById(id);
        ItemEntretenimentoDto itemDto = objectMapper.convertValue(itemEntretenimentoEntity, ItemEntretenimentoDto.class);
        //   emailService.sendEmailItemEntretenimento(itemDto, TipoTemplate.DELETE, usuarioDto);

        itemRepository.delete(itemEntretenimentoEntity);

    }

    public ItemEntretenimentoDto getItem(Integer id) throws RegraDeNegocioException {
        ItemEntretenimentoEntity itemPego = findById(id);

        return objectMapper.convertValue(itemPego, ItemEntretenimentoDto.class);
    }

    public ItemEntretenimentoEntity findById(Integer id) throws RegraDeNegocioException {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Item não encontrado!"));
    }

//    public Double calcularAvaliacao(Integer id) {
//        try {
//            return itemRepository.calcularAvaliacoes(id);
//        } catch (BancoDeDadosException ex) {
//            System.out.println("ERRO: " + ex.getMessage());
//        }
//        return null;
//    }
}
