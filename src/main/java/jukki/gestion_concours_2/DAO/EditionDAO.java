package jukki.gestion_concours_2.DAO;

import jukki.gestion_concours_2.model.Edition;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@N1qlPrimaryIndexed
@Repository
public interface EditionDAO extends CouchbaseRepository<Edition, String> {

}