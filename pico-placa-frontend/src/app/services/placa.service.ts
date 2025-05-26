import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlacaService {
  private apiUrl = 'http://localhost:8080/api/validar';

  constructor(private http: HttpClient) { }

  validarPlaca(placa: string, fechaHora: string): Observable<any> {
    const params = new URLSearchParams();
    params.set('placa', placa);
    params.set('fechaHora', fechaHora);

    return this.http.post(this.apiUrl, params.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    });
  }
}