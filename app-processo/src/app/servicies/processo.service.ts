import { Injectable } from '@angular/core';
import { Processo } from '../models/processo.model';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { ObjRequest } from '../models/objRequest.model';

@Injectable({
  providedIn: 'root'
})
export class ProcessoService {
  private apiUrl = '/api/processos'; 
  public processo: Processo | null = null;
  public token!: string;
  public usuarioLoguin: any;

  constructor(private http: HttpClient) { }

  async getAll(): Promise<Processo[]> {
    return await firstValueFrom (this.http.get<Processo[]>(this.apiUrl));
  }

  async getPagination(param:ObjRequest): Promise<any> {
    return await firstValueFrom (this.http.post<any>(this.apiUrl+"/paginacao", param));
  }

  async getById(id: number): Promise<Processo> {
    return await firstValueFrom (this.http.get<Processo>(`${this.apiUrl}/${id}`));
  }

  async create(processo: Processo): Promise<Processo> {
    return await firstValueFrom (this.http.post<Processo>(this.apiUrl, processo));
  }

  async update(id: number, processo: Processo): Promise<Processo> {
    return await firstValueFrom (this.http.put<Processo>(`${this.apiUrl}/${id}`, processo));
  }

  async delete(id: number): Promise<void> {
    return await firstValueFrom (this.http.delete<void>(`${this.apiUrl}/${id}`));
  }

  getUsuarioLoguin(): any | null {
    if (this.usuarioLoguin) {
      return this.usuarioLoguin;
    }
    const usuarioLoguin = localStorage.getItem("usuarioLoguin");
    return usuarioLoguin ? JSON.parse(usuarioLoguin) as any : null;
  }
}
