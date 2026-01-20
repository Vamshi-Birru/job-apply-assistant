package com.vamshi.jobapply.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.vamshi.jobapply.models.CandidateProfile;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoCandidateProfileRepository implements CandidateProfileRepository{

    private final MongoCollection<CandidateProfile> collection;
    public MongoCandidateProfileRepository(MongoClient client){
        MongoDatabase db = client.getDatabase(
                System.getenv().getOrDefault("MONGO_DB", "job-apply-automation")
        ).withCodecRegistry(
                fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build())
                )
        );

        collection = db.getCollection("candidate_profiles", CandidateProfile.class);
    }
    @Override
    public Optional<CandidateProfile> findByCandidateId(String candidateId) {
        return Optional.ofNullable(collection.find(eq("candidateId",candidateId)).first());
    }

    @Override
    public void saveOrUpdate(CandidateProfile profile) {
        collection.replaceOne(eq("candidateId",profile.getCandidateId()),profile,
                new ReplaceOptions().upsert(true));
    }


}
