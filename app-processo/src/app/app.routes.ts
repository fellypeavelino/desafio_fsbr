import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './guards/auth.guard';
import { ProcessosComponent } from './components/processos/processos.component';

export const routes: Routes = [
    { path: 'loguin', component: LoginComponent },
    { path: 'processos', component: ProcessosComponent, canActivate: [authGuard]  },
    //{ path: 'contatos/novo', component: ContatoFormComponent, canActivate: [authGuard]  },
    //{ path: 'contatos/editar/:id', component: ContatoFormComponent, canActivate: [authGuard]  },
    { path: '', redirectTo: '/loguin', pathMatch: 'full' }, 
    { path: '**', redirectTo: '/loguin' } 
];
