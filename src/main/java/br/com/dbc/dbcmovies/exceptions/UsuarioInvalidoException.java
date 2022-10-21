package br.com.dbc.dbcmovies.exceptions;

public class UsuarioInvalidoException extends RuntimeException{

    public UsuarioInvalidoException(String message) {
        super(message);
    }
}
