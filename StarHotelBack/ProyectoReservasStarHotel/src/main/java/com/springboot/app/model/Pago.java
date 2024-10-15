package com.springboot.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_pago")
@EntityListeners(AuditingEntityListener.class)
public class Pago {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @ManyToOne
    @JoinColumn(name = "id_reservacion")
    private Reservacion reservacion;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private String titularTarjeta;
    private String numTarjeta;
    private double montoPagado;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaPago;
    
    public enum MetodoPago {
        TARJETA_CREDITO, TARJETA_DEBITO, EFECTIVO, PAYPAL
    }
}
