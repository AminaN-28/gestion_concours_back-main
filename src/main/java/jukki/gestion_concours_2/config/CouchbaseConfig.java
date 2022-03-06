package jukki.gestion_concours_2.config;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
@ConfigurationProperties(prefix = "couchbase")
@Data
@NoArgsConstructor
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    Cluster cluster = Cluster.connect(this.getConnectionString(), this.getUserName(), this.getPassword());
    Bucket bucket = cluster.bucket(this.getBucketName());


    @Override
    public String getConnectionString() {
        return "couchbase://34.227.13.187";
    }

    @Override
    public String getUserName() {
        return "Bab4car";
    }

    @Override
    public String getPassword() {
        return "rootroot";
    }

    @Override
    public String getBucketName() {
        return "concoursBucket";
    }

}
