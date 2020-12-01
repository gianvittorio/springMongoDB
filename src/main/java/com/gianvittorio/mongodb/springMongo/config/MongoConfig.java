package com.gianvittorio.mongodb.springMongo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
//@EnableMongoRepositories(basePackageClasses = {ProjectRepository.class, TaskRepository.class})
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${udemy.mongodb.replicaset.database}")
    private String database;
    @Value("${udemy.mongodb.replicaset.authMechanism}")
    private String authMechanism;
    @Value("${udemy.mongodb.replicaset.username}")
    private String replicasetUsername;
    @Value("${udemy.mongodb.replicaset.password}")
    private String replicasetPassword;

    @Value("${udemy.mongodb.replicaset.primary}")
    private String replicasetPrimary;

    @Value("${udemy.mongodb.replicaset.name}")
    private String replicasetName;

    @Value("${udemy.mongodb.replicaset.port}")
    private String replicasetPort;
    @Value("${udemy.mongodb.replicaset.authentication-database}")
    private String replicasetAuthenticationDb;
    @Value("${udemy.mongodb.replicaset.maxPoolSize}")
    private String replicasetMaxPoolSize;

    @Autowired
    private MappingMongoConverter mongoConverter;

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://" + replicasetUsername + ":" + replicasetPassword + "@" + replicasetPrimary + ":" + replicasetPort + "/" + database + "?replicaSet=" + replicasetName + "&authSource=" + replicasetAuthenticationDb + "&maxPoolSize=" + replicasetMaxPoolSize + "&authMechanism=" + authMechanism);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mongoConverter);
    }

}