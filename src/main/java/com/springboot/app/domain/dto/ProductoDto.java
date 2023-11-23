package com.springboot.app.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para la entidad prodcuto
 * 
 * @author miloc
 * @since 21-09-2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto implements Serializable {

	private static final long serialVersionUID = -973124493744679361L;
	
	private Long idProducto;
	
	private String nombreProducto;
	
	private Double precio;
	
	private Boolean estadoProducto;
	
	private String infoProduccion;
	
	private String descripcion;
	
	private Long categoria;
	
	private Long usuario;

}
