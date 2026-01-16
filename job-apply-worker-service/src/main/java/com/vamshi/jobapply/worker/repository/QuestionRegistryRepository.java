package com.vamshi.jobapply.worker.repository;

import com.vamshi.jobapply.worker.enums.JobPlatform;
import com.vamshi.jobapply.worker.enums.QuestionCategory;
import com.vamshi.jobapply.worker.model.QuestionRegistry;

import java.util.Optional;

public interface QuestionRegistryRepository {
    Optional<QuestionRegistry> findByHash(String questionHash);

    void upsert(String hash, String normalized, JobPlatform platform, QuestionCategory category);

    QuestionRegistry save(QuestionRegistry questionRegistry);

    void incrementSeenCount(String questionHash);

}
