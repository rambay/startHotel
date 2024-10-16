import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListroomsfilterComponent } from './listroomsfilter.component';

describe('ListroomsfilterComponent', () => {
  let component: ListroomsfilterComponent;
  let fixture: ComponentFixture<ListroomsfilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListroomsfilterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListroomsfilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
