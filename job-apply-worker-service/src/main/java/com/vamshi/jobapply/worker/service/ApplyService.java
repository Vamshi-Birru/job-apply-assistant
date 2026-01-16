package com.vamshi.jobapply.worker.service;

import com.vamshi.jobapply.worker.resolver.AnswerResolver;
import com.vamshi.jobapply.worker.classifier.QuestionClassifier;
import com.vamshi.jobapply.worker.dto.ApplyRequest;
import com.vamshi.jobapply.worker.dto.ApplyResponse;
import com.vamshi.jobapply.worker.enums.ApplyStatus;
import com.vamshi.jobapply.worker.platform.GreenHouseApplyPlatform;
import com.vamshi.jobapply.worker.repository.QuestionRegistryRepository;

public class ApplyService {

    private final GreenHouseApplyPlatform greenHouseApplyPlatform;

    public ApplyService(QuestionRegistryRepository registry, QuestionClassifier classifier, AnswerResolver resolver){

        this.greenHouseApplyPlatform = new GreenHouseApplyPlatform(registry, classifier, resolver);


    }
    public ApplyResponse apply (ApplyRequest request) {

        return switch (request.getPlatform()){
            case GREENHOUSE -> greenHouseApplyPlatform.apply(request);
//            case EASY_APPLY -> easyApply.detect(request);
            default ->  new ApplyResponse(
                    request.getJobUrl(),
                    request.getPlatform(),
                    ApplyStatus.UNSUPPORTED,
                    null,
                    null,
                    null
            );
        };
    }
}
