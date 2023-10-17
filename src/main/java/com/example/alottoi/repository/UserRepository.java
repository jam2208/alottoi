package com.example.alottoi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.alottoi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmailAndPwd(String email, String pwd);
    public Optional<User> findByEmail(String email);
    
}
