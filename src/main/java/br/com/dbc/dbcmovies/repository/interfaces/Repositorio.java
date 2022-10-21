package br.com.dbc.dbcmovies.repository.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<CHAVE, OBJETO> { //<Integer, Pessoa>
    Integer getProximoId(Connection connection) throws SQLException;

    OBJETO adicionar(OBJETO object) throws Exception;

    boolean remover(CHAVE id) throws Exception;

    boolean editar(CHAVE id, OBJETO objeto) throws Exception;

    List<OBJETO> listar() throws Exception;
}