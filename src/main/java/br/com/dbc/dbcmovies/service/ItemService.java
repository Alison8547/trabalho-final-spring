package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.*;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    public ItemEntretenimentoDto createFilme(ItemEntretenimentoCreateDto itemEntretenimentoDto) {

        ItemEntretenimentoEntity itemEntity = objectMapper.convertValue(itemEntretenimentoDto, ItemEntretenimentoEntity.class);

        return objectMapper.convertValue(itemRepository.save(itemEntity), ItemEntretenimentoDto.class);
    }

    public ItemEntretenimentoDto createSerie(ItemEntretenimentoCreateDto itemEntretenimentoDto) {

        ItemEntretenimentoEntity itemEntity = objectMapper.convertValue(itemEntretenimentoDto, ItemEntretenimentoEntity.class);

        return objectMapper.convertValue(itemRepository.save(itemEntity), ItemEntretenimentoDto.class);
    }

    public List<ItemEntretenimentoDto> list() throws RegraDeNegocioException {

        return itemRepository.findAll().stream()
                .map(itemEntretenimentoEntity -> objectMapper.convertValue(itemEntretenimentoEntity, ItemEntretenimentoDto.class))
                .toList();

    }

    public List<ItemEntretenimentoDto> filter(String tipo, String genero, Integer classificacao) {

        return itemRepository.filtrar(tipo.toUpperCase(), genero.toUpperCase(), classificacao).stream()
                .map(itemEntretenimentoEntity -> objectMapper.convertValue(itemEntretenimentoEntity, ItemEntretenimentoDto.class))
                .toList();
    }

    public ItemEntretenimentoDto updateFilme(Integer id, FilmeCreateDto filmeAtualizar) throws RegraDeNegocioException {

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


        return objectMapper.convertValue(itemEntretenimentoEncontrado, ItemEntretenimentoDto.class);
    }

    public ItemEntretenimentoDto updateSerie(Integer id, SerieCreateDto serieAtualizar) throws RegraDeNegocioException {

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

        return objectMapper.convertValue(itemEntretenimentoEncontrado, ItemEntretenimentoDto.class);
    }

    public void delete(Integer id) throws RegraDeNegocioException {

        ItemEntretenimentoEntity itemEntretenimentoEntity = findById(id);

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

    public PageDTO<ItemEntretenimentoDto> listaItemEntretenimentoPaginado(Integer pagina, Integer tamanho) {
        Sort ordenacao = Sort.by("nome");
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<ItemEntretenimentoEntity> paginaDoRepositorio = itemRepository.findAll(pageRequest);
        List<ItemEntretenimentoDto> pessoasDaPagina = paginaDoRepositorio.getContent().stream()
                .map(itemEntretenimentoEntity -> objectMapper.convertValue(itemEntretenimentoEntity, ItemEntretenimentoDto.class))
                .toList();
        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                pagina,
                tamanho,
                pessoasDaPagina
        );
    }
}
