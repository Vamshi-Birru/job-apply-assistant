package com.vamshi.jobapply.core;

import com.vamshi.jobapply.enums.JobPlatform;

import static com.vamshi.jobapply.constants.Constants.*;


public class JobClassifier {
    public JobPlatform classify(String jobUrl){
        String url = jobUrl.toLowerCase();

        if(url.contains(LINKEDIN_COM) && url.contains(EASY_APPLY)){
            return JobPlatform.EASY_APPLY;
        }
        else if (url.contains(GREENHOUSE_IO)){
            return JobPlatform.GREENHOUSE;
        }
        else if ( url.contains(WORKDAYJOBS)){
            return JobPlatform.WORKDAYJOBS;
        }
        else if(url.contains(COMPANY_SITE)){
            return JobPlatform.COMPANY_SITE;
        }

        return JobPlatform.UNKNOWN;

    }
}
