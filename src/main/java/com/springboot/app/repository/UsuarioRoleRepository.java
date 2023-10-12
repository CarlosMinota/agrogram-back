package com.springboot.app.repository;

import com.springboot.app.domain.UsuarioRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRoleRepository extends JpaRepository<UsuarioRole, Long> {
}
