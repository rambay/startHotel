import { TestBed } from '@angular/core/testing';

import { TipohabitacionService } from './tipohabitacion.service';

describe('TipohabitacionService', () => {
  let service: TipohabitacionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TipohabitacionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
