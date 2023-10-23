package com.springboot.app.service.implement;

import com.springboot.app.domain.Ciudad;
import com.springboot.app.domain.Departamento;
import com.springboot.app.domain.Usuario;
import com.springboot.app.repository.CiudadRepository;
import com.springboot.app.repository.UsuarioRepository;
import com.springboot.app.service.IUsuarioService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope("singleton")
public class UsuarioServiceImpl implements UserDetailsService, IUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CiudadRepository ciudadRepository;
	
	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario findById(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		usuarioRepository.deleteById(id);
		
	}

	@Override
	public List<Ciudad> listaCiudadesDepartamento(Long id) {
		return ciudadRepository.findByDepartamento_idDepartamento(id);
	}

	@Override
	public List<Departamento> listAllDepartamentos() {
		return ciudadRepository.findAllDepartamentos();
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	@Override
	public boolean existByUsername(String username) {
		return usuarioRepository.existsByUsername(username);
	}

	@Override
	public boolean existByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByUsername(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole().getNombre()))
				.collect(Collectors.toList());
		
		return new User(usuario.getUsername(), usuario.getContrasena(), usuario.getEstadoUsuario(), 
				true, true, true, authorities);
	}
}
