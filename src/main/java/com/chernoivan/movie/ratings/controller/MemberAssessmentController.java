package com.chernoivan.movie.ratings.controller;

import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentCreateDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentPatchDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentPutDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentReadDTO;
import com.chernoivan.movie.ratings.service.MemberAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/member-assessments")
public class MemberAssessmentController {
    @Autowired
    private MemberAssessmentService memberAssessmentService;

    @GetMapping("/{id}")
    public MemberAssessmentReadDTO getMemberAssessment(@PathVariable UUID id) {
        return memberAssessmentService.getMemberAssessment(id);
    }

    @PutMapping("/{id}")
    public MemberAssessmentReadDTO putMemberAssessment(@PathVariable UUID id, @RequestBody MemberAssessmentPutDTO put) {
        return memberAssessmentService.updateMemberAssessment(id, put);
    }

    @PostMapping
    public MemberAssessmentReadDTO createMemberAssessment(@RequestBody MemberAssessmentCreateDTO create) {
        return memberAssessmentService.createMemberAssessment(create);
    }

    @PatchMapping("/{id}")
    public MemberAssessmentReadDTO patchMemberAssessment(@PathVariable UUID id,
                                                         @RequestBody MemberAssessmentPatchDTO patch) {
        return memberAssessmentService.patchMemberAssessment(id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteMemberAssessment(@PathVariable UUID id) {
        memberAssessmentService.deleteMemberAssessment(id);
    }
}
