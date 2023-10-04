package com.springboot.app.service;

import com.springboot.app.domain.ImagenProducto;

public interface ImagenProductoService {

	public ImagenProducto save(ImagenProducto imagenProducto);
	
	public void delete(Long id);
}
