package jukki.gestion_concours_2.DAO;

import jukki.gestion_concours_2.model.Equipe;
import jukki.gestion_concours_2.model.Projet;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeDAO extends CouchbaseRepository<Projet, String> {
}
