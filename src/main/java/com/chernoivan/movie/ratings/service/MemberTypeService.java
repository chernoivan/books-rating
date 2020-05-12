package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.MemberType;
import com.chernoivan.movie.ratings.exception.EntityNotFoundException;
import com.chernoivan.movie.ratings.repository.MemberTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberTypeService {
    @Autowired
    private MemberTypeRepository memberTypeRepository;


    public MemberType getMemberTypeRequired(UUID id) {
        return memberTypeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MemberType.class, id));
    }
}
