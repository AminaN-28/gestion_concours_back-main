package jukki.gestion_concours_2.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Data
@ToString
public class Coach {

    @Field
    private String prenom;
    @Field
    private String nom;
    @Field
    private String genre;
    @Field
    private String email;
    @Field
    private String specialite;
    @Field
    private double note;
}
