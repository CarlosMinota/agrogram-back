package com.springboot.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.domain.ImagenProducto;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Long> {

}
