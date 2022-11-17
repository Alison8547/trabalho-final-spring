package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.LancamentosEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentosRepository extends MongoRepository<LancamentosEntity, String> {


}
