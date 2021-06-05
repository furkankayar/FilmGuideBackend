package com.service.filmguide.controller.user.repository;

import com.service.filmguide.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IRoleRepository extends JpaRepository<Role, Long>{
    
    public Role findByRole(@Param("role") String role);
}