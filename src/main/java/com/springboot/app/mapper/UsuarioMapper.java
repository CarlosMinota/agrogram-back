package com.springboot.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.springboot.app.domain.Usuario;
import com.springboot.app.domain.dto.UsuarioDto;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@Mapping(source = "ciudad", target = "ciudad.idCiudad")
	@Mapping(source = "departamento", target = "ciudad.departamento.idDepartamento")
	public Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);
	
	@Mapping(source = "ciudad.idCiudad", target = "ciudad")
	@Mapping(source = "ciudad.departamento.idDepartamento", target = "departamento")
	public UsuarioDto usuarioToUsuarioDto(Usuario usuario);
	
	public List<UsuarioDto> listUsuarioToListUsuarioDto(List<Usuario> listaUsuario);
}
