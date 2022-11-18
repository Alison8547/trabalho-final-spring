package br.com.dbc.dbcmovies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "locadora")
public class LocadoraEntity {
    @Id
    private String idLocadora;

    private Double preco;

    private Integer diasLocacao;

    private String nomeItem;

    private boolean disponibilidade;


}
