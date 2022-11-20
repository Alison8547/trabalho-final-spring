package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.dto.LocadoraDto;
import br.com.dbc.dbcmovies.dto.PrecoDto;
import br.com.dbc.dbcmovies.entity.LocadoraEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocadoraRepository extends MongoRepository<LocadoraEntity, String> {

    List<LocadoraDto> findByPreco(Double preco);

    @Aggregation(pipeline = {
            "{'$group': {'_id':  '$preco', 'quantidade' : {'$sum': 1}}}"
    })
    List<PrecoDto> listQuantidadePreco();

}
