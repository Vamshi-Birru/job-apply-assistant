package com.vamshi.jobapply.worker.classifier;

import com.vamshi.jobapply.worker.enums.QuestionCategory;

public class RuleBasedQuestionClassifier implements QuestionClassifier{

    @Override
    public QuestionCategory classify(String questionText){
        String q =questionText.toLowerCase();

        if(q.contains("authorized to work")||q.contains("legally authorized")){
            return QuestionCategory.WORK_AUTHORIZATION;
        }

        if(q.contains("sponsorship")||q.contains("visa")){
            return QuestionCategory.SPONSORSHIP;
        }

        if(q.contains("gender")||q.contains("ethnicity")||q.contains("race")||q.contains("veteran")||q.contains("disability")){
            return QuestionCategory.DEMOGRAPHIC;
        }

        if(q.length()>100){
            return QuestionCategory.FREE_TEXT;
        }

        return QuestionCategory.UNKNOWN;
    }
}
