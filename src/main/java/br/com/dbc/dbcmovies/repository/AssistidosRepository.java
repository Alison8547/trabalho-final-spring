package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistidosRepository extends JpaRepository<ItemEntretenimentoEntity, Integer> {

    @Query(value = "  select count (id_usuario)        " +
            "  from EDUARDO_SEDREZ.assistidos a " +
            "  where a.id_usuario = ?1          ", nativeQuery = true)
    Integer verificarUsuarioNaTabela(Integer idUsuario);
}
