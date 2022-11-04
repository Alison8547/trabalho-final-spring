package br.com.dbc.dbcmovies.repository;


import br.com.dbc.dbcmovies.entity.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {

}
