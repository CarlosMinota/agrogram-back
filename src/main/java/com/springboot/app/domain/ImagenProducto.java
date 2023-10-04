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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para hacer el mapeo de tabla imagen_producto
 * 
 * @author miloc
 * @since18-09-2023
 */

@Entity
@Table(name = "imagen_producto", schema = "compra_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenProducto implements Serializable {
	
	private static final long serialVersionUID = 4373078918551855204L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_imagen")
	private Long idImagen;
	
	@Column(name = "imagen")
	private String imagen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"imagenes", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	@JoinColumn(name = "fk_producto")
	@NotNull(message = "no puede estar vacio")
	private Producto producto;
	
}
