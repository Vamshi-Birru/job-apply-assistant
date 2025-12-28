package com.vamshi.jobapply.worker.service;

import com.vamshi.jobapply.worker.dto.ApplyRequest;
import com.vamshi.jobapply.worker.dto.ApplyResponse;
import com.vamshi.jobapply.worker.dto.ApplyStatus;
import com.vamshi.jobapply.worker.platform.GreenHouseApplyPlatform;
import lombok.AllArgsConstructor;

import static com.vamshi.jobapply.worker.constants.Constants.*;

public class ApplyService {

    private final GreenHouseApplyPlatform greenHouseApplyPlatform;
    public ApplyService(){
        this.greenHouseApplyPlatform = new GreenHouseApplyPlatform();
    }
    public ApplyResponse apply (ApplyRequest request) {

        return switch (request.getPlatform()){
            case GREENHOUSE -> greenHouseApplyPlatform.detect(request);
//            case EASY_APPLY -> easyApply.detect(request);
            default ->  new ApplyResponse(
                    request.getJobUrl(),
                    request.getPlatform(),
                    ApplyStatus.UNSUPPORTED,
                    null
            );
        };
    }
}
