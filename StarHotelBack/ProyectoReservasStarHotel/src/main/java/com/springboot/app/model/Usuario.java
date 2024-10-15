package com.springboot.app.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long id;
	private String nombre;
	private String apellido;
	private String email;
	private String contrasena;
	private String telefono;
	private String direccion;

	@ManyToOne
	@JoinColumn(name = "id_rol", nullable = false)
	private Rol rol;

	@Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 50, nullable = false)
    private EstadoUsuario estado;
}
