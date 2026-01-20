package com.vamshi.jobapply.controller;

import com.vamshi.jobapply.dtos.SaveAnswerRequest;
import com.vamshi.jobapply.service.CandidateService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/candidate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CandidateController {
    private final CandidateService service;
     public CandidateController(CandidateService service){
         this.service = service;
     }

     @POST
     @Path("/answer")
    public Response saveAnswer(SaveAnswerRequest req) throws Exception {

         if(req.getCandidateId() == null ||req.getCandidateId().isBlank()){
             return Response.status(400).entity("CandidateID is missing").build();
         }

         service.saveAnswer(req.getCandidateId(), req.getQuestionHash(), req.getAnswer(), req.isBlock());

         return Response.ok().entity("OK").build();
     }


}
