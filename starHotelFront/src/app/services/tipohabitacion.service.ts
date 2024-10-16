import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TipohabitacionService {
  private baseUrl = environment.apiServicios;
  private tipohabitaciones = `${this.baseUrl}/tipoHabitaciones`;

  constructor(private http: HttpClient) {}

  getTipohabitaciones(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.get(this.tipohabitaciones, { headers });
  }

  guardarTipoHabitaciones(request: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.post(this.tipohabitaciones, request, { headers });
  }

  obtenerTipoHabitacionPorId(id: number): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.get(`${this.tipohabitaciones}/${id}`, { headers });
  }

  eliminarTipoHabitaciones(id: number): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.delete(`${this.tipohabitaciones}/${id}`, { headers });
  }

  editarTipoHabitaciones(id: number, request: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.put(`${this.tipohabitaciones}/${id}`, request, {
      headers,
    });
  }
}
