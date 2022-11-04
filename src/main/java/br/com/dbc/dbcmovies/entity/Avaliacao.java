package br.com.dbc.dbcmovies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Avaliacao{


    private Usuario usuario;

    private ItemEntretenimentoEntity itemEntretenimentoEntity;
    private Double nota;
    private String comentario;


    //Construtor
    public Avaliacao() {
    }

    public Avaliacao(Double nota, String comentario) {
        this.nota = nota;
        this.comentario = comentario;
    }


    public void setItemEntretenimentoEntity(ItemEntretenimentoEntity itemEntretenimentoEntity) {
        this.itemEntretenimentoEntity = itemEntretenimentoEntity;
    }


}
