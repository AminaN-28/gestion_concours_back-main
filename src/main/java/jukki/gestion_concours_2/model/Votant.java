package jukki.gestion_concours_2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Votant {

    @Field
    private String email;
    @Field
    private int otpCode;

    @Field
    private Projet projet;

    public Votant(String email, int code) {
        this.email = email;
        this.otpCode = code;
    }
}
