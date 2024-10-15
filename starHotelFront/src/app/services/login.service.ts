import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private baseUrl = environment.apiUrlBase;
  private login = `${this.baseUrl}/login`;

  constructor(private http: HttpClient,  private authService: AuthService) {}

  ingresar(request: any): Observable<any> {
    return this.http
      .post(`${this.login}`, request, {
        observe: 'response',
      })
      .pipe(
        map((response: HttpResponse<any>) => {
          const headers = response.headers;
          const bearerToken = headers.get('Authorization');

          if (bearerToken) {
            const token = bearerToken.replace('Bearer ', '');
            localStorage.setItem('token', token);

            const user = this.decodeToken(token);
            localStorage.setItem('user', JSON.stringify(user));
            localStorage.setItem('rol', user.rolName);

            this.authService.setNombreUsuario(user.nombre);
            this.authService.setNombreRol(user.rolName);
            
          } else {
            console.log('No se encontró token en la cabecera.');
          }
          return response.body;
        })
      );
  }

  private decodeToken(token: string): any {
    const payload = token.split('.')[1];
    const decodedPayload = atob(payload);
    return JSON.parse(decodedPayload);
  }

  token() {
    return localStorage.getItem('token');
  }

  getUserData() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('usuario');
    localStorage.removeItem('rol');
    
    this.authService.clearNombreUsuario();
    this.authService.clearNombreRol();
  }
}
