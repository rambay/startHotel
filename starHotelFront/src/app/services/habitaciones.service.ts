import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HabitacionesService {
  private baseUrl = environment.apiServicios
  private habitaciones = `${this.baseUrl}/habitaciones`

  constructor(private http: HttpClient) { }

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

  obtenerProducto(id: number): Observable<any> {
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
}
