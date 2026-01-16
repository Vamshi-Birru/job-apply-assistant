package com.vamshi.jobapply.worker.dto;

import com.vamshi.jobapply.worker.enums.AnswerConfidence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateAnswer {
    private String questionHash;
    private String answer;
    private String conuntry;
    private AnswerConfidence confidence;
    private boolean block;

    public boolean isBlock(){
        return block;
    }
}
