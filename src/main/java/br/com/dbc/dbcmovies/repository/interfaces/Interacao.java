package br.com.dbc.dbcmovies.repository.interfaces;

import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;

public interface Interacao {
    public boolean marcarAssistido(Integer idItem, Integer idUsuario) throws BancoDeDadosException;
}
