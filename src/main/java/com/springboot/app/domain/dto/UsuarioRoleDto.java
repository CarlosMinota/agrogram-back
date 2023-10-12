package com.springboot.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRoleDto implements Serializable {

    private static final long serialVersionUID = 1026304860509389947L;

    private Long id;

    private Long usuario;

    private Long role;
}
