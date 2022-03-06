package jukki.gestion_concours_2.controller;

import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;
import jukki.gestion_concours_2.model.Coach;
import jukki.gestion_concours_2.model.Projet;
import jukki.gestion_concours_2.model.Utils.ScopeManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjetController {

    ScopeManager scopeManage = new ScopeManager();

    // Add projet
    @PostMapping(value = "/projets/add/{scopeName}")
    public ResponseEntity<Void> add(@RequestBody Projet projet, @PathVariable String scopeName) {
        MutationResult result = scopeManage.insertInCollection(scopeName, "projets", projet.getEquipe().getNom_equipe(), projet);
        System.out.println(result);

        return ResponseEntity.ok().build();
    }

    // Get all
    @GetMapping(value = "/projets")
    public List<JsonObject> getAllEdition() {
        QueryResult result = scopeManage.couchbaseFindQuery("SELECT titre, objectif, description, ise, p.equipe  FROM concoursBucket.GAIDEI2000.projets p");
        return result.rowsAsObject();
    }

    // Get all
    @GetMapping(value = "/projets/{aliasConcours}/{editionName}")
    public List<JsonObject> getAllEdition(@PathVariable String aliasConcours, @PathVariable String editionName) {
        String query = "SELECT titre, objectif, description, ise, status, p.equipe, p.thematique.nom_thematique FROM concoursBucket." + aliasConcours + ".projets p WHERE p.edition.nom_edition = '" + editionName + "'";
        System.out.println("REQUEST " + query);
        QueryResult result = scopeManage.couchbaseFindQuery(query);
        return result.rowsAsObject();
    }

    // Get all preselected
    @GetMapping(value = "/projets/list_peselectionner/{aliasConcours}/{editionName}")
    public List<JsonObject> getAllProjetPreselectionned(@PathVariable String aliasConcours, @PathVariable String editionName) {
        String query = "SELECT titre, objectif, description, ise, status, p.equipe, p.thematique.nom_thematique FROM concoursBucket." + aliasConcours + ".projets p WHERE p.edition.nom_edition = '" + editionName + "' AND p.status = true";
        System.out.println("REQUEST " + query);
        QueryResult result = scopeManage.couchbaseFindQuery(query);
        return result.rowsAsObject();
    }

    // Get by thematique
    @GetMapping(value = "/projets/thematique/{aliasConcours}/{editionName}/{thematique}")
    public List<JsonObject> getByThematique(@PathVariable String aliasConcours, @PathVariable String editionName, @PathVariable String thematique) {
        String query = "SELECT * FROM concoursBucket."+aliasConcours+".projets WHERE thematique.nom_thematique = '"+thematique+"' and edition.nom_edition = '"+editionName+"'";
        System.out.println("REQUEST " + query);
        QueryResult result = scopeManage.couchbaseFindQuery(query);
        return result.rowsAsObject();
    }

    // Get by thematique
    @PutMapping(value = "/projets/preselectionner/{aliasConcours}/{editionName}/{titre}")
    public List<JsonObject> preselectionner(@RequestBody Object object, @PathVariable String aliasConcours, @PathVariable String editionName, @PathVariable String titre) {
        String query = "UPDATE concoursBucket."+aliasConcours+".projets p\n" +
                "SET status = TRUE " +
                "WHERE p.edition.nom_edition ='"+editionName+"' "+
                "AND p.titre LIKE '"+titre+"'";

        System.out.println("REQUEST " + query);
        QueryResult result = scopeManage.couchbaseFindQuery(query);

        scopeManage.insertInCollection(aliasConcours, "evaluations", titre+"_"+editionName, object);

        return result.rowsAsObject();
    }

    // Note coash
    @PutMapping(value = "/projets/noter/{aliasConcours}/{editionName}/{equipe}/{note1}/{note2}")
    public void saveNote(@PathVariable double note1, @PathVariable double note2, @PathVariable String aliasConcours, @PathVariable String editionName, @PathVariable String equipe) {

        String query = "update concoursBucket."+aliasConcours+".evaluations evaluations set coachs[0].note = "+note1+", coachs[1].note = "+note2+" where evaluations.edition.nom_edition = '"+editionName+"' AND evaluations.nom_equipe = '"+equipe+"'";
        System.out.println("REQUEST " + query);
        QueryResult result = scopeManage.couchbaseFindQuery(query);
    }

}