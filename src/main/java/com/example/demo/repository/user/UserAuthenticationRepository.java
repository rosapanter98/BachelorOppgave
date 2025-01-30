package com.example.demo.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.user.UserAuthentication;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long> {
    UserAuthentication findByEmail(String email);
}

