package com.springboot.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "administracion", name = "roles_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRole implements Serializable {
	
	private static final long serialVersionUID = -2614439881603154547L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_usuario")
	@JsonIgnoreProperties(value = {"roles", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_role")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Role role;
	
}
