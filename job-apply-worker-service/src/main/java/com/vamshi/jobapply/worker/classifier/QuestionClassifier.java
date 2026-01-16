package com.vamshi.jobapply.worker.classifier;

import com.vamshi.jobapply.worker.enums.QuestionCategory;

public interface QuestionClassifier {
    QuestionCategory classify(String questionText);
}
