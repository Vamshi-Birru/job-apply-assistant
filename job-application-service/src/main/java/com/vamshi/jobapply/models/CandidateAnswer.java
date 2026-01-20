package com.vamshi.jobapply.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateAnswer {
    private String questionHash;
    private String answer;
    private boolean block;
}
