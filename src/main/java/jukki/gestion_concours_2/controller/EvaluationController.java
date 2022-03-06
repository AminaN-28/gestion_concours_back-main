package jukki.gestion_concours_2.controller;

import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;
import jukki.gestion_concours_2.model.Evaluation;
import jukki.gestion_concours_2.model.Participant;
import jukki.gestion_concours_2.model.Projet;
import jukki.gestion_concours_2.model.Utils.ScopeManager;
import jukki.gestion_concours_2.model.Votant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EvaluationController {


    ScopeManager scopeManage = new ScopeManager();

    // Add evaluation
    @PostMapping(value = "/evaluations/add/{scopeName}")
    public ResponseEntity<Void> add(@RequestBody Evaluation evaluation, @PathVariable String scopeName) {
        MutationResult result = scopeManage.insertInCollection(scopeName,"evaluations", evaluation.getNom_equipe()+evaluation.getNom_thematique()+evaluation.getEdition().getNom_edition(), evaluation);
        System.out.println(result);

        return ResponseEntity.ok().build();
    }

    // Get all
    @GetMapping(value = "/evaluations")
    public List<JsonObject> getAllEdition() {
        QueryResult result = scopeManage.couchbaseFindQuery("SELECT nom_thematique, nom_equipe, e.coachs, nbr_vote  FROM concoursBucket.GAIDEI2000.evaluations e");
        return result.rowsAsObject();
    }

    // Get all
    @GetMapping(value = "/evaluations/general/{scopeName}/{editionName}")
    public List<JsonObject> getGeneralEvaluation(@PathVariable String scopeName, @PathVariable String editionName) {
        QueryResult result = scopeManage.couchbaseFindQuery("SELECT SUM(ADD(c.coachs[0].note,c.coachs[1].note))*10*25/100 + c.nbr_vote*75/100 AS note_final, c.nom_equipe, c.nom_thematique FROM concoursBucket."+scopeName+".evaluations c WHERE c.edition.nom_edition = '"+editionName+"' GROUP BY c.nom_equipe, c.nbr_vote, c.nom_thematique ORDER BY note_final DESC");
        return result.rowsAsObject();
    }

    // Voter
    @PutMapping(value = "/vote/enregistrer/{concours}/{edition}/{nomEquipe}")
    public ResponseEntity<Void> voter(@RequestBody Votant votant, @PathVariable String concours, @PathVariable String edition, @PathVariable String nomEquipe) {

        String query = "update concoursBucket."+concours+".evaluations evaluations set nbr_vote = nbr_vote+1 WHERE evaluations.edition.nom_edition = '"+edition+"' AND evaluations.nom_equipe = '"+nomEquipe+"'";
        System.out.println(query);
        QueryResult result = scopeManage.couchbaseFindQuery(query);

        saveInDB(votant, concours);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> saveInDB(@RequestBody Votant votant, @PathVariable String concours) {
        System.out.println(votant.toString());
        MutationResult result = scopeManage.insertInCollection(concours,"votants", votant.getEmail(), votant);
        return ResponseEntity.ok().build();
    }
}