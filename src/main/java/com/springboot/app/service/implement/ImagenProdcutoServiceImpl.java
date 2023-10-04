package com.springboot.app.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.springboot.app.domain.ImagenProducto;
import com.springboot.app.repository.ImagenProductoRepository;
import com.springboot.app.service.ImagenProductoService;

@Service
@Scope("singleton")
public class ImagenProdcutoServiceImpl implements ImagenProductoService {

	@Autowired
	private ImagenProductoRepository imagenProductoRepository;
	
	@Override
	public ImagenProducto save(ImagenProducto imagenProducto) {
		return imagenProductoRepository.save(imagenProducto);
	}

	@Override
	public void delete(Long id) {
		imagenProductoRepository.deleteById(id);
	}

}
