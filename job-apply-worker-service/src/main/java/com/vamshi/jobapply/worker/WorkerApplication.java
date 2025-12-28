package com.vamshi.jobapply.worker;

import com.vamshi.jobapply.worker.controller.ApplyController;
import com.vamshi.jobapply.worker.service.ApplyService;
import io.javalin.Javalin;

public class WorkerApplication {
    public static void main(String[] args){
        Javalin app = Javalin.create(
                config-> {
                    config.http.defaultContentType= "application/json";
                }
        ).start(8080);

        ApplyService applyService = new ApplyService();
        new ApplyController(app, applyService);
        System.out.println("Job Apply Worker running on port 8080");
    }
}
