CREATE DATABASE bd_starhotelv1;
USE bd_starhotelv1;


insert into tb_rol (descripcion_rol, nombre_rol) values ("Rol de Administrador", "Administrador");
insert into tb_rol (descripcion_rol, nombre_rol) values ("Rol de Usuario", "Usuario");

insert into tb_usuario (nombre, apellido, email, contrasena, telefono, direccion, estado, id_rol) VALUES
('Belen', 'Chavez', 'bchavez@gmail.com', '$2a$10$qti88HX6XjdbrvIotkKOteiHwowvrZVQ42TQt2ziNnpdPmE8SGzY2', '980000678', 'Calle San Genaro 123', 'ACTIVO', 1);

insert into tb_usuario (nombre, apellido, email, contrasena, telefono, direccion, estado, id_rol) VALUES
('Diego', 'Ramirez', 'rambay@gmail.com', '$2a$10$Gw1RvIKSKUnNBHzRbgFkVuwWULhWIXEsJKjt4lu/ahA6tNPhsePzS', '980000333', 'Lince', 'ACTIVO', 1);

insert into tb_usuario (nombre, apellido, email, contrasena, telefono, direccion, estado, id_rol) VALUES
('Luis', 'Ramirez', 'luis@gmail.com', '$2a$10$xQgmCW5A8MsAOCbP9IHzYOHsjIf.M8zmZLKWjLWVmkU.u2WjGshuu', '933029382', 'Jesus Maria', 'ACTIVO', 2);


DELIMITER //

CREATE PROCEDURE ObtenerHabitacionesDisponibles(
	IN capacidad INTEGER,
    IN fecha_entrada DATE,
    IN fecha_salida DATE
)
BEGIN
    SELECT *
    FROM tb_habitacion h
    WHERE h.estado = 'disponible'
      AND h.capacidad >= capacidad
      AND h.id_habitacion NOT IN (
          SELECT r.id_habitacion 
          FROM tb_reservacion r 
          WHERE (r.fecha_ini < fecha_salida AND r.fecha_fin > fecha_entrada)
      );
END //

DELIMITER ;

CALL ObtenerHabitacionesDisponibles(3, '2024-10-01', '2024-10-10');
