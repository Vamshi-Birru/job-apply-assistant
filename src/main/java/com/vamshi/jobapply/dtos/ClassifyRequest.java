package com.vamshi.jobapply.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassifyRequest {
    @JsonProperty
    private String jobUrl;

    // Required by Jackson
    public ClassifyRequest() {}

    public ClassifyRequest(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getJobUrl() {
        return jobUrl;
    }
}
