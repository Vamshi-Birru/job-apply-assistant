package com.vamshi.jobapply;

import com.vamshi.jobapply.controller.JobController;
import com.vamshi.jobapply.core.JobClassifier;
import com.vamshi.jobapply.enums.JobPlatform;
import com.vamshi.jobapply.service.JobService;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class JobApplyAssistantApplication extends Application<JobApplyAssistantConfiguration> {

//    public JobService jobService;
//    public JobClassifier classifier;
    public static void main(final String[] args) throws Exception {
        new JobApplyAssistantApplication().run(args);
    }

    @Override
    public String getName() {
        return "JobApplyAssistant";
    }

    @Override
    public void initialize(final Bootstrap<JobApplyAssistantConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final JobApplyAssistantConfiguration configuration,
                    final Environment environment) {

        environment.jersey().register(JobController.class);
    }

}
