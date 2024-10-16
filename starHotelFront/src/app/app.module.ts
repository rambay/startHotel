import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/views/login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HabitacionesComponent } from './components/views/habitaciones/habitaciones.component';
import { NavbarComponent } from './components/views/navbar/navbar.component';
import { SearchRoomComponent } from './components/views/search-room/search-room.component';
import { HomeComponent } from './components/views/home/home.component';
import { RegisterComponent } from './components/views/register/register.component';
import { AboutComponent } from './components/views/about/about.component';
import { ContactComponent } from './components/views/contact/contact.component';
import { UsuariosComponent } from './components/views/usuarios/usuarios.component';
import { TipohabitacionComponent } from './components/views/tipohabitacion/tipohabitacion.component';
import { ListroomsfilterComponent } from './components/views/listroomsfilter/listroomsfilter.component';
import { NotfoundComponent } from './components/views/notfound/notfound.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HabitacionesComponent,
    NavbarComponent,
    SearchRoomComponent,
    HomeComponent,
    RegisterComponent,
    AboutComponent,
    ContactComponent,
    UsuariosComponent,
    TipohabitacionComponent,
    ListroomsfilterComponent,
    NotfoundComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    CommonModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
