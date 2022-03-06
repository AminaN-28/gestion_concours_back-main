package jukki.gestion_concours_2.model;

import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Data
public class Equipe {

    @Field
    private String email_equipe;
    @Field
    private String nom_equipe;

    // ...
}
