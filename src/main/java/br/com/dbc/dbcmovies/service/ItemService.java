package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.entity.Filtro;
import br.com.dbc.dbcmovies.entity.ItemEntretenimento;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemEntretenimento create(ItemEntretenimento itemEntretenimento) throws BancoDeDadosException {
        return itemRepository.adicionar(itemEntretenimento);
    }

    public List<ItemEntretenimento> list() throws BancoDeDadosException {
        return itemRepository.listar();
    }

    public List<ItemEntretenimento> filter(Filtro filtro) throws BancoDeDadosException {
        return itemRepository.filtrarItens(filtro);
    }

    public ItemEntretenimento update(Integer id, ItemEntretenimento itemEntretenimento) throws RegraDeNegocioException, BancoDeDadosException {
        findById(id);
        if(itemRepository.editar(id, itemEntretenimento)){
            return itemRepository.pegar(id);
        }else {
            throw new RegraDeNegocioException("Não foi possivel atualizar o item.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        findById(id);
        itemRepository.remover(id);
    }

    public ItemEntretenimento findById(Integer id) throws RegraDeNegocioException {
        try{
            return itemRepository.pegar(id);
        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Id não encontrado.");
        }
    }
}
