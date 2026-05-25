package com.bookmyvenue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyvenue.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}