package com.vamshi.jobapply.repository;



import com.vamshi.jobapply.models.CandidateProfile;

import java.util.Optional;

public interface CandidateProfileRepository {
    Optional<CandidateProfile> findByCandidateId(String candidateId);
    void saveOrUpdate(CandidateProfile profile);


}
