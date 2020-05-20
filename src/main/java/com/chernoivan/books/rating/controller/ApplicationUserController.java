package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserCreateDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserPatchDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserPutDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.books.rating.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class ApplicationUserController {

    @Autowired
    private ApplicationUserService applicationUserService;

    @GetMapping("/{id}")
    public ApplicationUserReadDTO getUser(@PathVariable UUID id) {
        return applicationUserService.getUser(id);
    }

    @PutMapping("/{id}")
    public ApplicationUserReadDTO putUser(@PathVariable UUID id, @RequestBody ApplicationUserPutDTO put) {
        return applicationUserService.updateUser(id, put);
    }

    @PostMapping
    public ApplicationUserReadDTO createUser(@RequestBody ApplicationUserCreateDTO createDTO) {
        return applicationUserService.createUser(createDTO);
    }

    @PatchMapping("/{id}")
    public ApplicationUserReadDTO patchUser(@PathVariable UUID id, @RequestBody ApplicationUserPatchDTO patch) {
        return applicationUserService.patchUser(id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        applicationUserService.deleteUser(id);
    }
}
