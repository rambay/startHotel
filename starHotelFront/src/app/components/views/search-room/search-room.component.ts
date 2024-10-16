import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HabitacionesService } from 'src/app/services/habitaciones.service';

@Component({
  selector: 'app-search-room',
  templateUrl: './search-room.component.html',
  styleUrls: ['./search-room.component.css'],
})
export class SearchRoomComponent implements OnInit {
  formFiltrado: FormGroup;

  constructor(
    private _habitacionService: HabitacionesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.formFiltrado = new FormGroup({
      capacidad: new FormControl(null, [Validators.required]),
      fechaEntrada: new FormControl(null, [Validators.required]),
      fechaSalida: new FormControl(null, [Validators.required]),
    });
  }

  buscarHabitaciones() {
    if (this.formFiltrado.valid) {
      const { capacidad, fechaEntrada, fechaSalida } = this.formFiltrado.value;

      const fechaEntradaDate = new Date(fechaEntrada);
      const fechaSalidaDate = new Date(fechaSalida);

      this._habitacionService
        .filtrarHabitaciones(capacidad, fechaEntradaDate, fechaSalidaDate)
        .subscribe(
          (data: any) => {
            console.log(data.habitaciones);

            this._habitacionService.setHabitacionesFiltradas(data.habitaciones);

            this.router.navigate(['/rooms/filter'], {
              queryParams: {
                capacidad: capacidad,
                fechaIni: fechaEntradaDate.toISOString(),
                fechaFin: fechaSalidaDate.toISOString(),
              },
            });
          },
          (error) => {
            console.log(error);
          }
        );
    }
  }
}
