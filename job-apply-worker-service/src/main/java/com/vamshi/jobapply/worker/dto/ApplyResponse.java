package com.vamshi.jobapply.worker.dto;


import com.vamshi.jobapply.worker.enums.ApplyStatus;
import com.vamshi.jobapply.worker.enums.JobPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
public class ApplyResponse {
    private String jobUrl;
    private JobPlatform platform;
    private ApplyStatus status;
    private String screenshotPath;

    private String missingQuestionHash;
    private String missingQuestionText;
    public static ApplyResponse missing(String jobUrl, JobPlatform platform,
                                        String questionHash, String questionText) {
        return new ApplyResponse(
                jobUrl,
                platform,
                ApplyStatus.MISSING_INFO,
                null,
                questionHash,
                questionText
        );
    }
}
