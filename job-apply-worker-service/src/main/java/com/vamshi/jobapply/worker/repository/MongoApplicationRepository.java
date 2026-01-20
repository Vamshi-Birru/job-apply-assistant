package com.vamshi.jobapply.worker.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vamshi.jobapply.worker.dto.Application;
import com.vamshi.jobapply.worker.enums.ApplicationStatus;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoApplicationRepository implements ApplicationRepository{
    private final MongoCollection<Application> collection;
    public MongoApplicationRepository(MongoClient client){
        MongoDatabase db = client.getDatabase("job-apply-automation")
                .withCodecRegistry(fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build())
                ));
        this.collection = db.getCollection("applications", Application.class);
    }
    @Override
    public void create(Application application) {
        application.setCreatedAt(Instant.now());
        application.setUpdateAt(Instant.now());
        collection.insertOne(application);
    }

    @Override
    public Optional<Application> findById(String id) {
        return Optional.ofNullable(collection.find(eq("_id",id)).first());
    }

    @Override
    public List<Application> findPendingEligible(Instant now, int limit) {
        return collection.find(and(
                eq("status", ApplicationStatus.PENDING),
                lte("nextRetryAt", now),
                or(
                        exists("lockUntil",false),
                        lt("locksUntil", now)
                )
        ))
                .limit(limit)
                .into(new java.util.ArrayList<>());
    }

    @Override
    public boolean acquireLock(String applicationId, String workerId, Instant lockUntil) {
        return collection.updateOne(
                and(
                        eq("_id",applicationId),
                        or(
                                exists("lockUntil",false),
                                lt("lockUntil",Instant.now())
                        )
                ),
                combine(
                        set("lockedBy", workerId),
                        set("lockUntil", lockUntil),
                        set("updatedAt", Instant.now())
                )
        ).getModifiedCount()==1;
    }

    @Override
    public void markCompleted(String applicationId) {
        collection.updateOne(
                eq("_id", applicationId),
                combine(
                        set("status", ApplicationStatus.COMPLETED),
                        unset("missingQuestionHash"),
                        unset("lockedBy"),
                        unset("lockUntil"),
                        set("updatedAt", Instant.now())
                )
        );
    }

    @Override
    public void markFailed(String applicationId) {
        collection.updateOne(
                eq("_id", applicationId),
                combine(
                        set("status", ApplicationStatus.FAILED),
                        set("updatedAt", Instant.now())
                )
        );
    }

    @Override
    public void updateMissing(String applicationId, String missingQuestionHash, Instant nextRetryAt, int retryCount) {
        collection.updateOne(
                eq("_id",applicationId),
                combine(
                        set("missingQuestHash", missingQuestionHash),
                        set("nextRetryAt", nextRetryAt),
                        set("retryCount", retryCount),
                        set("updatedAt", Instant.now())
                )
        );
    }
}
