package com.springboot.app.service.implement;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.service.IUploadFileService;

/**
 * Servicio para gestionar la fotos de lo usuarios y productos
 * 
 * @author miloc
 * @since 21-09-2023
 */

@Service
@Scope("singleton")
public class IUploadFileServiceImpl implements IUploadFileService {

	private final static String DIRECTORIO_UPLOAD = "uploads";

	@Override
	public Resource cargarUsuario(String nombreFoto) throws MalformedURLException {
		Path rutaArchivo = getPath(nombreFoto);

		Resource recurso = new UrlResource(rutaArchivo.toUri());

		if (!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
			recurso = new UrlResource(rutaArchivo.toUri());
		}
		return recurso;
	}
	
	@Override
	public Resource cargarProducto(String nombreFoto) throws MalformedURLException {
		Path rutaArchivo = getPath(nombreFoto);

		Resource recurso = new UrlResource(rutaArchivo.toUri());

		if (!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("agrogram.png").toAbsolutePath();
			recurso = new UrlResource(rutaArchivo.toUri());
		}
		return recurso;
	}

	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename().replace(" ", "");
		Path rutaArchivo = getPath(nombreArchivo);

		Files.copy(archivo.getInputStream(), rutaArchivo);
		return nombreArchivo;
	}

	@Override
	public Boolean eliminar(String nombreFoto) {
		
		if (nombreFoto != null && nombreFoto.length() > 0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String nombreFoto) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}

}
