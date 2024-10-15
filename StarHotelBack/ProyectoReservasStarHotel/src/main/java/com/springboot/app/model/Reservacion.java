package com.springboot.app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_reservacion")
@EntityListeners(AuditingEntityListener.class)
public class Reservacion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservacion;

    @ManyToOne
    @JoinColumn(name = "id_huesped")
    private Huesped huesped;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion;

    private Date fechaIni;
    private Date fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoReservacion estadoReservacion;

    private double totalPago;
    
    public enum EstadoReservacion {
    	pendiente, confirmada, cancelada, completada
    }
    
    @ManyToOne()
    @JoinColumn(name="id_servicio")
    private Servicio servicio;
}
