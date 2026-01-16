package com.vamshi.jobapply.worker;

import com.mongodb.client.MongoClient;
import com.vamshi.jobapply.worker.resolver.AnswerResolver;
import com.vamshi.jobapply.worker.classifier.CompositeQuestionClassifier;
import com.vamshi.jobapply.worker.classifier.LLMQuestionClassifier;
import com.vamshi.jobapply.worker.classifier.QuestionClassifier;
import com.vamshi.jobapply.worker.classifier.RuleBasedQuestionClassifier;
import com.vamshi.jobapply.worker.config.MongoConfig;
import com.vamshi.jobapply.worker.controller.ApplyController;
import com.vamshi.jobapply.worker.db.MongoClientProvider;
import com.vamshi.jobapply.worker.resolver.SimpleAnswerResolver;
import com.vamshi.jobapply.worker.repository.CandidateProfileRepository;
import com.vamshi.jobapply.worker.repository.MongoCandidateProfileRepository;
import com.vamshi.jobapply.worker.repository.MongoQuestionRegistryRepository;
import com.vamshi.jobapply.worker.repository.QuestionRegistryRepository;

import com.vamshi.jobapply.worker.service.ApplyService;
import io.javalin.Javalin;

public class WorkerApplication {
    public static void main(String[] args){

        String mongoUri = System.getenv("MONGO_URI");
        if (mongoUri == null) {
            throw new RuntimeException("MONGO_URI environment variable not set");
        }

        String port = System.getenv().getOrDefault("WORKER_PORT", "8080");
        MongoConfig config = new MongoConfig(mongoUri, "job-apply-automation");

        MongoClient mongoClient = MongoClientProvider.getClient(config);

        QuestionRegistryRepository questionRegistryRepository = new MongoQuestionRegistryRepository(mongoClient);

        CandidateProfileRepository candidateProfileRepository = new MongoCandidateProfileRepository(mongoClient);


        QuestionClassifier classifier =
                new CompositeQuestionClassifier(
                        new RuleBasedQuestionClassifier(),
                        new LLMQuestionClassifier());

        AnswerResolver resolver = new SimpleAnswerResolver(candidateProfileRepository);
        Javalin app = Javalin.create().start(Integer.parseInt(port));

        ApplyService applyService = new ApplyService(questionRegistryRepository,classifier,resolver);
        new ApplyController(app, applyService);
        System.out.println("Job Apply Worker running on port 8080");
    }
}
