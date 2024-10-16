import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/views/login/login.component';
import { HabitacionesComponent } from './components/views/habitaciones/habitaciones.component';
import { AuthGuard } from './helpers/auth.guard';
import { HomeComponent } from './components/views/home/home.component';
import { RegisterComponent } from './components/views/register/register.component';
import { AboutComponent } from './components/views/about/about.component';
import { ContactComponent } from './components/views/contact/contact.component';
import { UsuariosComponent } from './components/views/usuarios/usuarios.component';
import { TipohabitacionComponent } from './components/views/tipohabitacion/tipohabitacion.component';
import { ListroomsfilterComponent } from './components/views/listroomsfilter/listroomsfilter.component';
import { NotfoundComponent } from './components/views/notfound/notfound.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'rooms', component: HabitacionesComponent, canActivate: [AuthGuard] },
  { path: 'users', component: UsuariosComponent, canActivate: [AuthGuard] },
  { path: 'about', component: AboutComponent },
  { path: 'rooms/filter', component: ListroomsfilterComponent },
  { path: 'rooms/type', component: TipohabitacionComponent },
  { path: 'contact', component: ContactComponent },
  { path: '**', component: NotfoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
