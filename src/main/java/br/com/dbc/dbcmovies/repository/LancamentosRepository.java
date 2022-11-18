package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.LancamentosEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LancamentosRepository extends MongoRepository<LancamentosEntity, String> {

    List<LancamentosEntity> findAllByDataLancamentoContains(String data);
}
