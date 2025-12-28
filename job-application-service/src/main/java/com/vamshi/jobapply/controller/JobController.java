package com.vamshi.jobapply.controller;

import com.vamshi.jobapply.core.JobClassifier;
import com.vamshi.jobapply.dtos.ClassifyRequest;
import com.vamshi.jobapply.dtos.ClassifyResponse;
import com.vamshi.jobapply.service.JobService;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.MediaType;

@Path("/jobs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobController {
//    private final JobService jobService;
//
//
//    public JobController(JobService jobService) {
//        this.jobService = jobService;
//    }
    private static final JobService jobService =
            new JobService(new JobClassifier());

    @POST
    @Path("/classify")
    public ClassifyResponse classifyJob(ClassifyRequest request){
        if(request==null|| request.getJobUrl() == null || request.getJobUrl().isBlank()){
            throw new BadRequestException("jobUrl must be provided");
        }
        return jobService.classifyJob(request.getJobUrl());

    }
}
