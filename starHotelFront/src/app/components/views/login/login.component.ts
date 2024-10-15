import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { LoginService } from 'src/app/services/login.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  usuario: any[] = [];
  formLogin: FormGroup;

  constructor(
    private _loginService: LoginService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.formLogin = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.email]),
      password: new FormControl(null, [Validators.required]),
    });
  }

  login() {
    if (this.formLogin.valid) {
      this._loginService.ingresar(this.formLogin.value).subscribe({
        next: (res) => {
          const user = this._loginService.getUserData();
          localStorage.setItem('usuario', JSON.stringify(user));
          console.log('Usuario guardado en localStorage:', user);
          this.authService.setNombreUsuario(user.nombre);
          this.router.navigate(['/']);
        },
        error: (err: HttpErrorResponse) => {
          this.alertaError();
        },
      });
    }
  }

  alertaError() {
    Swal.fire({
      position: 'top-end',
      icon: 'error',
      title: 'Correo o contraseña incorrecta',
      showConfirmButton: false,
      timer: 1500,
    });
    this.formLogin.reset();
  }
}
