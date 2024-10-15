import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuariosService } from 'src/app/services/usuarios.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registroForm: FormGroup;

  constructor(private _usuariosService: UsuariosService, private router: Router) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.registroForm = new FormGroup({
      nombre: new FormControl(null, [Validators.required]),
      apellido: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required, Validators.email]),
      contrasena: new FormControl(null, [Validators.required]),
      telefono: new FormControl(null, [Validators.required]),
      direccion: new FormControl(null, [Validators.required]),
      estado: new FormControl('ACTIVO', [Validators.required]),
      id_rol: new FormControl(2, [Validators.required]),
    });
  }

  onSubmit() {
    if (this.registroForm.valid) {
      const request = {
        ...this.registroForm.value,
        rol: {
          idRol: this.registroForm.value.id_rol,
        },
      };

      this._usuariosService.registrarUsuario(request).subscribe({
        next: (res) => {
          console.log('Usuario registrado con éxito:', res);
          this.router.navigate(['/login']);
        },
        error: (err) => {
          console.error('Error al registrar usuario:', err);
        },
      });
    }
  }
}
