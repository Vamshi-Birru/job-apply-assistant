package com.vamshi.jobapply.worker.repository;

import com.vamshi.jobapply.worker.dto.Application;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepository {
    void create (Application application);

    Optional<Application> findById(String id);

    List<Application> findPendingEligible(Instant now, int limit);

    boolean acquireLock(String applicationId, String workerId, Instant lockUntil);

    void markCompleted(String applicationId);

    void markFailed(String applicationId);

    void updateMissing(
            String applicationId,
            String missingQuestionHash,
            Instant nextRetryAt,
            int retryCount
    );
}
