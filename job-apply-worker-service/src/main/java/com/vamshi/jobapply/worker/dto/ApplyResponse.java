package com.vamshi.jobapply.worker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
public class ApplyResponse {
    private String jobUrl;
    private JobPlatform platform;
    private ApplyStatus status;
    private String screenshotPath;
}
