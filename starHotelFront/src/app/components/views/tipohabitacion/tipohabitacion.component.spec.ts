import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipohabitacionComponent } from './tipohabitacion.component';

describe('TipohabitacionComponent', () => {
  let component: TipohabitacionComponent;
  let fixture: ComponentFixture<TipohabitacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TipohabitacionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TipohabitacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
