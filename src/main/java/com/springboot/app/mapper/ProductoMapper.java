package com.springboot.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.springboot.app.domain.Producto;
import com.springboot.app.domain.dto.ProductoDto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

	@Mapping(source = "categoria", target = "categoria.idCategoria")
	@Mapping(source = "usuario", target = "usuario.idUsuario")
	@Mapping(source = "presentacionProducto", target = "presentacionProducto.idPresentacion")
	public Producto productoDtoToProducto(ProductoDto productoDto);
	
	@Mapping(source = "categoria.idCategoria", target = "categoria")
	@Mapping(source = "usuario.idUsuario", target = "usuario")
	@Mapping(source = "presentacionProducto.idPresentacion", target = "presentacionProducto")
	public ProductoDto produtoToProductoDto(Producto producto);
}
