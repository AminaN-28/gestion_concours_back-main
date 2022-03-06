package jukki.gestion_concours_2.DAO;

import jukki.gestion_concours_2.model.Admin;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@N1qlPrimaryIndexed
public interface AdminDAO extends CouchbaseRepository<Admin, String> {
}
