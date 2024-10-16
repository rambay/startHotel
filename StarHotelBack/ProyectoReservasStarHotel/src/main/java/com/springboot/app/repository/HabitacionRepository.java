package com.springboot.app.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.app.model.Habitacion;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long>{

	@Query(value = "CALL ObtenerHabitacionesDisponibles(:capacidad, :fecha_entrada, :fecha_salida)", nativeQuery = true)
	List<Habitacion> obtenerHabitacionesPorFechas(int capacidad, Date fecha_entrada, Date fecha_salida);
}
