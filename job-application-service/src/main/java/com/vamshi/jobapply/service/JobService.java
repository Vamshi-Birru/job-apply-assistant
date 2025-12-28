package com.vamshi.jobapply.service;

import com.vamshi.jobapply.core.JobClassifier;
import com.vamshi.jobapply.dtos.ClassifyResponse;
import com.vamshi.jobapply.enums.JobPlatform;


public class JobService {
    private final JobClassifier jobClassifier;

    public JobService(JobClassifier jobClassifier)
    {
        this.jobClassifier = jobClassifier;
    }

    public ClassifyResponse classifyJob (String jobUrl){
        JobPlatform platform = jobClassifier.classify(jobUrl);
        return new ClassifyResponse(jobUrl, platform);
    }
}
