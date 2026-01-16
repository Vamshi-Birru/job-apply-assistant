package com.vamshi.jobapply.worker.resolver;

import com.vamshi.jobapply.worker.dto.AnswerResolution;

public interface AnswerResolver {
    AnswerResolution resolve(String candidateId, String country, String questionHash );
}
