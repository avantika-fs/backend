package com.example.demo.repository;

import com.example.demo.entity.SystemUser;
import com.example.demo.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByUsername(String username);
    List<SystemUser> findByRole(Role role);
}
