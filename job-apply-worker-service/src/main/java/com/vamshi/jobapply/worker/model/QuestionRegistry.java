package com.vamshi.jobapply.worker.model;

import com.vamshi.jobapply.worker.enums.InputType;
import com.vamshi.jobapply.worker.enums.JobPlatform;
import com.vamshi.jobapply.worker.enums.QuestionCategory;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class QuestionRegistry {
//    private ObjectId id;

    private String questionHash;
//    private String questionText;
    private String normalizedText;
    private JobPlatform platform;
    private QuestionCategory category;
//    private Double confidence;
    private InputType inputType;
    private List<String> options;


//    private Set<String>  companies;
    private int seenCount;
    private Instant createdAt;
    private Instant lastSeenAt;
    private int version;

    public QuestionRegistry(String questionHash, String normalizedText, JobPlatform platform, QuestionCategory category){
        this.questionHash = questionHash;
        this.normalizedText = normalizedText;
        this.platform = platform;
        this.category = category;
    }

}
