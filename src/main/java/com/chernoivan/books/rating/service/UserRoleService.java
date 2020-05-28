package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.UserRole;
import com.chernoivan.books.rating.dto.userrole.UserRoleCreateDTO;
import com.chernoivan.books.rating.dto.userrole.UserRolePatchDTO;
import com.chernoivan.books.rating.dto.userrole.UserRolePutDTO;
import com.chernoivan.books.rating.dto.userrole.UserRoleReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private TranslationService translationService;

    public UserRoleReadDTO getRole(UUID id) {
        UserRole userRole = getUserRoleRequired(id);
        return translationService.toRead(userRole);
    }

    public UserRoleReadDTO createUserRole(UserRoleCreateDTO create) {
        UserRole userRole = translationService.toEntity(create);

        userRole = roleRepository.save(userRole);
        return translationService.toRead(userRole);
    }

    public UserRoleReadDTO patchUserRole(UUID id, UserRolePatchDTO patch) {
        UserRole userRole = getUserRoleRequired(id);

        translationService.patchEntity(patch, userRole);

        userRole = roleRepository.save(userRole);
        return translationService.toRead(userRole);
    }

    public UserRoleReadDTO updateUserRole(UUID id, UserRolePutDTO put) {
        UserRole userRole = getUserRoleRequired(id);

        translationService.updateEntity(put, userRole);

        userRole = roleRepository.save(userRole);
        return translationService.toRead(userRole);
    }

    public void deleteUserRole(UUID id) {
        roleRepository.delete(getUserRoleRequired(id));
    }


    public UserRole getUserRoleRequired(UUID id) {
        return roleRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(UserRole.class, id));
    }

}
