package com.springboot.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.springboot.app.domain.ImagenProducto;
import com.springboot.app.domain.dto.ImagenProductoDto;

@Mapper(componentModel = "spring")
public interface ImagenProductoMapper {

	@Mapping(source = "producto", target = "producto.idProducto")
	public ImagenProducto imagenProdutoDtoToImagenProducto(ImagenProductoDto imagenProductoDto);
	
	@Mapping(source = "producto.idProducto", target = "producto")
	public ImagenProductoDto imagenProdutoToImagenProductoDto(ImagenProducto imagenProducto);
}
