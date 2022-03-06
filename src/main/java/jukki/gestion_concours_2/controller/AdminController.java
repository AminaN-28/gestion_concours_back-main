package jukki.gestion_concours_2.controller;

import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;
import jukki.gestion_concours_2.DAO.AdminDAO;
import jukki.gestion_concours_2.model.Admin;
import jukki.gestion_concours_2.model.Utils.ScopeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    ScopeManager scopeManager = new ScopeManager();
    @Autowired
    private AdminDAO adminDAO;

    // Add admins
    @PostMapping(value = "/admins/add")
    public ResponseEntity<Void> add(@RequestBody Admin admin) {

        MutationResult result = scopeManager.insertInCollection("GLOBAL","admins",admin.getLogin(),admin);
        return ResponseEntity.ok().build();
    }

    // Add admins
    @PostMapping(value = "/admins/login")
    public List<JsonObject> login(@RequestBody Admin admin) {

        QueryResult result = scopeManager.couchbaseFindQuery("SELECT prenom, nom, login, edition.nom_edition, concours.alias_concours, concours.nom_concours FROM concoursBucket.GLOBAL.admins WHERE login = '"+admin.getLogin()+"' AND `password` = '"+admin.getPassword()+"'");
        return result.rowsAsObject();
    }

    // Get admins
    @GetMapping(value = "/admins")
    public List<JsonObject> getAdmin() {
        QueryResult result = scopeManager.couchbaseFindQuery("SELECT prenom, nom, login, edition.nom_edition, concours.alias_concours, concours.nom_concours FROM concoursBucket.GLOBAL.admins");
        return result.rowsAsObject();
    }

    // Get statistique in genre
    @GetMapping(value = "/statistique_genre/{concours}/{edition}")
    public List<JsonObject> getAdmin(@PathVariable String concours, @PathVariable String edition) {
        String query = "SELECT p.genre,count(p.genre) AS nombre FROM concoursBucket."+concours+".participants p WHERE p.edition.nom_edition = '"+edition+"' GROUP BY p.genre";
        QueryResult result = scopeManager.couchbaseFindQuery(query);
        return result.rowsAsObject();
    }

    // Get statistique number
    @GetMapping(value = "/statistique_number/{concours}/{edition}")
    public List<JsonObject> getNumberStatistique(@PathVariable String concours, @PathVariable String edition) {

        try{

            String indexAdvisor = "CREATE INDEX adv_ALL_coach_edition_nom_edition_nbr_vote ON `default`:`concoursBucket`."+concours+".`evaluations`(ALL `coach`,`edition`.`nom_edition`,`nbr_vote`)";
            String indexAdvisor2 = "CREATE INDEX adv_edition_nom_edition ON `default`:`concoursBucket`."+concours+".`projets`(`edition`.`nom_edition`)";
            String indexAdvisor3 = "CREATE INDEX adv_ALL_coach ON `default`:`concoursBucket`."+concours+".`evaluations`(ALL `coach`)";
            String indexAdvisor4 = "CREATE INDEX adv_edition_nom_edition ON `default`:`concoursBucket`."+concours+".`participants`(`edition`.`nom_edition`)";

            scopeManager.couchbaseFindQuery(indexAdvisor);
            scopeManager.couchbaseFindQuery(indexAdvisor2);
            scopeManager.couchbaseFindQuery(indexAdvisor3);
            scopeManager.couchbaseFindQuery(indexAdvisor4);
        } catch (Exception e) { }

        String query = "SELECT \n" +
                "       SUM(DISTINCT(e.nbr_vote)) AS nb_votants ,\n" +
                "       COUNT(DISTINCT(p)) AS nb_participants,\n" +
                "       COUNT(DISTINCT(pr)) AS nb_projets,\n" +
                "       COUNT(DISTINCT(pj)) AS nb_projets_preselectionne\n" +
                "FROM concoursBucket."+concours+".evaluations e\n" +
                "    JOIN concoursBucket."+concours+".participants p ON p.edition.nom_edition = e.edition.nom_edition\n" +
                "    JOIN concoursBucket."+concours+".projets pr ON pr.edition.nom_edition = p.edition.nom_edition\n" +
                "    JOIN concoursBucket."+concours+".projets pj ON pr.edition.nom_edition = pj.edition.nom_edition\n" +
                "WHERE pr.edition.nom_edition = '"+edition+"'" +
                "    AND pj.status = TRUE";

        QueryResult result = scopeManager.couchbaseFindQuery(query);
        return result.rowsAsObject();
    }


}
