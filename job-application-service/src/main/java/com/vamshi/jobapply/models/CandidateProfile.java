package com.vamshi.jobapply.models;

import com.vamshi.jobapply.dtos.CandidateAnswer;
import lombok.Data;

import java.util.Map;

@Data
public class CandidateProfile {
    private String candidateId;
    private Map<String, CandidateAnswer> answers;

}
