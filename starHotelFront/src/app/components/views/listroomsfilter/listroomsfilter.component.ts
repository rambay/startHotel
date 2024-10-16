import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HabitacionesService } from 'src/app/services/habitaciones.service';

@Component({
  selector: 'app-listroomsfilter',
  templateUrl: './listroomsfilter.component.html',
  styleUrls: ['./listroomsfilter.component.css'],
})
export class ListroomsfilterComponent implements OnInit {
  habitaciones: any[] = [];
  capacidad: number;
  fechaEntrada: Date;
  fechaSalida: Date;

  constructor(
    private _habitacionService: HabitacionesService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.capacidad = +params['capacidad'];
      this.fechaEntrada = new Date(params['fechaIni']);
      this.fechaSalida = new Date(params['fechaFin']);

      if (this.capacidad && this.fechaEntrada && this.fechaSalida) {
        this._habitacionService
          .filtrarHabitaciones(
            this.capacidad,
            this.fechaEntrada,
            this.fechaSalida
          )
          .subscribe(
            (data: any) => {
              this.habitaciones = data.habitaciones;
              console.log('Habitaciones filtradas:', this.habitaciones);
            },
            (error) => {
              console.error('Error al obtener habitaciones filtradas:', error);
            }
          );
      } else {
        console.error('No se proporcionaron todos los parámetros necesarios.');
      }
    });
  }
}
