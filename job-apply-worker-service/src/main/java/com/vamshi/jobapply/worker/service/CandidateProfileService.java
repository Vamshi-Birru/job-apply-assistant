package com.vamshi.jobapply.worker.service;

import com.vamshi.jobapply.worker.dto.CandidateProfile;
import com.vamshi.jobapply.worker.repository.CandidateProfileRepository;

import java.util.Optional;

public class CandidateProfileService {
    private final CandidateProfileRepository repository;

    public CandidateProfileService(CandidateProfileRepository repository){
        this.repository = repository;
    }

    public Optional<CandidateProfile> getProfile(String candidateId){
        return repository.findByCandidateId(candidateId);
    }

    public void saveProfile(CandidateProfile profile){
        repository.save(profile);
    }

    public void updateProfile(CandidateProfile profile){
        repository.update(profile);
    }
}
