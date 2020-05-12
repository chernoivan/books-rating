package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.MemberAssessment;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentCreateDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentPatchDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentPutDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentReadDTO;
import com.chernoivan.movie.ratings.exception.EntityNotFoundException;
import com.chernoivan.movie.ratings.repository.MemberAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberAssessmentService {
    @Autowired
    private MemberAssessmentRepository memberAssessmentRepository;

    @Autowired
    private TranslationService translationService;

    public MemberAssessmentReadDTO getMemberAssessment(UUID id) {
        MemberAssessment memberAssessment = getMemberRequired(id);
        return translationService.toRead(memberAssessment);
    }

    public MemberAssessmentReadDTO createMemberAssessment(MemberAssessmentCreateDTO create) {
        MemberAssessment memberAssessment = translationService.toEntity(create);

        memberAssessment = memberAssessmentRepository.save(memberAssessment);
        return translationService.toRead(memberAssessment);
    }

    public MemberAssessmentReadDTO patchMemberAssessment(UUID id, MemberAssessmentPatchDTO patch) {
        MemberAssessment memberAssessment = getMemberRequired(id);

        translationService.patchEntity(patch, memberAssessment);

        memberAssessment = memberAssessmentRepository.save(memberAssessment);
        return translationService.toRead(memberAssessment);
    }

    public MemberAssessmentReadDTO updateMemberAssessment(UUID id, MemberAssessmentPutDTO put) {
        MemberAssessment memberAssessment = getMemberRequired(id);

        translationService.updateEntity(put, memberAssessment);

        memberAssessment = memberAssessmentRepository.save(memberAssessment);
        return translationService.toRead(memberAssessment);
    }

    public void deleteMemberAssessment(UUID id) {
        memberAssessmentRepository.delete(getMemberRequired(id));
    }

    public MemberAssessment getMemberRequired(UUID id) {
        return memberAssessmentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MemberAssessment.class, id));
    }

}
