package com.springboot.app.repository;

import com.springboot.app.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByUsername(String username);

	public boolean existsByUsername(String username);

	public boolean existsByCedula(String cedula);

	public boolean existsByEmail(String email);
}
