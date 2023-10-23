package com.springboot.app.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para hacer el mapeo de la tabla usuarios
 * 
 * @author miloc
 * @since 18-09-2023
 */

@Entity
@Table(name = "usuarios", schema = "compra_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

	private static final long serialVersionUID = -3453730571498730089L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Column(name = "nombre_usuario")
	private String nombreUsuario;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "estado_usuario")
	private Boolean estadoUsuario;
	
	@Email
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "contrasena")
	private String contrasena;
	
	@Column(name = "imagen")
	private String imagen;
	
	@Column(name = "username", unique = true)
	private String username;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_ciudad")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@NotNull(message = "no puede estar vacio")
	private Ciudad ciudad;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"usuario", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	private List<Producto> productos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"usuario", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	private List<UsuarioRole> roles;
	
	
}
