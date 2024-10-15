import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { LoginService } from 'src/app/services/login.service';
import { UsuariosService } from 'src/app/services/usuarios.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit, OnDestroy {
  errorMessage: string = '';
  usuarioName: string | null = null;
  rolName: string | null = null;
  isLoggedIn: boolean = false;
  private subscription: Subscription = new Subscription();

  constructor(
    private loginService: LoginService,
    private _usuarioService: UsuariosService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.subscription.add(
      this.authService.nombreUsuario$.subscribe((nombre) => {
        this.usuarioName = nombre;
        this.isLoggedIn = !!nombre;
      })
    );
    
    this.subscription.add(
      this.authService.rolName$.subscribe((rol) => {
        this.rolName = rol;
      })
    );

    this.checkLoginStatus();
    
    this.obtenerDetallesUsuario();
  }

  obtenerDetallesUsuario(): void {
    const user = this.loginService.getUserData();

    if (user) {
      const userId = user.id;
      this._usuarioService.listarUsuarioPorId(userId).subscribe(
        (data) => {
          this.usuarioName = data.usuarios.nombre;
          this.rolName = data.usuarios.rol.nombre;

          this.authService.setNombreUsuario(this.usuarioName);
          this.authService.setNombreRol(this.rolName);
        },
        (error) => {
          this.errorMessage = 'Error al obtener los detalles del usuario.';
          console.error(error);
        }
      );
    } else {
      this.errorMessage = 'No se encontraron datos del usuario.';
    }
  }

  checkLoginStatus(): void {
    this.isLoggedIn = this.authService.isAuthenticated();
  }

  logout(): void {
    this.loginService.logout();
    this.authService.clearNombreUsuario();
    this.authService.clearNombreRol();
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
