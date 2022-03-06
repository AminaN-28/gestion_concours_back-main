package jukki.gestion_concours_2.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {

    private String to;
    private String body;
    private String topic;
}
