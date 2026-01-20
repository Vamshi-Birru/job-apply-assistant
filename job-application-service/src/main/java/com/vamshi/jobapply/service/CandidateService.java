package com.vamshi.jobapply.service;

import com.vamshi.jobapply.dtos.CandidateAnswer;
import com.vamshi.jobapply.models.CandidateProfile;
import com.vamshi.jobapply.repository.CandidateProfileRepository;
import com.vamshi.jobapply.repository.MongoCandidateProfileRepository;

import java.util.HashMap;

public class CandidateService {
    private final CandidateProfileRepository repo;

    public CandidateService(CandidateProfileRepository repo){
        this.repo = repo;
    }

    public void saveAnswer(String candidateId, String questionHash, String answer, boolean block) throws Exception {
        var profileOpt = repo.findByCandidateId(candidateId);

        if(candidateId== null){
            throw new Exception("Candidate not found");
        }

        CandidateProfile profile = profileOpt.orElseGet(()->{
            CandidateProfile p = new CandidateProfile();
            p.setCandidateId(candidateId);
            p.setAnswers(new HashMap<>());
            return p;
        });

        profile.getAnswers().put(questionHash, new CandidateAnswer(questionHash, answer, block));

        repo.saveOrUpdate(profile);
    }


}
