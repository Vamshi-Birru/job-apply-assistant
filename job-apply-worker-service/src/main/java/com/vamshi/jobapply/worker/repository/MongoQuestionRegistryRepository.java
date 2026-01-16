package com.vamshi.jobapply.worker.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.vamshi.jobapply.worker.enums.JobPlatform;
import com.vamshi.jobapply.worker.enums.QuestionCategory;
import com.vamshi.jobapply.worker.model.QuestionRegistry;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.Instant;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MongoQuestionRegistryRepository implements QuestionRegistryRepository{

    private final MongoCollection<QuestionRegistry> collection;

    public MongoQuestionRegistryRepository(MongoCollection<QuestionRegistry> collection){
        this.collection = collection;
    }

    public MongoQuestionRegistryRepository(MongoClient client) {
        MongoDatabase db = client.getDatabase("job-apply-automation");

        // 👇 IMPORTANT - typed collection with codec support
        this.collection = db.getCollection("question_registry", QuestionRegistry.class);
    }

    @Override
    public Optional<QuestionRegistry> findByHash(String hash) {
        return Optional.ofNullable(collection.find(Filters.eq("_id", hash)).first());
    }

    @Override
    public void upsert(String hash, String normalized, JobPlatform platform, QuestionCategory category ){
        var filter = Filters.eq("_id",hash);

        var update = Updates.combine(
                Updates.setOnInsert("_id", hash),
                Updates.setOnInsert("normalizedText", normalized),
                //always store latest category
                Updates.set("category", category.name()),
                //append platform if it is missing
                Updates.addToSet("platforms", platform.name())
        );

        collection.updateOne(filter, update,new UpdateOptions().upsert(true)
        );

    }

    @Override
    public QuestionRegistry save(QuestionRegistry questionRegistry) {
        Instant now = Instant.now();
        questionRegistry.setLastSeenAt(now);
        questionRegistry.setCreatedAt(now);
        questionRegistry.setSeenCount(1);
        collection.insertOne(questionRegistry);
        return questionRegistry;
    }

    @Override
    public void incrementSeenCount(String questionHash) {
        collection.updateOne(
                eq("questionHash", questionHash),
                new Document("$inc", new Document("seenCount", 1))
                        .append("$set", new Document("lastSeenAt", Instant.now()))
        );
    }
}
