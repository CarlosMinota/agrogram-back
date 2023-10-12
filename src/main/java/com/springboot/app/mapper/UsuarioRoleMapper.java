package com.springboot.app.mapper;

import com.springboot.app.domain.UsuarioRole;
import com.springboot.app.domain.dto.UsuarioRoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioRoleMapper {

    @Mapping(source = "usuario", target = "usuario.idUsuario")
    @Mapping(source = "role", target = "role.idRole")
    public UsuarioRole usuarioRoleDtoToUsuarioRole(UsuarioRoleDto usuarioRoleDto);

}
