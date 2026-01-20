package com.vamshi.jobapply.dtos;

import lombok.Data;

@Data
public class SaveAnswerRequest {
    private String candidateId;
    private String questionHash;
    private String answer;
    private boolean block;
}
