package be.ing.fundtransfer.config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.cassandra.config.DataCenterReplication;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories("be.ing.fundtransfer.repository")
public class CassandraConfig extends AbstractCassandraConfiguration{
    @Autowired
    private Environment environment;

    @Bean
    @Override
    public CassandraCqlClusterFactoryBean cluster() {
        CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();
        bean.setKeyspaceCreations(getKeyspaceCreations());
        bean.setContactPoints(environment.getProperty("cassandra.contactpoints"));
        System.out.println(environment.getProperty("cassandra.user"));
        bean.setUsername(environment.getProperty("cassandra.user"));
       // bean.setPassword(environment.getProperty("cassandra.password"));
        return bean;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected String getKeyspaceName() {
        return environment.getProperty("cassandra.keyspace");
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"be.ing.fundtransfer.data"};
    }


    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        List<CreateKeyspaceSpecification> createKeyspaceSpecifications = new ArrayList<>();
        createKeyspaceSpecifications.add(getKeySpaceSpecification());
        return createKeyspaceSpecifications;
    }

    // Below method creates "my_keyspace" if it doesnt exist.
    private CreateKeyspaceSpecification getKeySpaceSpecification() {
        CreateKeyspaceSpecification pandaCoopKeyspace = new CreateKeyspaceSpecification();
        DataCenterReplication dcr = new DataCenterReplication("datacenter1", 3L);
        pandaCoopKeyspace.name(environment.getProperty("cassandra.keyspace"));
        pandaCoopKeyspace.ifNotExists(true).createKeyspace().withNetworkReplication(dcr);
        return pandaCoopKeyspace;
    }
}

