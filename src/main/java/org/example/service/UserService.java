package org.example.service;

import org.example.model.AppUser;

import java.util.Optional;

public interface UserService {

    AppUser createUser(AppUser user);
    Optional<AppUser> findUserByEmail(String email);
    Optional<AppUser> findUserById(String id);
}
