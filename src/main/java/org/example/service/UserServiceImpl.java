package org.example.service;

import org.example.model.AppUser;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AppUser createUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<AppUser> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
