package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.LancamentosEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LancamentosRepository extends MongoRepository<LancamentosEntity, Integer> {

}
