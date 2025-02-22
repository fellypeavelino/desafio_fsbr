import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = '/api/usuario'; 
  public usuarioLoguin!: any;

  constructor(private http: HttpClient) { }

  async loguin(loguin: any): Promise<Usuario> {
    return await firstValueFrom (this.http.post<any>(this.apiUrl, loguin));
  }

  async getToken(): Promise<any> {
    return await firstValueFrom (this.http.get<any>("/api/token")).then(data => data.sub);
  }
}
