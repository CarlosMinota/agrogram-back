package com.springboot.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para hacer el mapeo de la tabla departamentos
 * 
 * @author miloc
 * @since 18-09-2023
 */

@Entity
@Table(name = "departamento", schema = "compra_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Departamento implements Serializable {
	
	private static final long serialVersionUID = -2753367792652856223L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_departamento")
	private Long idDepartamento;
	
	@Column(name = "nombre_departamento")
	private String nombreDepartamento;

}
