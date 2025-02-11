import { HttpInterceptorFn } from '@angular/common/http';
import { LoadingService } from '../servicies/loading.service';
import { inject } from '@angular/core';
import { finalize } from 'rxjs';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);
  const authToken = localStorage.getItem("token");
  
  loadingService.setLoading(true);

  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${authToken}`
    }
  });

  return next(authReq).pipe(
    finalize(() => loadingService.setLoading(false))
  );
};
