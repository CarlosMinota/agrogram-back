package com.springboot.app.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenProductoDto implements Serializable {
	
	private static final long serialVersionUID = 1324788703460871170L;
	
	private Long idImagen;

	private String imagen;
	
	private Long producto;
}
