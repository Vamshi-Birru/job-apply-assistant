package com.vamshi.jobapply.worker.resolver;

import com.vamshi.jobapply.worker.dto.AnswerResolution;
import com.vamshi.jobapply.worker.dto.CandidateAnswer;
import com.vamshi.jobapply.worker.dto.CandidateProfile;
import com.vamshi.jobapply.worker.enums.AnswerConfidence;
import com.vamshi.jobapply.worker.repository.CandidateProfileRepository;

import java.util.Optional;

public class SimpleAnswerResolver implements AnswerResolver{
    private final CandidateProfileRepository candidateProfileRepository;
    public SimpleAnswerResolver(CandidateProfileRepository candidateProfileRepository){
        this.candidateProfileRepository = candidateProfileRepository;
    }
    @Override
    public AnswerResolution resolve(String candidateId, String country, String questionHash){
        Optional<CandidateProfile> candidateProfileOpt = candidateProfileRepository.findByCandidateId(candidateId);
        if(candidateProfileOpt.isEmpty()){
            return AnswerResolution.missing(questionHash);
        }

        CandidateProfile candidateProfile = candidateProfileOpt.get();

        Optional<CandidateAnswer> answerOpt = candidateProfile.answerFor(questionHash);

        if(answerOpt.isEmpty()){
            return AnswerResolution.missing(questionHash);
        }
        CandidateAnswer answer = answerOpt.get();

        if (answer.isBlock()) {
            return AnswerResolution.block(questionHash);
        }

        return AnswerResolution.known(
                questionHash,
                answer.getAnswer(),
                AnswerConfidence.HIGH
        );

    }
}
