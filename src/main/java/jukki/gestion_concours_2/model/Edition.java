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
public class Edition {

        @Id
        @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
        private String edition_id;
        @Field
        private String nom_edition;
        @Field
        private String date_debut;
        @Field
        private String date_fin;
        @Field
        private String date_fin_vote;

        @Field
        private Concours concours;
}
