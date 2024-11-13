package com.integrador.backend2.repository;

import com.integrador.backend2.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findAdminByEmailAndPassword(String email, String password);

    Admin findByEmail(String email);
}
