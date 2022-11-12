import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cidade } from '../models/Cidade';

@Injectable({
  providedIn: 'root'
})
export class CidadeService {
  baseUrl = '/api/cidades';

  constructor(private http: HttpClient) { }

  findAll(): Observable<Cidade[]> {
    return this.http.get<Cidade[]>(this.baseUrl);
  }
  
  create(cidade: Cidade): Observable<Cidade> {
    return this.http.post<Cidade>(this.baseUrl, cidade);
  }
  
  delete(id: any): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  findById(id: any): Observable<Cidade> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Cidade>(url);
  }

  update(cidade: Cidade): Observable<Cidade>{
    const url = `${this.baseUrl}/${cidade.id}`;
    return this.http.put<Cidade>(url, cidade);
  }
}
