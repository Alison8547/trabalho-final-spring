package br.com.dbc.dbcmovies.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class PrecoDto {

    private Integer quantidade;
    @Field("_id")
    private Double preco;
}
