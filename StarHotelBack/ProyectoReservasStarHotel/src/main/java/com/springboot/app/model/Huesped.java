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
@Table(name = "tb_huesped")
@EntityListeners(AuditingEntityListener.class)
public class Huesped {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHuesped;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String email;
    private String nombreuser;
    private String telefono;
    private String direccion;
}
