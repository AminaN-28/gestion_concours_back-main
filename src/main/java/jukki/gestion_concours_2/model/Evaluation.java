package jukki.gestion_concours_2.model;

import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.List;

@Document
@Data
public class Evaluation {

    @Field
    private String nom_equipe;
    @Field
    private String nom_thematique;
    @Field
    private int nbr_vote;

    @Field
    private Edition edition;

    @Field
    private List<Coach> coachs;
}
