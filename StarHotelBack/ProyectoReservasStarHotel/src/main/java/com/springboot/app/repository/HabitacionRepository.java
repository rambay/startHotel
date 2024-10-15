package com.springboot.app.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.app.model.Habitacion;
import com.springboot.app.model.TipoHabitacion;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long>{
	
	List<Habitacion> findByTipoAndReservacionesFechaIniBeforeAndReservacionesFechaFinAfter(
	        TipoHabitacion tipo, Date fechaFin, Date fechaIni);
	
}
