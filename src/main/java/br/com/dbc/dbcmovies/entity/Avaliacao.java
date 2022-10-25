package br.com.dbc.dbcmovies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class Avaliacao{

    @NotNull
    private Usuario usuario;
    @NotNull
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
