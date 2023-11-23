package com.springboot.app.service;

import java.util.List;

import com.springboot.app.domain.Categoria;
import com.springboot.app.domain.Producto;

public interface IProductoService {

	public Producto save(Producto producto);
	
	public List<Producto> findAll();
	
	public Producto findById(Long id);
	
	public void delete(Long id);
	
	public List<Categoria> findAllCategorias();

	public List<Producto> filtrarProductos(String nombreProducto);
}
