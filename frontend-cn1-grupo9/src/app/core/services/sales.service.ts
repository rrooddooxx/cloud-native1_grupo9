import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface SaleRequest {
  productId: number;
  productTitle: string;
  price: number;
  quantity: number;
  customerId: string;
  customerEmail: string;
}

export interface SaleResponse {
  id: number;
  productId: number;
  productTitle: string;
  price: number;
  quantity: number;
  customerId: string;
  customerEmail: string;
  saleDate: string;
}

@Injectable({
  providedIn: 'root'
})
export class SalesService {
  private readonly baseUrl = `${environment.bffUrl}/sales`;

  constructor(private http: HttpClient) {}

  createSale(saleData: SaleRequest): Observable<SaleResponse> {
    return this.http.post<SaleResponse>(`${this.baseUrl}/transaction`, saleData);
  }
}