package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.LocadoraEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocadoraRepository extends MongoRepository<LocadoraEntity,String> {
}
