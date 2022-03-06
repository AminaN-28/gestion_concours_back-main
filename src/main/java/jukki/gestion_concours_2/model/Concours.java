package jukki.gestion_concours_2.model;

import com.couchbase.client.core.deps.com.fasterxml.jackson.core.JsonGenerator;
import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.JsonSerializer;
import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Concours {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String concours_id;
    @Field
    private String nom_concours;
    @Field
    private String alias_concours;
}