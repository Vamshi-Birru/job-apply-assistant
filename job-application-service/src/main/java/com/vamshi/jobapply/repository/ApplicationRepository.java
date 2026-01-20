package com.vamshi.jobapply.repository;

import com.vamshi.jobapply.dtos.Application;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepository {

    void create(Application application);

    Optional<Application> findById(String applicationId);

    List<Application> findPendingApplications(Instant now);

    void markRunning(String applicationId);

    void markCompleted(String applicationId);

    void markFailed(String applicationId);

    void updateMissing(
            String applicationId,
            String questionHash,
            String questionText,
            Instant nextRetry
    );
}
