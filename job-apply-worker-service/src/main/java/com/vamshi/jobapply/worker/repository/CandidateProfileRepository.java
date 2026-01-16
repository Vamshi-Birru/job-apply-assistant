package com.vamshi.jobapply.worker.repository;

import com.vamshi.jobapply.worker.dto.CandidateProfile;

import java.util.Optional;

public interface CandidateProfileRepository {
    Optional<CandidateProfile> findByCandidateId(String candidateId);
    void save(CandidateProfile profile);

    void update (CandidateProfile profile);
}
