package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.IndicacaoEntity;
import br.com.dbc.dbcmovies.entity.pk.IndicacaoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndicacaoRepository extends JpaRepository<IndicacaoEntity, IndicacaoPK> {

}
