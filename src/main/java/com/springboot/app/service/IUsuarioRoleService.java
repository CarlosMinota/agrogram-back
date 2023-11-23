package com.springboot.app.service;

import com.springboot.app.domain.UsuarioRole;

public interface IUsuarioRoleService {

    public void saveUsuarioRole(Long idUsuario, Long tipoUsuario);
}
