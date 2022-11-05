package br.com.dbc.dbcmovies.repository;


import br.com.dbc.dbcmovies.entity.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {

    @Query( "select a" +
            " from AVALIACAO a" +
            " join a.itemEntretenimento it")
    List<AvaliacaoEntity>findItemEntretenimento();

    @Query ("select a" +
            " from AVALIACAO a" +
            " join a.itemEntretenimento it" +
            " where (:idUsuario is null or a.avaliacaoPK.idUsuario = :idUsuario)")
    List<AvaliacaoEntity>findByIdEntretenimento(Integer idUsuario);


    @Query("select a" +
            " from AVALIACAO a" +
            " where a.avaliacaoPK.idUsuario = :idUsuario and a.avaliacaoPK.idItem = :idItem")
    AvaliacaoEntity pegar(Integer idUsuario, Integer idItem);


    @Query("select a" +
            " from AVALIACAO a" +
            " where a.avaliacaoPK.idUsuario = :idUsuario and a.avaliacaoPK.idItem = :idItem")
    AvaliacaoEntity remover(Integer idUsuario, Integer idItem);
}
