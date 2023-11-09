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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.domain.Categoria;
import com.springboot.app.domain.ImagenProducto;
import com.springboot.app.domain.PresentacionProducto;
import com.springboot.app.domain.Producto;
import com.springboot.app.domain.dto.ImagenProductoDto;
import com.springboot.app.domain.dto.ProductoDto;
import com.springboot.app.mapper.ImagenProductoMapper;
import com.springboot.app.mapper.ProductoMapper;
import com.springboot.app.service.IProductoService;
import com.springboot.app.service.IUploadFileService;
import com.springboot.app.service.ImagenProductoService;

@RestController
@RequestMapping("api")
@CrossOrigin("*")
public class ProductoRestController {

	@Autowired
	private IProductoService iProductoService;

	@Autowired
	private ProductoMapper productoMapper;

	@Autowired
	private IUploadFileService iUploadFileService;

	@Autowired
	private ImagenProductoService imagenProductoService;

	@Autowired
	private ImagenProductoMapper imagenProductoMapper;

	@GetMapping("/producto")
	public List<Producto> findAll() {
		return iProductoService.findAll();
	}
	
	@GetMapping("/producto/categoria")
	public List<Categoria> findAllCategorias(){
		return iProductoService.findAllCategorias();
	}

	@GetMapping("/producto/filtrar-productos/{nombreProducto}")
	@ResponseStatus(HttpStatus.OK)
	public List<Producto> filtrarProductos(@PathVariable String nombreProducto){
		return iProductoService.filtrarProductos(nombreProducto);
	}
	
	@GetMapping("/producto/presentacion-producto")
	public List<PresentacionProducto> findAllPresentacionProductos(){
		return iProductoService.findAllPresentacionProductos();
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@PostMapping("/producto")
	public ResponseEntity<?> create(@RequestBody ProductoDto productoDto) {

		HashMap<String, Object> response = new HashMap<>();

		try {
			productoDto.setEstadoProducto(true);
			iProductoService.save(productoMapper.productoDtoToProducto(productoDto));
		} catch (DataAccessException e) {
			response.put("error", "Se ha presentado un error insertando en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Se ha creado con éxito");
		response.put("producto", productoDto);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@GetMapping("/producto/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		ProductoDto productoDto = null;
		HashMap<String, Object> response = new HashMap<>();

		try {
			productoDto = productoMapper.produtoToProductoDto(iProductoService.findById(id));
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (productoDto == null) {
			response.put("mensaje",
					"El producto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		response.put("producto", productoDto);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@GetMapping("/producto-entidad/{id}")
	public ResponseEntity<?> showEntidad(@PathVariable Long id) {

		Producto producto = null;
		HashMap<String, Object> response = new HashMap<>();

		try {
			producto = iProductoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (producto == null) {
			response.put("mensaje",
					"El producto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		response.put("producto", producto);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@PutMapping("/producto/{id}")
	public ResponseEntity<?> update(@RequestBody ProductoDto productoDto, @PathVariable Long id) {
		Producto productoActual = iProductoService.findById(id);
		Producto productoUpdate = productoMapper.productoDtoToProducto(productoDto);
		HashMap<String, Object> response = new HashMap<>();

		try {
			productoActual.setNombreProducto(productoDto.getNombreProducto());
			productoActual.setPrecio(productoDto.getPrecio());
			productoActual.setEstadoProducto(productoDto.getEstadoProducto());
			productoActual.setInfoProduccion(productoDto.getInfoProduccion());
			productoActual.setDescripcion(productoDto.getDescripcion());
			productoActual.setCategoria(productoUpdate.getCategoria());
			productoActual.setUsuario(productoUpdate.getUsuario());
			productoActual.setPresentacionProducto(productoUpdate.getPresentacionProducto());
			iProductoService.save(productoActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el producto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El producto se ha actualizado con éxito");
		response.put("producto", productoUpdate);
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		HashMap<String, Object> response = new HashMap<>();

		try {
			iProductoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el producto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El producto se ha eliminado con éxito");
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@PostMapping("/producto/uploads")
	public ResponseEntity<?> uploadImagenProducto(@RequestParam("archivo") MultipartFile archivo,
			@RequestParam Long id) {
		HashMap<String, Object> response = new HashMap<>();
		ImagenProductoDto imagenProductoDto = new ImagenProductoDto();
		ImagenProducto imagenProducto = new ImagenProducto();

		String nombreArchivo = null;

		if (!archivo.isEmpty()) {
			try {
				nombreArchivo = iUploadFileService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del producto ");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			imagenProductoDto.setProducto(id);
			imagenProductoDto.setImagen(nombreArchivo);
			imagenProducto = imagenProductoMapper.imagenProdutoDtoToImagenProducto(imagenProductoDto);
			imagenProductoService.save(imagenProducto);

		}
		response.put("mensaje", "La imagen se han agregado con éxito");
		response.put("imagen", nombreArchivo);

		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/producto/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		
		Resource recurso = null;

		try {
			recurso = iUploadFileService.cargarProducto(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}

	@Secured({"ROLE_VENDEDOR", "ROLE_COMPRADOR"})
	@DeleteMapping("/producto/uploads/{id}")
	public ResponseEntity<?> deleteImagenProducto(@PathVariable Long id) {
		HashMap<String, Object> response = new HashMap<>();

		try {
			imagenProductoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la imagen del producto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La imagen del producto se eliminó con éxito");
		return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
	}

}
