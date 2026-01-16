package com.vamshi.jobapply.worker.dto;


import com.vamshi.jobapply.worker.enums.JobPlatform;
import lombok.Data;

@Data
public class ApplyRequest {
    private String jobUrl;
    private String candidateId;
    private JobPlatform platform;
    private String country;

}
