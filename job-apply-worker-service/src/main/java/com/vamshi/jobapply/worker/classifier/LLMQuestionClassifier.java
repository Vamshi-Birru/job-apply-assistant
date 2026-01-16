package com.vamshi.jobapply.worker.classifier;

import com.vamshi.jobapply.worker.enums.QuestionCategory;

public class LLMQuestionClassifier implements QuestionClassifier{
    @Override
    public QuestionCategory classify(String questionText){

        return QuestionCategory.UNKNOWN;
    }
}
