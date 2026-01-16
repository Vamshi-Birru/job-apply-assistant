package com.vamshi.jobapply.worker.dto;

import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
public class CandidateProfile {
    private String candidateId;
    private Map<String, CandidateAnswer> answers;

    public Optional<CandidateAnswer> answerFor(String questionHash){
        if(answers==null) return Optional.empty();

        return Optional.ofNullable(answers.get(questionHash));
    }
}
