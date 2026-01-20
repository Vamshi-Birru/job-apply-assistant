package com.vamshi.jobapply.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vamshi.jobapply.dtos.Application;
import com.vamshi.jobapply.enums.ApplicationStatus;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoApplicationRepository implements ApplicationRepository {

    private final MongoCollection<Application> collection;

    public MongoApplicationRepository(MongoClient client){
        MongoDatabase db = client.getDatabase(
                System.getenv().getOrDefault("MONGO_DB", "job-apply-automation")
        ).withCodecRegistry(
                fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build())
                )
        );

        collection = db.getCollection("applications", Application.class);
    }
    @Override
    public void create(Application application) {
            collection.insertOne(application);
    }

    @Override
    public Optional<Application> findById(String applicationId) {
        return Optional.ofNullable(collection.find(eq("applicationId",applicationId)).first());
    }

    @Override
    public List<Application> findPendingApplications(Instant now) {
        return collection.find(
                and(
                        eq("status", ApplicationStatus.PENDING),
                        lte("nextRetryAt", now)
                )
        ).into(new ArrayList<>());
    }

    @Override
    public void markRunning(String applicationId) {

    }

    @Override
    public void markCompleted(String applicationId) {

    }

    @Override
    public void markFailed(String applicationId) {

    }

    @Override
    public void updateMissing(String applicationId, String questionHash, String questionText, Instant nextRetry) {

    }
}
