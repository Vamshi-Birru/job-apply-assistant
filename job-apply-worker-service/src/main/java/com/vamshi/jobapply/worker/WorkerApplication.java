package com.vamshi.jobapply.worker;

import io.javalin.Javalin;

public class WorkerApplication {
    public static void main(String[] args){
        Javalin app = Javalin.create().start(8080);

        app.get("/health", ctx -> ctx.result("OK"));
        app.get("/apply", ctx -> {
            ctx.result("Job application triggered");
        });
        System.out.println("Job Apply Worker started on port 8080");
    }
}
