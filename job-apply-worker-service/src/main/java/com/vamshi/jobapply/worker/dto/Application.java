package com.vamshi.jobapply.worker.dto;

import com.vamshi.jobapply.worker.enums.ApplicationStatus;
import com.vamshi.jobapply.worker.enums.JobPlatform;
import lombok.Data;

import java.time.Instant;

@Data
public class Application {
    private String id;
    private String candidateId;
    private String jobUrl;
    private JobPlatform platform;
    private ApplicationStatus status;
    private String missingQuestionHash;
    private int retryCount;
    private Instant nextRetryAt;
    private String lockedBy;
    private Instant lockUntil;
    private Instant createdAt;
    private Instant updateAt;
}
