package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntretenimentoEntity,Integer> {

    @Query(" select i from ITEM_ENTRETENIMENTO i " +
            "WHERE UPPER(i.tipo) = :tipo  and UPPER(i.genero) = :genero and i.classificacao = :classificacao")
    List<ItemEntretenimentoEntity> filtrar(String tipo,String genero,Integer classificacao);

    List<ItemEntretenimentoEntity>findByDisponibilidade(Integer disponibilidade);
}
