package com.springboot.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.springboot.app.domain.Producto;
import com.springboot.app.domain.dto.ProductoDto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

	@Mapping(source = "categoria", target = "categoria.idCategoria")
	@Mapping(source = "usuario", target = "usuario.idUsuario")
	public Producto productoDtoToProducto(ProductoDto productoDto);
	
	@Mapping(source = "categoria.idCategoria", target = "categoria")
	@Mapping(source = "usuario.idUsuario", target = "usuario")
	public ProductoDto produtoToProductoDto(Producto producto);
}
