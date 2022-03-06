package jukki.gestion_concours_2.model.Utils;

import com.couchbase.client.core.error.CollectionExistsException;
import com.couchbase.client.core.error.IndexExistsException;
import com.couchbase.client.core.error.ScopeExistsException;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.manager.collection.CollectionSpec;
import com.couchbase.client.java.query.QueryResult;
import jukki.gestion_concours_2.config.CouchbaseConfig;
import lombok.Data;

@Data
public class ScopeManager {

    private CouchbaseConfig couchbaseConfig = new CouchbaseConfig();

    public boolean createScope(String scopeName) {

        try {
            couchbaseConfig.getBucket().collections().createScope(scopeName);
            System.out.println("========"+scopeName+" SCOPE created ========");
            return true;
        } catch (ScopeExistsException see) {
            System.out.println("======== "+scopeName+" scope already exist ========");
            return false;
        }
    }

    public boolean createCollection(String scopeName, String collectionName) {
        try {
            couchbaseConfig.getBucket().collections().createCollection(CollectionSpec.create(collectionName, scopeName));
            System.out.println("======== '"+collectionName+"' collection created in "+scopeName+" SCOPE ========");
        } catch (CollectionExistsException cee) {
            System.out.println("======== "+collectionName+" COLLECTION already exist ========");
        } finally {
            try {
                String createIndexQuery = "CREATE PRIMARY INDEX "+collectionName+"_index ON "+couchbaseConfig.getBucketName()+"."+scopeName+"."+collectionName;
                System.out.println(createIndexQuery);
                couchbaseConfig.getCluster().query(createIndexQuery);
                return true;
            } catch (IndexExistsException e) {
                System.out.println("======== "+collectionName+"_index INDEX already exist ========");
                return false;
            }
        }
    }

    public MutationResult insertInCollection(String scopeName, String collectionName, String docId, Object object) {

        createScope(scopeName);
        createCollection(scopeName, collectionName);

        return couchbaseConfig.getBucket().scope(scopeName).collection(collectionName).insert(docId, object);
    }

    public QueryResult couchbaseFindQuery(String query) {
        return couchbaseConfig.getCluster().query(query);
    }
}
