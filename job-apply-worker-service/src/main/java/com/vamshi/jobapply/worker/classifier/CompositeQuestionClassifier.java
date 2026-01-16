package com.vamshi.jobapply.worker.classifier;

import com.vamshi.jobapply.worker.enums.QuestionCategory;

public class CompositeQuestionClassifier implements QuestionClassifier{
    private final QuestionClassifier primary;
    private final QuestionClassifier fallback;

    public CompositeQuestionClassifier(
            QuestionClassifier primary,
            QuestionClassifier fallback
    ){
        this.primary = primary;
        this.fallback = fallback;
    }

    @Override
    public QuestionCategory classify(String questionText){
        QuestionCategory category = primary.classify(questionText);
        if(category!=QuestionCategory.UNKNOWN){
            return category;
        }
        return fallback.classify(questionText);
    }
}
