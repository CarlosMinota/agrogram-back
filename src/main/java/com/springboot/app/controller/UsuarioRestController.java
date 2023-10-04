package com.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	private UsuarioMapper usuarioMapper;
	
	@Autowired
	private IUploadFileService iUploadFileService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/usuario")
	public List<Usuario> findAll(){
		return iUsuarioService.findAll();
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<?> create(@RequestBody UsuarioDto usuarioDto){
		
		Map<String, Object> response = new HashMap<>();
		String encriptarContrasena = "";
		
		try {
			usuarioDto.setEstadoUsuario(true);
			encriptarContrasena = bCryptPasswordEncoder.encode(usuarioDto.getContrasena());
			usuarioDto.setContrasena(encriptarContrasena);
			iUsuarioService.save(usuarioMapper.usuarioDtoToUsuario(usuarioDto));
		} catch (DataAccessException e) {
			response.put("mensaje", "Se ha presentado un error insertando en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado con éxito");
		response.put("usuario", usuarioDto);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		
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
			usuarioActual.setTelefono(usuarioUpdate.getTelefono());
			usuarioActual.setEstadoUsuario(usuarioUpdate.getEstadoUsuario());
			usuarioActual.setEmail(usuarioUpdate.getEmail());
			usuarioActual.setCedula(usuarioUpdate.getCedula());
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
			listaDepartamentos = iUsuarioService.listAllDepartamentos();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al listar los departamentos");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("departamentos", listaDepartamentos);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}
}