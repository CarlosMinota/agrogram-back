package com.springboot.app.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * IUploadFileService
 * 
 * @author miloc
 * @since 21-09-2023
 */

public interface IUploadFileService {

	public Resource cargarUsuario(String nombreFoto) throws MalformedURLException;
	
	public Resource cargarProducto(String nombreFoto) throws MalformedURLException;
	
	public String copiar(MultipartFile archivo) throws IOException;
	
	public Boolean eliminar(String nombreFoto);
	
	public Path getPath(String nombreFoto);
}
