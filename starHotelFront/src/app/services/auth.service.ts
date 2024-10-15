import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private nombreUsuarioSource = new BehaviorSubject<string | null>(null);
  private rolNameSource = new BehaviorSubject<string | null>(null);
  nombreUsuario$ = this.nombreUsuarioSource.asObservable();
  rolName$ = this.rolNameSource.asObservable();

  setNombreUsuario(nombre: string): void {
    this.nombreUsuarioSource.next(nombre);
  }

  setNombreRol(rol: string): void {
    this.rolNameSource.next(rol);
  }

  clearNombreUsuario(): void {
    this.nombreUsuarioSource.next(null);
  }

  clearNombreRol(): void {
    this.rolNameSource.next(null);
  }


  isAuthenticated(): boolean {
    const user = JSON.parse(localStorage.getItem('usuario') || '{}');
    return !!user && !!user.nombre;
  }
}
