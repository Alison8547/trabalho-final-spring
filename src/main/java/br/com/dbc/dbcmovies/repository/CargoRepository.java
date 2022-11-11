package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity,Integer> {

}
