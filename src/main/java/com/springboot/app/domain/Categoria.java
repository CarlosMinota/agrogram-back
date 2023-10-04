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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para hacer el mapeo de tabla categoria
 * 
 * @author miloc
 * @since18-09-2023
 */

@Entity
@Table(name = "categorias", schema = "compra_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria implements Serializable {
	
	private static final long serialVersionUID = 2252705194780089769L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private Long idCategoria;

	@Column(name = "nombre_categoria")
	private String nombreCategoria;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"categoria", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	private List<Producto> productos;
}
