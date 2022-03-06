package jukki.gestion_concours_2.controller;

import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;
import jukki.gestion_concours_2.DAO.ConcoursDAO;
import jukki.gestion_concours_2.model.Concours;
import jukki.gestion_concours_2.model.Utils.ScopeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ConcoursController {

    @Autowired
    private ConcoursDAO concoursDAO;

    ScopeManager scopeManager = new ScopeManager();

    // Add concours
    @PostMapping(value = "/concours/add")
    public ResponseEntity<Void> add(@RequestBody Concours concours) {
        scopeManager.createScope(concours.getAlias_concours());
        MutationResult result = scopeManager.insertInCollection("GLOBAL","concours", concours.getAlias_concours(), concours);
        System.out.println(result);

        return ResponseEntity.ok().build();
    }

    // Get all
    @GetMapping(value = "/concours/{aliasConcours}")
    public List<JsonObject> getAllEdition(@PathVariable String aliasConcours) {
        QueryResult result = scopeManager.couchbaseFindQuery("SELECT * FROM concoursBucket.GLOBAL.concours USE KEYS '"+aliasConcours+"'");
        return result.rowsAsObject();
    }

    // Get all
    @GetMapping(value = "/concours")
    public List<JsonObject> getAllEdition() {
        QueryResult result = scopeManager.couchbaseFindQuery("SELECT * FROM concoursBucket.GLOBAL.concours");
        return result.rowsAsObject();
    }
}
