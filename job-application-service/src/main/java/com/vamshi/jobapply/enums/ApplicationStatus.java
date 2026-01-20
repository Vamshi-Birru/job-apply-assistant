package com.vamshi.jobapply.enums;

public enum ApplicationStatus {
    PENDING, // Waiting for missing info or retry time
    RUNNING,  // Currently being processed by reconciler
    COMPLETED, // Successfully applied
    FAILED // Terminal failure
}
