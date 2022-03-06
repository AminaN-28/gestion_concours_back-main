package jukki.gestion_concours_2.DAO;

import jukki.gestion_concours_2.model.Thematique;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThematiqueDAO extends CouchbaseRepository<Thematique, String> {
}
