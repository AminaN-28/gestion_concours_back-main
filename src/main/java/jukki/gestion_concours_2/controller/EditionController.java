package jukki.gestion_concours_2.controller;

import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;
import jukki.gestion_concours_2.DAO.EditionDAO;
import jukki.gestion_concours_2.model.Edition;
import jukki.gestion_concours_2.model.Utils.ScopeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EditionController {

    ScopeManager scopeManage = new ScopeManager();

    // Add edition
    @PostMapping(value = "/editions/add")
    public ResponseEntity<Void> add(@RequestBody Edition edition) {
        System.out.println("ALIAS CONCOURS : "+edition.getConcours().getAlias_concours());
        System.out.println("EDITION NAME : "+edition.getNom_edition());
        MutationResult result = scopeManage.insertInCollection(edition.getConcours().getAlias_concours(),"editions", edition.getNom_edition(), edition);
        System.out.println(result);

        return ResponseEntity.ok().build();
   }

    // Get all
    @GetMapping(value = "/editions/{aliasConcours}")
    public List<JsonObject> getAllEdition(@PathVariable String aliasConcours) {
        String query = "SELECT nom_edition, date_debut, date_fin, date_fin_vote, status FROM concoursBucket."+aliasConcours+".editions";
        System.out.println("REQUEST "+query);
        QueryResult result = scopeManage.couchbaseFindQuery(query);
        return result.rowsAsObject();
    }

}
