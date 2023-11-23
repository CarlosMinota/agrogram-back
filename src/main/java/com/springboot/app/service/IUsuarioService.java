package com.springboot.app.service;

import java.util.List;

import com.springboot.app.domain.Ciudad;
import com.springboot.app.domain.Departamento;
import com.springboot.app.domain.TipoUsuario;
import com.springboot.app.domain.Usuario;

public interface IUsuarioService {
	
	public Usuario save(Usuario usuario);
	
	public List<Usuario> findAll();
	
	public Usuario findById(Long id);
	
	public void delete(Long id);
	
	public List<Ciudad> listaCiudadesDepartamento(Long id);
	
	public List<Departamento> listAllDepartamentos(Long idDepartamento);

	public List<TipoUsuario> listTipoUsuario();
	
	public Usuario findByUsername(String username);

	public boolean existByUsername(String username);

	public boolean existByEmail(String email);

	public List<Usuario> filtrarUsuarios(String nombreUsuario);
}
