import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TipohabitacionService } from 'src/app/services/tipohabitacion.service';
import Swal from 'sweetalert2';

declare var bootstrap: any;

@Component({
  selector: 'app-tipohabitacion',
  templateUrl: './tipohabitacion.component.html',
  styleUrls: ['./tipohabitacion.component.css'],
})
export class TipohabitacionComponent implements OnInit {
  listaTiposHabitaciones: any[] = [];
  formTipoHabitacion: FormGroup;
  title: any;
  nameBoton: any;
  id: number;

  constructor(private _tipohabitacionService: TipohabitacionService) {}

  ngOnInit(): void {
    this.obtenerHabitaciones();
    this.initForm();
  }

  initForm() {
    this.formTipoHabitacion = new FormGroup({
      idTipo: new FormControl(null, [Validators.required]),
      descripcion: new FormControl(null, [Validators.required]),
    });
  }

  obtenerHabitaciones() {
    this._tipohabitacionService.getTipohabitaciones().subscribe((data) => {
      this.listaTiposHabitaciones = data.tipoHabitaciones;
      if (this.listaTiposHabitaciones.length > 0) {
        console.log(this.listaTiposHabitaciones);
      }
    });
  }

  cerrarModal() {
    const modalElement = document.getElementById('modalProducto');
    const modal = bootstrap.Modal.getInstance(modalElement);
    modal.hide();
  }

  crearEditarTipoHabitaciones(boton: any) {
    if (boton == 'Guardar') {
      this.alertRegistro();
    } else {
      this.alertModificar();
    }
  }

  guardar(formulario: any): void {
    if (this.formTipoHabitacion.valid) {
      this._tipohabitacionService.guardarTipoHabitaciones(formulario).subscribe(
        (response) => {
          this.cerrarModal();
          this.obtenerHabitaciones();
          this.resetForm();
          console.log('Tipo de Habitación registrada', response);
        },
        (error) => {
          console.log(formulario);
          console.error('Error al registrar tipo de habitación:', error);
        }
      );
    }
  }

  titulo(titulo: any, id: number): void {
    this.title = `${titulo} tipo de habitación`;
    titulo == 'Crear'
      ? (this.nameBoton = 'Guardar')
      : (this.nameBoton = 'Modificar');
    if (id !== null) {
      this.id = id;
      this.obtenerProductoPorId(id);
    }
  }

  obtenerProductoPorId(id: any) {
    let form = this.formTipoHabitacion;

    this._tipohabitacionService.obtenerTipoHabitacionPorId(id).subscribe(
      (response) => {
        if (response) {
          console.log('DATA: ', response);

          form.controls['descripcion'].setValue(
            response.tipoHabitacion.descripcion || null
          );
        } else {
          console.error(
            'Error al obtener el tipo habitación por ID:',
            response
          );
        }
      },
      (error) => {
        console.error('Error al obtener el tipo habitación por ID:', error);
      }
    );
  }

  eliminarTipoHabitaciones(id: number): void {
    Swal.fire({
      title: '¿Deseas eliminar el tipo habitación?',
      text: '¡No podrás revertir esto!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this._tipohabitacionService.eliminarTipoHabitaciones(id).subscribe(
          (response) => {
            Swal.fire(
              'Eliminado!',
              'El tipo habitación ha sido eliminada con éxito.',
              'success'
            );
            this.obtenerHabitaciones();
          },
          (error) => {
            const errorMsg =
              error?.message || 'No se pudo eliminar el tipo habitación';
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
      title: 'Tipo Habitación ' + titulo + ' correctamente',
      showConfirmButton: false,
      timer: 1500,
    });
  }

  resetForm(): void {
    this.formTipoHabitacion.reset();
  }

  alertRegistro() {
    if (this.formTipoHabitacion.valid) {
      Swal.fire({
        title: '¿Deseas registrar el tipo habitación?',
        icon: 'success',
        showCancelButton: true,
        confirmButtonText: 'Si, confirmar',
        cancelButtonText: 'Cancelar',
      }).then((result) => {
        if (result.isConfirmed) {
          this.guardar(this.formTipoHabitacion.value);
          this.alertaExitosa('registrado');
        }
      });
    }
  }

  alertModificar() {
    if (this.formTipoHabitacion.valid) {
      Swal.fire({
        title: '¿Deseas modificar el tipo habitación?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Si, confirmar',
        cancelButtonText: 'Cancelar',
      }).then((result) => {
        if (result.isConfirmed) {
          this.editar(this.id, this.formTipoHabitacion.value);
          this.alertaExitosa('modificado');
        }
      });
    }
  }

  editar(id: number, formulario: any): void {
    if (this.formTipoHabitacion.valid) {
      this._tipohabitacionService
        .editarTipoHabitaciones(id, formulario)
        .subscribe(
          (response) => {
            this.cerrarModal();
            this.obtenerHabitaciones();
            this.resetForm();
            console.log('Tipo Habitación modificada', response);
          },
          (error) => {
            console.error('Error al modificar tipo habitación', error);
          }
        );
    }
  }

  cerrarBoton() {
    this.resetForm();
    this.cerrarModal();
  }
}
