package br.com.dbc.dbcmovies.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ClassificaoDto {

    private Integer quantidade;
    @Field("_id")
    private Integer classificacao;
}
