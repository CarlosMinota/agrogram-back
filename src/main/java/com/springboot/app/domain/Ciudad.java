package com.springboot.app.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para hacer el mapeo de la tabla ciudades
 * 
 * @author miloc
 * @since 18-09-2023
 */

@Entity
@Table(name = "ciudades", schema = "compra_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ciudad implements Serializable {
	
	private static final long serialVersionUID = 1472256045752985814L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ciudad")
	private Long idCiudad;
	
	@Column(name = "nombre_ciudad")
	private String nombreCiudad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "fk_departamento")
	private Departamento departamento;
}
