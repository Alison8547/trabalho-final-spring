package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.Dto.FilmeCreateDto;
import br.com.dbc.dbcmovies.Dto.ItemEntretenimentoCreateDto;
import br.com.dbc.dbcmovies.Dto.ItemEntretenimentoDto;
import br.com.dbc.dbcmovies.Dto.SerieCreateDto;
import br.com.dbc.dbcmovies.entity.Filtro;
import br.com.dbc.dbcmovies.entity.ItemEntretenimento;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
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
    private final ObjectMapper objectMapper;

    public ItemEntretenimentoDto createFilme(ItemEntretenimentoCreateDto itemEntretenimentoDto) throws BancoDeDadosException {

        ItemEntretenimento itemEntity = objectMapper.convertValue(itemEntretenimentoDto, ItemEntretenimento.class);
        itemEntity = itemRepository.adicionar(itemEntity);
        return objectMapper.convertValue(itemEntity, ItemEntretenimentoDto.class);
    }

    public ItemEntretenimentoDto createSerie(ItemEntretenimentoCreateDto itemEntretenimentoDto) throws BancoDeDadosException {

        ItemEntretenimento itemEntity = objectMapper.convertValue(itemEntretenimentoDto, ItemEntretenimento.class);
        itemEntity = itemRepository.adicionar(itemEntity);
        return objectMapper.convertValue(itemEntity, ItemEntretenimentoDto.class);
    }

    public List<ItemEntretenimentoDto> list() throws BancoDeDadosException {
        List<ItemEntretenimento> resultList = itemRepository.listar();
        resultList.forEach(item -> {
            double media = calcularAvaliacao(item.getId());
            if(media == 0){
                item.setMediaAvaliacoes(null);
            }else {
                item.setMediaAvaliacoes(media);
            }
        });

        return resultList.stream()
                .map(item -> objectMapper.convertValue(item, ItemEntretenimentoDto.class))
                .toList();
    }

    public List<ItemEntretenimentoDto> filter(Filtro filtro) throws BancoDeDadosException {
        List<ItemEntretenimento> resultList = itemRepository.filtrarItens(filtro);
        resultList.forEach(item -> {
            double media = calcularAvaliacao(item.getId());
            if(media == 0){
                item.setMediaAvaliacoes(null);
            }else {
                item.setMediaAvaliacoes(media);
            }
        });

        return resultList.stream().map(item -> objectMapper.convertValue(item, ItemEntretenimentoDto.class)).toList();
    }

    public ItemEntretenimentoDto updateFilme(Integer id, FilmeCreateDto filmeCreateDto) throws RegraDeNegocioException, BancoDeDadosException {
        findById(id);
        ItemEntretenimento itemEntretenimento = objectMapper.convertValue(filmeCreateDto, ItemEntretenimento.class);
        if(itemRepository.editar(id, itemEntretenimento)){
            return objectMapper.convertValue(itemRepository.pegar(id), ItemEntretenimentoDto.class);
        }else {
            throw new RegraDeNegocioException("Não foi possivel atualizar o item.");
        }
    }

    public ItemEntretenimentoDto updateSerie(Integer id, SerieCreateDto serieCreateDto) throws RegraDeNegocioException, BancoDeDadosException {
        findById(id);
        ItemEntretenimento itemEntretenimento = objectMapper.convertValue(serieCreateDto, ItemEntretenimento.class);
        if(itemRepository.editar(id, itemEntretenimento)){
            return objectMapper.convertValue(itemRepository.pegar(id), ItemEntretenimentoDto.class);
        }else {
            throw new RegraDeNegocioException("Não foi possivel atualizar o item.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        findById(id);
        itemRepository.remover(id);
    }

    public ItemEntretenimentoDto getItem(Integer id) throws RegraDeNegocioException {
        try{
            return objectMapper.convertValue(itemRepository.pegar(id), ItemEntretenimentoDto.class);
        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Id não encontrado.");
        }
    }

    public ItemEntretenimento findById(Integer id) throws RegraDeNegocioException {
        try{
            return itemRepository.pegar(id);
        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Id não encontrado.");
        }
    }

    public Double calcularAvaliacao(Integer id){
        try{
            return itemRepository.calcularAvaliacoes(id);
        } catch (BancoDeDadosException ex) {
            System.out.println("ERRO: "+ex.getMessage());
        }
        return null;
    }
}
