import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { GuardService } from '../servicies/guard.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(GuardService);
  const router = inject(Router);
  const logado = localStorage.getItem("logado");
  if (authService.isLoggedIn() || logado == "true") {
    return true;
  } else {
    router.navigate(['loguin']);
    return false;
  }
};
