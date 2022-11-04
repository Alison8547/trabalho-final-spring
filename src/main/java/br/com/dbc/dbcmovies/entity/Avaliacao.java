package br.com.dbc.dbcmovies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Avaliacao{


    private UsuarioEntity usuarioEntity;

    private ItemEntretenimento itemEntretenimento;
    private Double nota;
    private String comentario;


    //Construtor
    public Avaliacao() {
    }

    public Avaliacao(Double nota, String comentario) {
        this.nota = nota;
        this.comentario = comentario;
    }


    public void setItemEntretenimento(ItemEntretenimento itemEntretenimento) {
        this.itemEntretenimento = itemEntretenimento;
    }


}
