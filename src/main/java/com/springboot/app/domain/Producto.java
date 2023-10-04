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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para hacer el mapeo de la tabla productos
 * 
 * @author miloc
 * @since 18-09-2023
 */

@Entity
@Table(name = "productos", schema = "compra_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable {
	
	private static final long serialVersionUID = 8939415782438677617L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Long idProducto;
	
	@Column(name = "nombre_producto")
	private String nombreProducto;
	
	@Column(name = "precio")
	private Double precio;
	
	@Column(name = "estado_producto")
	private Boolean estadoProducto;
	
	@Column(name = "info_produccion")
	private String infoProduccion;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_categoria")
	@JsonIgnoreProperties(value = {"productos", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	@NotNull(message = "no puede estar vacio")
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_usuario")
	@JsonIgnoreProperties(value = {"productos", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	@NotNull(message = "no puede estar vacio")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_presentacion")
	@JsonIgnoreProperties(value = {"productos", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	@NotNull(message = "no puede estar vacio")
	private PresentacionProducto presentacionProducto;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "producto", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"producto", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	private List<ImagenProducto> imagenes;
}
