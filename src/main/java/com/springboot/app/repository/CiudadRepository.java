package com.springboot.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.app.domain.Ciudad;
import com.springboot.app.domain.Departamento;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

	public List<Ciudad> findByDepartamento_idDepartamento(Long id);
	
	@Query("from Departamento")
	public List<Departamento> findAllDepartamentos();
}
