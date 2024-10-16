import { formatDate } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class HabitacionesService {
  private baseUrl = environment.apiServicios;
  private habitaciones = `${this.baseUrl}/habitaciones`;
  private habitacionesFiltradas: any[] = [];

  constructor(private http: HttpClient) {}

  getHabitaciones(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.get(this.habitaciones, { headers });
  }

  guardarHabitaciones(request: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.post(this.habitaciones, request, { headers });
  }

  editarHabitaciones(id: number, request: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.put(`${this.habitaciones}/${id}`, request, { headers });
  }

  obtenerHabitacionPorId(id: number): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.get(`${this.habitaciones}/${id}`, { headers });
  }

  eliminarHabitaciones(id: number): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.delete(`${this.habitaciones}/${id}`, { headers });
  }

  filtrarHabitaciones(
    capacidad: number,
    fechaEntrada: Date,
    fechaSalida: Date
  ): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    const fechaEntradaISO = fechaEntrada.toISOString().split('T')[0];
    const fechaSalidaISO = fechaSalida.toISOString().split('T')[0];

    return this.http.get(
      `${this.habitaciones}/filtrar/${capacidad}/${fechaEntradaISO}/${fechaSalidaISO}`,
      { headers }
    );
  }

  setHabitacionesFiltradas(habitaciones: any[]) {
    this.habitacionesFiltradas = habitaciones;
  }

  getHabitacionesFiltradas(): any[] {
    return this.habitacionesFiltradas;
  }
}
