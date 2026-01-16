package com.vamshi.jobapply.worker.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.vamshi.jobapply.worker.config.MongoConfig;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MongoClientProvider {
    private static MongoClient mongoClient;

    public static MongoClient getClient(MongoConfig config){
        if(mongoClient==null){
            synchronized (MongoClientProvider.class){
                if(mongoClient==null){
                    mongoClient = MongoClients.create(config.getConnectionUri());
                }
            }
        }
        return mongoClient;
    }
}
