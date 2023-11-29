package com.springboot.app.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.app.domain.Categoria;
import com.springboot.app.domain.Producto;
import com.springboot.app.repository.ProductoRepository;
import com.springboot.app.service.IProductoService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope("singleton")
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public List<Producto> findAll() {
		return productoRepository.findAll(Sort.by("nombreProducto"));
	}

	@Override
	public Producto findById(Long id) {
		return productoRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		productoRepository.deleteById(id);
		
	}

	@Override
	public List<Categoria> findAllCategorias() {
		return productoRepository.findAllCategorias();
	}

	@Override
	@Transactional
	public List<Producto> filtrarProductos(String nombreProducto) {
		return productoRepository.findByNombreProductoContainingIgnoreCase(nombreProducto);
	}

}
