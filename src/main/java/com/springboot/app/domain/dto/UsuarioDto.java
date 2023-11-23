package com.springboot.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto implements Serializable {
	
	private static final long serialVersionUID = 2176121684156047440L;

	private Long idUsuario;
	
	private String nombreUsuario;
	
	private String telefono;
	
	private Boolean estadoUsuario;
	
	private String email;
	
	private String contrasena;
	
	private String username;
	
	private String imagen;

	private Long tipoUsuario;

	private Integer ciudad;

	private Integer departamento;
}
