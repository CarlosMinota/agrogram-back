package com.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springboot.app.domain.TipoUsuario;
import com.springboot.app.service.IUsuarioRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.domain.Ciudad;
import com.springboot.app.domain.Departamento;
import com.springboot.app.domain.Usuario;
import com.springboot.app.domain.dto.UsuarioDto;
import com.springboot.app.mapper.UsuarioMapper;
import com.springboot.app.service.IUploadFileService;
import com.springboot.app.service.IUsuarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UsuarioRestController {
	
	@Autowired
	private IUsuarioService iUsuarioService;

	@Autowired
	private IUsuarioRoleService iUsuarioRoleService;
	
	@Autowired
	private UsuarioMapper usuarioMapper;
	
	@Autowired
	private IUploadFileService iUploadFileService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/usuario")
	public List<Usuario> findAll(){
		return iUsuarioService.findAllUsuariosTipoProductor(2L);
	}

	@GetMapping("/usuario/filtrar-usuarios/{nombreUsuario}")
	@ResponseStatus(HttpStatus.OK)
	public List<Usuario> filtrarUsuarios(@PathVariable String nombreUsuario){
		return iUsuarioService.filtrarUsuarios(2L, nombreUsuario);
	}

	@PostMapping("/usuario")
	public ResponseEntity<?> create(@RequestBody UsuarioDto usuarioDto){
		
		Map<String, Object> response = new HashMap<>();
		String encriptarContrasena = "";

		Usuario usuario;
		
		try {
			usuarioDto.setEstadoUsuario(true);
			encriptarContrasena = bCryptPasswordEncoder.encode(usuarioDto.getContrasena());
			usuarioDto.setContrasena(encriptarContrasena);
			usuario = usuarioMapper.usuarioDtoToUsuario(usuarioDto);
			iUsuarioService.save(usuario);
			iUsuarioRoleService.saveUsuarioRole(usuario.getIdUsuario(), usuarioDto.getTipoUsuario());
		} catch (DataAccessException e) {
			response.put("mensaje", "Se ha presentado un error insertando en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado con éxito");
		response.put("usuario", usuarioDto);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		
		UsuarioDto usuarioDto = null;
		HashMap<String, Object> response = new HashMap<>();
		
		try {
			usuarioDto = usuarioMapper.usuarioToUsuarioDto(iUsuarioService.findById(id));
			usuarioDto.setContrasena("");
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (usuarioDto == null) {
			response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("usuario", usuarioDto);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@GetMapping("/usuario-entidad/{id}")
	public ResponseEntity<?> showEntidad(@PathVariable Long id){

		Usuario usuario = null;
		HashMap<String, Object> response = new HashMap<>();

		try {
			usuario = iUsuarioService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuario == null) {
			response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("usuario", usuario);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> update(@RequestBody UsuarioDto usuarioDto, @PathVariable Long id){
		Usuario usuarioActual = iUsuarioService.findById(id);
		String encriptarContrasena = bCryptPasswordEncoder.encode(usuarioDto.getContrasena());
		usuarioDto.setContrasena(encriptarContrasena);
		Usuario usuarioUpdate = usuarioMapper.usuarioDtoToUsuario(usuarioDto);
		HashMap<String, Object> response = new HashMap<>();
		
		if (usuarioActual == null) {
			response.put("mensaje", "Error: no se pudo editar, "
					+ "el cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			usuarioActual.setNombreUsuario(usuarioUpdate.getNombreUsuario());
			usuarioActual.setUsername(usuarioUpdate.getUsername());
			usuarioActual.setTelefono(usuarioUpdate.getTelefono());
			usuarioActual.setEstadoUsuario(usuarioUpdate.getEstadoUsuario());
			usuarioActual.setEmail(usuarioUpdate.getEmail());
			usuarioActual.setContrasena(usuarioUpdate.getContrasena());
			usuarioActual.setCiudad(usuarioUpdate.getCiudad());
			
			usuarioUpdate = iUsuarioService.save(usuarioActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario se ha actualizado con éxito!");
		response.put("usuario", usuarioUpdate);
		
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		HashMap<String, Object> response = new HashMap<>();
		
		try {
			iUsuarioService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el usuario en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario se ha eliminado con éxito");
		
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/usuario/username/{username}")
	public ResponseEntity<?> existsByUsername(@PathVariable String username){
		HashMap<String, Object> response = new HashMap<>();

		if (iUsuarioService.existByUsername(username)){
			response.put("mensaje", "El nombre de usuario ya se está en uso");
			return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
		}

		response.put("estado", false);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/usuario/email/{email}")
	public ResponseEntity<?> existsByEmail(@PathVariable String email){
		HashMap<String, Object> response = new HashMap<>();

		if (iUsuarioService.existByEmail(email)){
			response.put("mensaje", "El correo electronico ya se encuentra registrado");
			return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
		}

		response.put("estado", false);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@PostMapping("usuario/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {

		Map<String, Object> response = new HashMap<>();
		Usuario usuario = iUsuarioService.findById(id);

		if (!archivo.isEmpty()) {
			
			String nombreArchivo = null;
			
			try {
				nombreArchivo = iUploadFileService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del cliente ");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String fotoAnterior = usuario.getImagen();

			iUploadFileService.eliminar(fotoAnterior);

			usuario.setImagen(nombreArchivo);
			iUsuarioService.save(usuario);
			response.put("usuario", usuario);
			response.put("mensaje", "Has subido correctamente la imagen " + nombreArchivo);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("usuario/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		
		Resource recurso = null;

		try {
			recurso = iUploadFileService.cargarUsuario(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@GetMapping("/usuario/ciudades-departamento/{id}")
	public ResponseEntity<?> listarCiudadesDepartamento(@PathVariable Long id){
		HashMap<String, Object> response = new HashMap<>();
		List<Ciudad> listaCiudades = null;
		
		if (id == null) {
			response.put("mensaje", "Debe seleccionar una región para listar las ciudades");
			return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			listaCiudades = iUsuarioService.listaCiudadesDepartamento(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al buscar la región");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("ciudades", listaCiudades);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/usuario/departamento")
	public ResponseEntity<?> listarDepartamento(){
		HashMap<String, Object> response = new HashMap<>();
		List<Departamento> listaDepartamentos = null;
		
		try {
			listaDepartamentos = iUsuarioService.listAllDepartamentos(1L);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al listar los departamentos");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("departamentos", listaDepartamentos);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/usuario/tipo-usuario")
	public List<TipoUsuario> listTipoUsuario(){
		return iUsuarioService.listTipoUsuario();
	}
}
