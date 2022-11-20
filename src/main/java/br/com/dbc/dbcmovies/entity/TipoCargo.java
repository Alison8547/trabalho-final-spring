package br.com.dbc.dbcmovies.entity;

public enum TipoCargo {
    ADMIN(1), CLIENTE(2), RECUPERACAO(3);

    private int cargo;
    TipoCargo(int role) {
        this.cargo = role;
    }

    public int getCargo() {
        return cargo;
    }
}
