package com.springboot.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_tipo_habitacion")
@EntityListeners(AuditingEntityListener.class)
public class TipoHabitacion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipo;

	@Column(length = 100, nullable = false)
    private String descripcion;

}
