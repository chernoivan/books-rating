package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.ApplicationUser;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserCreateDTO;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserPatchDTO;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserPutDTO;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.movie.ratings.exception.EntityNotFoundException;
import com.chernoivan.movie.ratings.repository.ApplicationUserRepository;
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
