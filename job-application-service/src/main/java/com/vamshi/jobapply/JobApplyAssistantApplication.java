package com.vamshi.jobapply;

import com.mongodb.client.MongoClient;
import com.vamshi.jobapply.config.MongoConfig;
import com.vamshi.jobapply.controller.CandidateController;
import com.vamshi.jobapply.db.MongoClientProvider;
import com.vamshi.jobapply.repository.CandidateProfileRepository;
import com.vamshi.jobapply.repository.MongoCandidateProfileRepository;
import com.vamshi.jobapply.service.CandidateService;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

public class JobApplyAssistantApplication
        extends Application<JobApplyAssistantConfiguration> {

    public static void main(final String[] args) throws Exception {
        new JobApplyAssistantApplication().run(args);
    }

    @Override
    public void run(
            JobApplyAssistantConfiguration configuration,
            Environment environment
    ) {

        String mongoUri = System.getenv("MONGO_URI");
        if (mongoUri == null) {
            throw new RuntimeException("MONGO_URI environment variable not set");
        }

        MongoConfig mongoConfig =
                new MongoConfig(mongoUri, "job-apply-automation");

        MongoClient mongoClient =
                MongoClientProvider.getClient(mongoConfig);

        CandidateProfileRepository profileRepo =
                new MongoCandidateProfileRepository(mongoClient);

        CandidateService candidateService =
                new CandidateService(profileRepo);

        environment.jersey().register(
                new CandidateController(candidateService)
        );
    }
}
