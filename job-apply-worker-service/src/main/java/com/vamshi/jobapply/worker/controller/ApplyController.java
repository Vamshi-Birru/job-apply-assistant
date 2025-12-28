package com.vamshi.jobapply.worker.controller;

import com.vamshi.jobapply.worker.dto.ApplyRequest;
import com.vamshi.jobapply.worker.dto.ApplyResponse;
import com.vamshi.jobapply.worker.service.ApplyService;
import io.javalin.Javalin;

public class ApplyController {

    private final ApplyService applyService;

    public ApplyController(Javalin app, ApplyService applyService){
            this.applyService = applyService;
        app.post("/apply", ctx ->{
            ApplyRequest request = ctx.bodyAsClass(ApplyRequest.class);
            if(request.getJobUrl() == null || request.getPlatform() == null){
                ctx.status(400).result("jobUrl and platform required");
                return;
            }
            ApplyResponse response = applyService.apply(request);
            ctx.json(response);
        });
    }


}
