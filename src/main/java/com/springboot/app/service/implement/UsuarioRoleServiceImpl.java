package com.springboot.app.service.implement;

import com.springboot.app.domain.UsuarioRole;
import com.springboot.app.domain.dto.UsuarioRoleDto;
import com.springboot.app.mapper.UsuarioRoleMapper;
import com.springboot.app.repository.UsuarioRoleRepository;
import com.springboot.app.service.IUsuarioRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioRoleServiceImpl implements IUsuarioRoleService {

    @Autowired
    private UsuarioRoleRepository usuarioRoleRepository;

    @Autowired
    private UsuarioRoleMapper usuarioRoleMapper;
    @Override
    public void saveUsuarioRole(Long idUsuario, Long tipoUsuario) {
        UsuarioRoleDto usuarioRoleDto = new UsuarioRoleDto();
        if (tipoUsuario == 1L){
            usuarioRoleDto.setUsuario(idUsuario);
            usuarioRoleDto.setRole(2L);
        } else if (tipoUsuario == 2L){
            usuarioRoleDto.setUsuario(idUsuario);
            usuarioRoleDto.setRole(1L);
        }
        usuarioRoleRepository.save(usuarioRoleMapper.usuarioRoleDtoToUsuarioRole(usuarioRoleDto));
    }
}
