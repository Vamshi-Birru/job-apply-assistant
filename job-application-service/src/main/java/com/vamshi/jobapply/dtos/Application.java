package com.vamshi.jobapply.dtos;

import com.vamshi.jobapply.enums.ApplicationStatus;
import com.vamshi.jobapply.enums.JobPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    private String applicationId; //UUID (primary)
    private ApplicationStatus status;
    private String candidateId;
    private String jobUrl;
    private JobPlatform platform;
    private String country;
    //resume from last
    private String lastMissingQuestionHash;
    private String lastMissingQuestionText;

    //MetaData
    private Instant createdAt;
    private Instant updatedAt;

}
