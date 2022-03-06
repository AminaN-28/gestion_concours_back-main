package jukki.gestion_concours_2.controller;

import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;
import jukki.gestion_concours_2.model.Edition;
import jukki.gestion_concours_2.model.Thematique;
import jukki.gestion_concours_2.model.Utils.ScopeManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ThematiqueController {

    ScopeManager scopeManage = new ScopeManager();

    // Add edition
    @PostMapping(value = "/thematiques/add/{scopeName}")
    public ResponseEntity<Void> add(@RequestBody Thematique thematique, @PathVariable String scopeName) {
        MutationResult result = scopeManage.insertInCollection(scopeName,"thematiques", thematique.getNom_thematique()+thematique.getEdition().getNom_edition(), thematique);
        System.out.println(result);

        return ResponseEntity.ok().build();
    }

    // Get all
    @GetMapping(value = "/thematiques/{aliasConcours}")
    public List<JsonObject> getAllEdition(@PathVariable String aliasConcours) {
        QueryResult result = scopeManage.couchbaseFindQuery("SELECT th.nom_thematique, th.edition.nom_edition  FROM concoursBucket."+aliasConcours+".thematiques th");
        return result.rowsAsObject();
    }

    // Get all
    @GetMapping(value = "/thematiques/{aliasConcours}/{editionName}")
    public List<JsonObject> getAllEdition(@PathVariable String aliasConcours, @PathVariable String editionName) {
        String query = "SELECT nom_thematique, edition.nom_edition FROM concoursBucket."+aliasConcours+".thematiques t WHERE t.edition.nom_edition = '"+editionName+"'";
        System.out.println("REQUEST "+query);
        QueryResult result = scopeManage.couchbaseFindQuery(query);
        return result.rowsAsObject();
    }
}
