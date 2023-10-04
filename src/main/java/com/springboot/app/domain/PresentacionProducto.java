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
 * Clase para hacer el mapeo de tabla presentacion_prodcuto
 * 
 * @author miloc
 * @since18-09-2023
 */

@Entity
@Table(name = "presentacion_producto", schema = "compra_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentacionProducto implements Serializable {
	
	private static final long serialVersionUID = 4524774897701805482L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_presentacion")
	private Long idPresentacion;
	
	@Column(name = "tipo_presentacion")
	private String tipoPresentacion;
	
	@Column(name = "unidad_orden")
	private String unidadOrden;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "presentacionProducto", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"presentacionProducto", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	private List<Producto> productos;
}
