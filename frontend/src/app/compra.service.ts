import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

export interface Compra {
  id: number;
  fecha: string;
  medioPago: string;
  comprador: string;
  montoTotal: number;
  comercioId: number;
  comercioNombre: string;
}

export interface Comercio {
  id: number;
  nombre: string;
  lugar: string;
}

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getCompras(): Observable<Compra[]> {
    return this.http.get<Compra[]>(`${this.apiUrl}/compras`);
  }

  createCompra(compra: Omit<Compra, 'id'>): Observable<Compra> {
    return this.http.post<Compra>(`${this.apiUrl}/compras`, compra);
  }

  updateCompra(id: number, compra: Partial<Compra>): Observable<Compra> {
    return this.http.put<Compra>(`${this.apiUrl}/compras/${id}`, compra);
  }

  deleteCompra(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/compras/${id}`);
  }

  getComprasByComercio(comercioId: number): Observable<Compra[]> {
    return this.http.get<Compra[]>(`${this.apiUrl}/compras/comercio/${comercioId}`);
  }

  getComprasByFecha(comercioId: number, fechaInicio: string, fechaFin: string): Observable<Compra[]> {
    return this.http.get<Compra[]>(`${this.apiUrl}/compras/comercio/${comercioId}/fecha?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`);
  }

  getComprasByMedioPago(comercioId: number, medioPago: string): Observable<Compra[]> {
    return this.http.get<Compra[]>(`${this.apiUrl}/compras/comercio/${comercioId}/medio-pago/${medioPago}`);
  }

  getComercios(): Observable<Comercio[]> {
    return this.http.get<Comercio[]>(`${this.apiUrl}/comercios`);
  }
}