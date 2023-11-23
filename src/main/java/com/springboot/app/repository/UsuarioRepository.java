package com.springboot.app.repository;

import com.springboot.app.domain.TipoUsuario;
import com.springboot.app.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByUsername(String username);

	public boolean existsByUsername(String username);

	public boolean existsByEmail(String email);

	public List<Usuario> findByNombreUsuarioContainingIgnoreCase(String nombreUsuario);

	@Query("from TipoUsuario ")
	public List<TipoUsuario> listTipoUsuario();
}
