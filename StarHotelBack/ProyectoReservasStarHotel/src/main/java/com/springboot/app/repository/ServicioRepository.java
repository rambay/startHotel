package com.springboot.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.app.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio,Long>{

}
