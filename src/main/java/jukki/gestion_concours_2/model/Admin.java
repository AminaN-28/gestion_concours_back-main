package jukki.gestion_concours_2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String admin_id;
    @Field
    private String prenom;
    @Field
    private String nom;
    @Field
    private String login;
    @Field
    private String password;

    @Field
    private Edition edition;
    @Field
    private Concours concours;
}
