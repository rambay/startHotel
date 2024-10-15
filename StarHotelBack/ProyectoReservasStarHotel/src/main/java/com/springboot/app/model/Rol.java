package com.springboot.app.model;

import javax.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_rol")
@EntityListeners(AuditingEntityListener.class)
public class Rol {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
	
	@Column(name="nombre_rol")
    private String nombre;
    private String descripcionRol;
}
