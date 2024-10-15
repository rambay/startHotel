import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UsuariosService } from 'src/app/services/usuarios.service';
import Swal from 'sweetalert2'

declare var bootstrap: any;

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {
  listarUsuarios : any[] = [];
  formUsuarios: FormGroup;
  title: any;
  nameBoton: any;
  id: number;


  constructor(private _usuariosService: UsuariosService) { }

  ngOnInit(): void {
    this.obtenerUsuarios();
    this.initForm();
  }

  initForm() {
    this.formUsuarios = new FormGroup({
      apellido: new FormControl(null, [Validators.required]),
      contrasena: new FormControl(null, [Validators.required]),
      direccion: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required]),
      estado: new FormControl(null, [Validators.required]),
      nombre: new FormControl(null, [Validators.required]),
      rol: new FormGroup({
        idRol: new FormControl(null, [Validators.required]),
      }),
      telefono: new FormControl(null, [Validators.required]),
    });
  }

    obtenerUsuarios(){
      this._usuariosService.listarUsuarios().subscribe(data => {
        console.log("Usuarios:", data.usuarios);
        this.listarUsuarios = data.usuarios;
      })
    }

    cerrarModal() {
      const modalElement = document.getElementById('modalProducto');
      const modal = bootstrap.Modal.getInstance(modalElement);
      modal.hide();
    }

    crearEditarUsuarios(boton: any) {
      if(boton == 'Guardar') {
        this.alertRegistro();
      } else {
        this.alertModificar();
      }
    }

  guardar(formulario: any): void {
    if(this.formUsuarios.valid) {
      this._usuariosService.registrarUsuario(formulario).subscribe((response) => {
        console.log(formulario);
        this.cerrarModal();
        this.obtenerUsuarios();
        this.resetForm();
        console.log('Usuario registrado', response);
      }, (error) => {
        console.log(formulario);
        console.error('Error al registrar usuario:', error);
      })
    }
  }

  
  titulo(titulo: any, id: number): void {
    this.title = `${titulo} usuario`;
    titulo == 'Crear' ? (this.nameBoton = 'Guardar')
    : (this.nameBoton = 'Modificar');
    if(id !== null) {
      this.id = id;
      this.obtenerUsuarioPorId(id);
    }
  }

  obtenerUsuarioPorId(id: any) {
    let form = this.formUsuarios;

    this._usuariosService.listarUsuarioPorId(id).subscribe((response) => {
      if(response) {
        form.controls['apellido'].setValue(response.usuarios.apellido || null);
        form.controls['contrasena'].setValue(response.usuarios.contrasena || null);
        form.controls['direccion'].setValue(response.usuarios.direccion || null);
        form.controls['email'].setValue(response.usuarios.email || null);
        form.controls['estado'].setValue(response.usuarios.estado || null);
        form.controls['nombre'].setValue(response.usuarios.nombre || null);
        form.controls['rol'].get('idRol')?.setValue(response.usuarios.rol?.idRol || null);
        form.controls['telefono'].setValue(response.usuarios.telefono || null);
      } else {
        console.error('Error al obtener usuario por ID:', response);
      }
    }, (error) => {
      console.error('Error al obtener usuario por ID:', error);
    })
  }

  eliminarUsuarios(id: number): void {
    Swal.fire({
      title: '¿Deseas eliminar el usuario?',
      text: '¡No podrás revertir esto!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this._usuariosService.eliminarUsuarios(id).subscribe(
          (response) => {
            Swal.fire('Eliminado!', 'El usuario ha sido eliminada con éxito.', 'success');
            this.obtenerUsuarios();
          },
          (error) => {
            const errorMsg = error?.message || 'No se pudo eliminar el usuario';
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
      title: 'Usuario '+ titulo +' correctamente',
      showConfirmButton: false,
      timer: 1500,
    })
  }

  resetForm(): void {
    this.formUsuarios.reset();
  }

  
  alertRegistro() {
    if(this.formUsuarios.valid) {
    Swal.fire({
      title: '¿Deseas registrar el usuario?',
      icon: 'success',
      showCancelButton: true,
      confirmButtonText: 'Si, confirmar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.guardar(this.formUsuarios.value);
        this.alertaExitosa('registrado')
      }});
    }
  }

  alertModificar() {
    if(this.formUsuarios.valid) {
      Swal.fire({
        title: '¿Deseas modificar el usuario?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Si, confirmar',
        cancelButtonText: 'Cancelar',
      }).then((result) => {
        if (result.isConfirmed) {
          this.editar(this.id, this.formUsuarios.value);
          this.alertaExitosa('modificado')
        }
      })
    }
   }

   editar(id: number, formulario: any): void {
    if(this.formUsuarios.valid) {
      this._usuariosService.editarUsuarios(id, formulario).subscribe((response) => {
        this.cerrarModal();
        this.obtenerUsuarios();
        this.resetForm();
        console.log("Usuario modificado", response);
      }, (error) => {
        console.error("Error al modificar usuario", error);
      })
   }
  }

  cerrarBoton() {
    this.resetForm();
    this.cerrarModal();
  }

}

