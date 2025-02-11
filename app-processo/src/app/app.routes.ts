import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
    { path: 'loguin', component: LoginComponent },
    ///{ path: 'contatos', component: ContatoListComponent, canActivate: [authGuard]  },
    //{ path: 'contatos/novo', component: ContatoFormComponent, canActivate: [authGuard]  },
    //{ path: 'contatos/editar/:id', component: ContatoFormComponent, canActivate: [authGuard]  },
    //{ path: 'error', component: ErrorComponent },
    { path: '', redirectTo: '/loguin', pathMatch: 'full' }, 
    { path: '**', redirectTo: '/loguin' } 
];
