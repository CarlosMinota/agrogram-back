package com.springboot.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.app.domain.Categoria;
import com.springboot.app.domain.PresentacionProducto;
import com.springboot.app.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	@Query("from Categoria")
	public List<Categoria> findAllCategorias();
	
	@Query("from PresentacionProducto")
	public List<PresentacionProducto> findAllPresentacionProducto();
}
