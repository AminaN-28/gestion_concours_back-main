package jukki.gestion_concours_2.model;

import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Data
public class Participant {

    @Field
    private String prenom;
    @Field
    private String nom;
    @Field
    private String profile;
    @Field
    private String genre;
    @Field
    private String niveau;
    @Field
    private String etablissement;
    @Field
    private String email_participant;

    @Field
    private Equipe equipe;
    @Field
    private Edition edition;
}
