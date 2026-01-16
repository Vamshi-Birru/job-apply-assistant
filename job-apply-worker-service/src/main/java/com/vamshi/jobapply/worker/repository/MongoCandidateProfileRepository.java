package com.vamshi.jobapply.worker.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.vamshi.jobapply.worker.dto.CandidateProfile;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.ReplaceOptions.*;
import static org.bson.codecs.configuration.CodecRegistries.*;

public class MongoCandidateProfileRepository implements CandidateProfileRepository {

    private final MongoCollection<CandidateProfile> collection;

    public MongoCandidateProfileRepository(MongoClient client) {

        String dbName = System.getenv().getOrDefault("MONGODB_DB", "job-apply-automation");

        MongoDatabase database = client.getDatabase(dbName)
                .withCodecRegistry(
                        fromRegistries(
                                MongoClientSettings.getDefaultCodecRegistry(),
                                fromProviders(PojoCodecProvider.builder().automatic(true).build())
                        )
                );

        this.collection = database.getCollection(
                "candidate_profiles",
                CandidateProfile.class
        );
    }

    @Override
    public Optional<CandidateProfile> findByCandidateId(String candidateId) {
        return Optional.ofNullable(collection.find(eq("candidateId", candidateId)).first());
    }

    @Override
    public void save(CandidateProfile profile) {
        collection.replaceOne(
                eq("candidateId", profile.getCandidateId()),
                profile,
                new ReplaceOptions().upsert(true)
        );
    }

    @Override
    public void update(CandidateProfile profile) {

    }
}
