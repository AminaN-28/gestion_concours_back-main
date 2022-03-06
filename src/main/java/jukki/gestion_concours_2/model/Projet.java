package jukki.gestion_concours_2.model;

import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Data
public class Projet {

    @Field
    private String titre;
    @Field
    private String description;
    @Field
    private String objectif;
    @Field
    private String ise;
    @Field
    private boolean status;

    @Field
    private Equipe equipe;
    @Field
    private Thematique thematique;
    @Field
    private Edition edition;
}
