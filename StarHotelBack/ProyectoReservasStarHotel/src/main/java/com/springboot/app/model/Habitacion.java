package com.springboot.app.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_habitacion")
@EntityListeners(AuditingEntityListener.class)
public class Habitacion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHabitacion;
    private String numeroHabitacion;
    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoHabitacion tipo;
    private double precioPorNoche;
    @Enumerated(EnumType.STRING)
    private EstadoHabitacion estado;
    private String descripcion;
    public enum EstadoHabitacion {
        disponible, ocupada, mantenimiento
    }
    @OneToMany(mappedBy = "habitacion")
    @JsonIgnore
    private List<Reservacion> reservaciones;
    private String imageHabitacion;
}
