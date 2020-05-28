package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserCreateDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserPatchDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserPutDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.ApplicationUserRepository;
import com.chernoivan.books.rating.domain.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplicationUserService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private TranslationService translationService;

    public ApplicationUserReadDTO getUser(UUID id) {
        ApplicationUser applicationUser = getUserRequired(id);
        return translationService.toRead(applicationUser);
    }

    public ApplicationUserReadDTO createUser(ApplicationUserCreateDTO create) {
        ApplicationUser applicationUser = translationService.toEntity(create);

        applicationUser = applicationUserRepository.save(applicationUser);
        return translationService.toRead(applicationUser);
    }

    public ApplicationUserReadDTO patchUser(UUID id, ApplicationUserPatchDTO patch) {
        ApplicationUser applicationUser = getUserRequired(id);

        translationService.patchEntity(patch, applicationUser);

        applicationUser = applicationUserRepository.save(applicationUser);
        return translationService.toRead(applicationUser);
    }

    public ApplicationUserReadDTO updateUser(UUID id, ApplicationUserPutDTO put) {
        ApplicationUser applicationUser = getUserRequired(id);

        translationService.updateEntity(put, applicationUser);

        applicationUser = applicationUserRepository.save(applicationUser);
        return translationService.toRead(applicationUser);
    }

    public void deleteUser(UUID id) {
        applicationUserRepository.delete(getUserRequired(id));
    }

    public ApplicationUser getUserRequired(UUID id) {
        return applicationUserRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ApplicationUser.class, id));
    }
}
