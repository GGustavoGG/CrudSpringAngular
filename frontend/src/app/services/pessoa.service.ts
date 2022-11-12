import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pessoa } from "../models/Pessoa";

@Injectable({
  providedIn: 'root'
})
export class PessoaService {
  public baseUrl = '/api/pessoas';

  constructor(private http: HttpClient) { }

  findAll(): Observable<Pessoa[]> {
    return this.http.get<Pessoa[]>(this.baseUrl);
  }

  create(pessoa: Pessoa): Observable<Pessoa> {
    return this.http.post<Pessoa>(this.baseUrl, pessoa);
  }

  delete(id: any): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  findById(id: any): Observable<Pessoa>{
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Pessoa>(url);
  }

  update(pessoa: Pessoa): Observable<Pessoa>{
    const url = `${this.baseUrl}/${pessoa.id}`;
    return this.http.put<Pessoa>(url, pessoa);
  }

  relatorio(id: any) {
    const url = `${this.baseUrl}/${id}/imprimir`;
    return this.http.get(url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'blob'
    });
  }
}
