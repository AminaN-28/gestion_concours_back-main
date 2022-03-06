package jukki.gestion_concours_2.controller;

import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;
import jukki.gestion_concours_2.model.Participant;
import jukki.gestion_concours_2.model.Utils.ScopeManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParticipantController {

    ScopeManager scopeManage = new ScopeManager();

    // Add participant
    @PostMapping(value = "/participants/add/{scopeName}")
    public ResponseEntity<Void> add(@RequestBody Participant participant, @PathVariable String scopeName) {
        MutationResult result = scopeManage.insertInCollection(scopeName,"participants", participant.getEmail_participant(), participant);
        System.out.println("ADD PAPRTICIPANT");

        return ResponseEntity.ok().build();
    }

    // Get all
    @GetMapping(value = "/participants/{scopeName}/{editionName}")
    public List<JsonObject> getAllEdition(@PathVariable String scopeName, @PathVariable String editionName) {
        QueryResult result = scopeManage.couchbaseFindQuery("SELECT prenom, nom, profile, genre, niveau, email_participant, p.equipe.nom_equipe FROM concoursBucket."+scopeName+".participants p WHERE p.edition.nom_edition = "+editionName);
        return result.rowsAsObject();
    }

}
