package br.com.dbc.dbcmovies.repository;


import br.com.dbc.dbcmovies.dto.UsuarioAvaliacaoPersonalizadoDto;
import br.com.dbc.dbcmovies.dto.UsuarioItemPersonalizadoDto;
import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Integer> {

    UsuarioEntity findByEmailAndSenha(String email,String senha);

    Optional<UsuarioEntity> findByEmail(String email);

    @Query("select new br.com.dbc.dbcmovies.dto.UsuarioItemPersonalizadoDto(" +
            " u.idUsuario," +
            " u.nome," +
            " u.idade," +
            " u.email," +
            " f.nome," +
            " f.tipo," +
            " f.genero," +
            " f.sinopse," +
            " f.anoLancamento," +
            " f.classificacao)" +
            " from USUARIO u" +
            " join u.itemEntretenimentos f" +
            " where (:idUsuario is null or u.idUsuario = :idUsuario)")
    List<UsuarioItemPersonalizadoDto> listaPersonalizadaUsuarioItem (Integer idUsuario);

    @Query("select new br.com.dbc.dbcmovies.dto.UsuarioAvaliacaoPersonalizadoDto(" +
            " u.idUsuario," +
            " u.nome," +
            " u.idade," +
            " u.email," +
            " a.itemEntretenimento.nome," +
            " a.nota," +
            " a.comentario)" +
            " from USUARIO u" +
            " join u.avaliacaos a" +
            " where (:idUsuario is null or u.idUsuario = :idUsuario)")
    List<UsuarioAvaliacaoPersonalizadoDto> listaPersonalizadaUsuarioAvaliacao (Integer idUsuario);
}
