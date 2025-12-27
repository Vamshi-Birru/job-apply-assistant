package com.vamshi.jobapply.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vamshi.jobapply.enums.JobPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassifyResponse {
    @JsonProperty
    private String jobUrl;
    private JobPlatform jobPlatform;

}
