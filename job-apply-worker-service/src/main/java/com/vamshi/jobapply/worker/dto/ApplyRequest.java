package com.vamshi.jobapply.worker.dto;


import lombok.Data;

@Data
public class ApplyRequest {
    private String jobUrl;
//    private boolean dryRun;
    private JobPlatform platform;
//    private CandidateProfile profile;

}
