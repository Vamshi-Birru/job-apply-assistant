package com.vamshi.jobapply.worker.dto;

import com.vamshi.jobapply.worker.enums.AnswerConfidence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResolution {
    private String questionHash;
    private String answer;
    private AnswerConfidence confidence;
    private boolean block;
    private boolean missing;

    public static AnswerResolution known(String hash, String answer, AnswerConfidence confidence){
        return new AnswerResolution(
                hash,
                answer,
                confidence,
                false,
                false
        );
    }
    public static AnswerResolution missing(String hash){
        return new AnswerResolution(
                hash,
                null,
                AnswerConfidence.UNKNOWN,
               false,
                true
        );
    }
    public static AnswerResolution block(String hash){
        return new AnswerResolution(
                hash,
                null,
                AnswerConfidence.HIGH,
                true,
                false
        );
    }

}

