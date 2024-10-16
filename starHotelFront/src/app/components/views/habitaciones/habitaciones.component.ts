import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HabitacionesService } from 'src/app/services/habitaciones.service';
import Swal from 'sweetalert2';

declare var bootstrap: any;

@Component({
  selector: 'app-habitaciones',
  templateUrl: './habitaciones.component.html',
  styleUrls: ['./habitaciones.component.css'],
})
export class HabitacionesComponent implements OnInit {
  listaHabitaciones: any[] = [];
  formHabitaciones: FormGroup;
  title: any;
  nameBoton: any;
  id: number;

  constructor(private _habitacionesService: HabitacionesService) {}

  ngOnInit(): void {
    this.obtenerHabitaciones();
    this.initForm();
  }

  initForm() {
    this.formHabitaciones = new FormGroup({
      descripcion: new FormControl(null, [Validators.required]),
      estado: new FormControl(null, [Validators.required]),
      imageHabitacion: new FormControl(null, [Validators.required]),
      numeroHabitacion: new FormControl(null, [Validators.required]),
      capacidad: new FormControl(null, [Validators.required]),
      precioPorNoche: new FormControl(null, [Validators.required]),
      tipo: new FormGroup({
        idTipo: new FormControl(null, [Validators.required]),
      }),
    });
  }

  obtenerHabitaciones() {
    this._habitacionesService.getHabitaciones().subscribe((data) => {
      this.listaHabitaciones = data.habitaciones;
    });
  }

  cerrarModal() {
    const modalElement = document.getElementById('modalProducto');
    const modal = bootstrap.Modal.getInstance(modalElement);
    modal.hide();
  }

  crearEditarHabitaciones(boton: any) {
    if (boton == 'Guardar') {
      this.alertRegistro();
    } else {
      this.alertModificar();
    }
  }

  guardar(formulario: any): void {
    if (this.formHabitaciones.valid) {
      this._habitacionesService.guardarHabitaciones(formulario).subscribe(
        (response) => {
          this.cerrarModal();
          this.obtenerHabitaciones();
          this.resetForm();
          console.log('Habitación registrada', response);
        },
        (error) => {
          console.log(formulario);
          console.error('Error al registrar habitación:', error);
        }
      );
    }
  }

  titulo(titulo: any, id: number): void {
    this.title = `${titulo} habitacion`;
    titulo == 'Crear'
      ? (this.nameBoton = 'Guardar')
      : (this.nameBoton = 'Modificar');
    if (id !== null) {
      this.id = id;
      this.obtenerProductoPorId(id);
    }
  }

  obtenerProductoPorId(id: any) {
    let form = this.formHabitaciones;

    this._habitacionesService.obtenerHabitacionPorId(id).subscribe(
      (response) => {
        if (response) {
          console.log('DATA: ', response);

          form.controls['descripcion'].setValue(
            response.habitacion.descripcion || null
          );
          form.controls['estado'].setValue(response.habitacion.estado || null);
          form.controls['imageHabitacion'].setValue(
            response.habitacion.imageHabitacion || null
          );
          form.controls['numeroHabitacion'].setValue(
            response.habitacion.numeroHabitacion || null
          );
          form.controls['capacidad'].setValue(
            response.habitacion.capacidad || null
          );
          form.controls['precioPorNoche'].setValue(
            response.habitacion.precioPorNoche || null
          );
          form.controls['tipo']
            .get('idTipo')
            ?.setValue(response.habitacion.tipo?.idTipo || null);
        } else {
          console.error('Error al obtener habitación por ID:', response);
        }
      },
      (error) => {
        console.error('Error al obtener habitación por ID:', error);
      }
    );
  }

  eliminarHabitaciones(id: number): void {
    Swal.fire({
      title: '¿Deseas eliminar la habitación?',
      text: '¡No podrás revertir esto!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this._habitacionesService.eliminarHabitaciones(id).subscribe(
          (response) => {
            Swal.fire(
              'Eliminado!',
              'La habitación ha sido eliminada con éxito.',
              'success'
            );
            this.obtenerHabitaciones();
          },
          (error) => {
            const errorMsg =
              error?.message || 'No se pudo eliminar la habitación';
            Swal.fire('Error', errorMsg, 'error');
          }
        );
      }
    });
  }

  alertaExitosa(titulo: any) {
    Swal.fire({
      position: 'center',
      icon: 'success',
      title: 'Habitación ' + titulo + ' correctamente',
      showConfirmButton: false,
      timer: 1500,
    });
  }

  resetForm(): void {
    this.formHabitaciones.reset();
  }

  alertRegistro() {
    if (this.formHabitaciones.valid) {
      Swal.fire({
        title: '¿Deseas registrar la habitación?',
        icon: 'success',
        showCancelButton: true,
        confirmButtonText: 'Si, confirmar',
        cancelButtonText: 'Cancelar',
      }).then((result) => {
        if (result.isConfirmed) {
          this.guardar(this.formHabitaciones.value);
          this.alertaExitosa('registrado');
        }
      });
    }
  }

  alertModificar() {
    if (this.formHabitaciones.valid) {
      Swal.fire({
        title: '¿Deseas modificar la habitación?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Si, confirmar',
        cancelButtonText: 'Cancelar',
      }).then((result) => {
        if (result.isConfirmed) {
          this.editar(this.id, this.formHabitaciones.value);
          this.alertaExitosa('modificado');
        }
      });
    }
  }

  editar(id: number, formulario: any): void {
    if (this.formHabitaciones.valid) {
      this._habitacionesService.editarHabitaciones(id, formulario).subscribe(
        (response) => {
          this.cerrarModal();
          this.obtenerHabitaciones();
          this.resetForm();
          console.log('Habitación modificada', response);
        },
        (error) => {
          console.error('Error al modificar habitación', error);
        }
      );
    }
  }

  cerrarBoton() {
    this.resetForm();
    this.cerrarModal();
  }
}
